package tests;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
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
        System.out.println("All " + checks + " checks passed.");
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
