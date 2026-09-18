package ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/** DFS and BFS using the discovery rules from Topic 6. */
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

    /** Visits only vertices reachable from source, using recursive DFS. */
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

    private static <V> void dfs(Graph<V> graph, V vertex, Set<V> marked,
            List<V> order, Map<V, V> parent)
    {
        marked.add(vertex);
        order.add(vertex);
        for (V neighbour : graph.neighbours(vertex))
        {
            if (!marked.contains(neighbour))
            {
                parent.put(neighbour, vertex);
                dfs(graph, neighbour, marked, order, parent);
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
