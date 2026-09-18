package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import app.ExampleGraph;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Set;
import ds.Graph;
import ds.Traversals;

/** Behaviour checks, including an independent shortest-hop oracle for BFS. */
public final class ExampleChecks
{
    private static int checks;

    private static void check(boolean condition)
    {
        checks++;
        if (!condition)
            throw new AssertionError("Check " + checks);
    }

    private static void expect(Class<? extends Throwable> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Throwable failure)
        {
            if (type.isInstance(failure))
                return;
            throw new AssertionError("Wrong exception", failure);
        }
        throw new AssertionError("Expected " + type.getSimpleName());
    }

    public static void main(String[] args)
    {
        checkTheoryGraph();
        Graph<Integer> empty = new Graph<>(Comparator.naturalOrder());
        check(Traversals.breadthFirstForest(empty).order().isEmpty());
        check(Traversals.depthFirstForest(empty).order().isEmpty());
        expect(IllegalArgumentException.class, () -> Traversals.breadthFirst(empty, 0));
        expect(IllegalArgumentException.class, () -> Traversals.depthFirst(empty, null));
        expect(IllegalArgumentException.class, () -> Traversals.depthFirstForest(null));
        expect(IllegalArgumentException.class, () -> new Graph<Integer>(null));
        expect(IllegalArgumentException.class, () -> empty.addEdge(1, null));
        check(empty.vertices().isEmpty());
        check(!empty.addEdge(1, 1));
        check(empty.vertices().isEmpty());
        check(empty.addVertex(1));
        check(!empty.addVertex(1));
        check(empty.addEdge(1, 2));
        check(!empty.addEdge(1, 2));
        check(empty.neighbours(2).isEmpty());
        expect(UnsupportedOperationException.class, () -> empty.vertices().clear());
        expect(UnsupportedOperationException.class, () -> empty.neighbours(1).clear());
        Traversals.Result<Integer> saved = Traversals.breadthFirst(empty, 1);
        empty.addEdge(2, 3);
        check(saved.order().equals(List.of(1, 2)));
        expect(UnsupportedOperationException.class, () -> saved.order().clear());
        expect(UnsupportedOperationException.class, () -> saved.parent().clear());
        Graph<String> lesson = new Graph<>(Comparator.naturalOrder());
        lesson.addEdge("A", "B");
        lesson.addEdge("A", "D");
        lesson.addEdge("A", "E");
        lesson.addEdge("B", "C");
        lesson.addEdge("B", "D");
        lesson.addEdge("C", "A");
        lesson.addVertex("Z");
        check(Traversals.breadthFirst(lesson, "A").order().equals(List.of("A", "B", "D", "E", "C")));
        check(Traversals.depthFirst(lesson, "A").order().equals(List.of("A", "B", "C", "D", "E")));
        check(Traversals.depthFirstForest(lesson).order().size() == 6);
        check(!Traversals.depthFirstForest(lesson).parent().containsKey("Z"));
        Random random = new Random(603);
        for (int trial = 0; trial < 100; trial++)
        {
            int n = 1 + random.nextInt(10);
            Graph<Integer> graph = new Graph<>(Comparator.naturalOrder());
            int[][] distance = new int[n][n];
            for (int i = 0; i < n; i++)
            {
                graph.addVertex(i);
                for (int j = 0; j < n; j++)
                {
                    distance[i][j] = i == j ? 0 : 1000;
                }
            }
            for (int i = 0; i < n; i++)
            {
                for (int j = 0; j < n; j++)
                {
                    if (i != j && random.nextInt(4) == 0)
                    {
                        graph.addEdge(i, j);
                        distance[i][j] = 1;
                    }
                }
            }
            // Floyd-Warshall computes shortest hop counts independently of BFS.
            for (int k = 0; k < n; k++)
            {
                for (int i = 0; i < n; i++)
                {
                    for (int j = 0; j < n; j++)
                    {
                        distance[i][j] = Math.min(distance[i][j], distance[i][k] + distance[k][j]);
                    }
                }
            }
            for (int source = 0; source < n; source++)
            {
                Traversals.Result<Integer> bfs = Traversals.breadthFirst(graph, source);
                Traversals.Result<Integer> dfs = Traversals.depthFirst(graph, source);
                check(new HashSet<>(bfs.order()).equals(new HashSet<>(dfs.order())));
                verifyParents(graph, bfs);
                verifyParents(graph, dfs);
                List<Integer> referenceOrder = new ArrayList<>();
                Map<Integer, Integer> referenceParent = new LinkedHashMap<>();
                referenceDfs(graph, source, new HashSet<>(), referenceOrder, referenceParent);
                check(dfs.order().equals(referenceOrder));
                check(dfs.parent().equals(referenceParent));
                int previous = -1;
                for (int vertex : bfs.order())
                {
                    int depth = 0;
                    int cursor = vertex;
                    while (bfs.parent().containsKey(cursor))
                    {
                        cursor = bfs.parent().get(cursor);
                        depth++;
                    }
                    check(cursor == source);
                    check(depth == distance[source][vertex]);
                    check(depth >= previous);
                    previous = depth;
                }
                for (int vertex = 0; vertex < n; vertex++)
                {
                    check(bfs.order().contains(vertex) == (distance[source][vertex] < 1000));
                }
            }
            Traversals.Result<Integer> forest = Traversals.depthFirstForest(graph);
            check(forest.order().size() == n);
            verifyParents(graph, forest);
            forest = Traversals.breadthFirstForest(graph);
            check(forest.order().size() == n);
            verifyParents(graph, forest);
        }
        Graph<Integer> chain = new Graph<>(Comparator.naturalOrder());
        for (int i = 0; i < 20000; i++)
        {
            chain.addEdge(i, i + 1);
        }
        check(Traversals.depthFirst(chain, 0).order().size() == 20001);
        System.out.println("All " + checks + " checks passed.");
    }

    // Independent recursive oracle, used only for the small random test graphs.
    private static void referenceDfs(Graph<Integer> graph, int vertex, Set<Integer> marked,
            List<Integer> order, Map<Integer, Integer> parent)
    {
        marked.add(vertex);
        order.add(vertex);
        for (int neighbour : graph.neighbours(vertex))
        {
            if (!marked.contains(neighbour))
            {
                parent.put(neighbour, vertex);
                referenceDfs(graph, neighbour, marked, order, parent);
            }
        }
    }

    private static void checkTheoryGraph()
    {
        Graph<String> graph = ExampleGraph.create();
        check(graph.vertices().equals(Set.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")));
        check(graph.neighbours("A").equals(Set.of("B", "D", "E")));
        check(graph.neighbours("B").equals(Set.of("C", "D")));
        check(graph.neighbours("C").equals(Set.of("A")));
        check(graph.neighbours("D").equals(Set.of()));
        check(graph.neighbours("E").equals(Set.of("B", "D")));
        check(graph.neighbours("F").equals(Set.of("G", "I", "J")));
        check(graph.neighbours("G").equals(Set.of("E", "H")));
        check(graph.neighbours("H").equals(Set.of("C", "D")));
        check(graph.neighbours("I").equals(Set.of("D", "H")));
        check(graph.neighbours("J").equals(Set.of("G", "I")));
        Traversals.Result<String> dfs = Traversals.depthFirstForest(graph);
        Traversals.Result<String> bfs = Traversals.breadthFirstForest(graph);
        check(dfs.order().equals(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")));
        check(bfs.order().equals(List.of("A", "B", "D", "E", "C", "F", "G", "I", "J", "H")));
        check(dfs.parent().equals(Map.of("B", "A", "C", "B", "D", "B", "E", "A",
                "G", "F", "H", "G", "I", "F", "J", "F")));
        check(bfs.parent().equals(Map.of("B", "A", "D", "A", "E", "A", "C", "B",
                "G", "F", "I", "F", "J", "F", "H", "G")));
        check(Traversals.depthFirst(graph, "A").order().equals(List.of("A", "B", "C", "D", "E")));
        check(Traversals.breadthFirst(graph, "A").order().equals(List.of("A", "B", "D", "E", "C")));
        check(Traversals.depthFirst(graph, "F").order().size() == 10);
        check(Traversals.breadthFirst(graph, "F").order().size() == 10);
    }

    private static void verifyParents(Graph<Integer> graph, Traversals.Result<Integer> result)
    {
        check(new HashSet<>(result.order()).size() == result.order().size());
        List<Integer> seen = new ArrayList<>();
        for (int vertex : result.order())
        {
            if (result.parent().containsKey(vertex))
            {
                int parent = result.parent().get(vertex);
                check(seen.contains(parent));
                check(graph.neighbours(parent).contains(vertex));
            }
            seen.add(vertex);
        }
    }
}
