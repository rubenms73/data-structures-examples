package ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** DFS with a stack of vertices and BFS with a queue of vertices. */
public final class Traversals
{
    private Traversals()
    {
    }

    /** Immutable snapshots; roots have no entry in parent. */
    public record Result<V>(List<V> order, Map<V, V> parent)
    {
        public Result
        {
            if (order == null || parent == null)
                throw new IllegalArgumentException("Null result data");
            order = Collections.unmodifiableList(new ArrayList<>(order));
            parent = Collections.unmodifiableMap(new LinkedHashMap<>(parent));
        }
    }

    /** Visits only vertices reachable from source, using a FIFO queue. */
    public static <V> Result<V> breadthFirst(Graph<V> graph, V source)
    {
        checkSource(graph, source);
        List<V> order = new ArrayList<>();
        Map<V, V> parent = new LinkedHashMap<>();
        bfs(graph, source, new HashSet<>(), order, parent);
        return new Result<>(order, parent);
    }

    /** Visits only vertices reachable from source, using an explicit LIFO stack of vertices. */
    public static <V> Result<V> depthFirst(Graph<V> graph, V source)
    {
        checkSource(graph, source);
        List<V> order = new ArrayList<>();
        Map<V, V> parent = new LinkedHashMap<>();
        dfs(graph, source, new HashSet<>(), order, parent);
        return new Result<>(order, parent);
    }

    /** Visits the whole graph, starting BFS again at each unmarked vertex. */
    public static <V> Result<V> breadthFirstForest(Graph<V> graph)
    {
        return forest(graph, true);
    }

    /** Visits the whole graph, starting DFS again at each unmarked vertex. */
    public static <V> Result<V> depthFirstForest(Graph<V> graph)
    {
        return forest(graph, false);
    }

    private static <V> Result<V> forest(Graph<V> graph, boolean breadthFirst)
    {
        if (graph == null)
            throw new IllegalArgumentException("Null graph");
        Set<V> marked = new HashSet<>();
        List<V> order = new ArrayList<>();
        Map<V, V> parent = new LinkedHashMap<>();
        for (V vertex : graph.vertices())
        {
            if (!marked.contains(vertex))
            {
                if (breadthFirst)
                    bfs(graph, vertex, marked, order, parent);
                else
                    dfs(graph, vertex, marked, order, parent);
            }
        }
        return new Result<>(order, parent);
    }

    private static <V> void checkSource(Graph<V> graph, V source)
    {
        if (graph == null)
            throw new IllegalArgumentException("Null graph");
        graph.neighbours(source);
    }

    private static <V> void dfs(Graph<V> graph, V source, Set<V> marked,
            List<V> order, Map<V, V> parent)
    {
        Deque<V> pending = new ArrayDeque<>();
        Map<V, V> candidateParent = new HashMap<>();
        pending.push(source);
        while (!pending.isEmpty())
        {
            V vertex = pending.pop();
            // A vertex can have several pending entries. Visit only the first popped.
            if (marked.contains(vertex))
                continue;
            marked.add(vertex);
            order.add(vertex);
            if (candidateParent.containsKey(vertex))
                parent.put(vertex, candidateParent.get(vertex));

            // Reverse the neighbour order: the smallest must be on top of the stack.
            List<V> neighbours = new ArrayList<>(graph.neighbours(vertex));
            for (int i = neighbours.size() - 1; i >= 0; i--)
            {
                V neighbour = neighbours.get(i);
                if (!marked.contains(neighbour))
                {
                    pending.push(neighbour);
                    // Still tentative: a deeper branch may push this vertex again.
                    candidateParent.put(neighbour, vertex);
                }
            }
        }
    }

    private static <V> void bfs(Graph<V> graph, V source, Set<V> marked,
            List<V> order, Map<V, V> parent)
    {
        Queue<V> pending = new ArrayDeque<>();
        marked.add(source);
        pending.add(source);
        while (!pending.isEmpty())
        {
            V vertex = pending.remove();
            order.add(vertex);
            for (V neighbour : graph.neighbours(vertex))
            {
                // Mark on enqueue: two incoming arcs cannot enqueue it twice.
                if (marked.add(neighbour))
                {
                    parent.put(neighbour, vertex);
                    pending.add(neighbour);
                }
            }
        }
    }
}
