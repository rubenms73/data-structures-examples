package ds;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Simple directed weighted graph, represented by sorted adjacency maps.
 * Vertices are non-null. The comparator must be consistent with equals;
 * comparison, equality and hash codes must remain stable while vertices are used.
 * Finite negative weights are allowed in the graph, but rejected by Dijkstra.
 * This class is not thread-safe. Do not modify a graph during a search.
 * @param <V> vertex type (it need not implement Comparable)
 */
public class WeightedGraph<V>
{
    private final Comparator<? super V> order;
    private final SortedMap<V, SortedMap<V, Double>> edges;
    private int edgeCount;

    /** Creates an empty graph using the same ordering for all adjacency maps. */
    public WeightedGraph(Comparator<? super V> order)
    {
        if (order == null)
            throw new NullPointerException("Comparator must not be null");
        this.order = order;
        edges = new TreeMap<>(order);
    }

    /** Copies all maps; the vertex objects themselves are shared. */
    public WeightedGraph(WeightedGraph<V> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        order = source.order;
        edges = new TreeMap<>(order);
        for (V vertex : source.edges.keySet())
        {
            SortedMap<V, Double> neighbours = new TreeMap<>(order);
            neighbours.putAll(source.edges.get(vertex));
            edges.put(vertex, neighbours);
        }
        edgeCount = source.edgeCount;
    }

    /** Returns false for an existing vertex, without changing the graph. */
    public boolean addVertex(V vertex)
    {
        if (vertex == null)
            throw new NullPointerException("Vertex must not be null");
        if (edges.containsKey(vertex))
            return false;
        edges.put(vertex, new TreeMap<>(order));
        return true;
    }

    /** Tests membership; null is not a valid vertex. */
    public boolean hasVertex(V vertex)
    {
        if (vertex == null)
            throw new NullPointerException("Vertex must not be null");
        return edges.containsKey(vertex);
    }

    /**
     * Adds one arc, creating missing endpoints. Returns false for a self-loop
     * or an existing arc; neither case changes the graph or the existing weight.
     * @throws IllegalArgumentException if weight is NaN or infinite
     * @throws NullPointerException if either endpoint is null
     */
    public boolean addEdge(V from, V to, double weight)
    {
        if (from == null || to == null)
            throw new NullPointerException("Endpoints must not be null");
        if (Double.isNaN(weight) || Double.isInfinite(weight))
            throw new IllegalArgumentException("Weight must be finite");
        if (order.compare(from, to) == 0 || hasEdge(from, to))
            return false;
        addVertex(from);
        addVertex(to);
        edges.get(from).put(to, weight);
        edgeCount++;
        return true;
    }

    /** Counts vertices, including isolated vertices. */
    public int vertexCount()
    {
        return edges.size();
    }

    /** Counts directed arcs; a two-way connection consists of two arcs. */
    public int edgeCount()
    {
        return edgeCount;
    }

    /** Returns false if either endpoint or the arc is absent; rejects null. */
    public boolean hasEdge(V from, V to)
    {
        if (from == null || to == null)
            throw new NullPointerException("Endpoints must not be null");
        Map<V, Double> neighbours = edges.get(from);
        return neighbours != null && neighbours.containsKey(to);
    }

    /** Returns the weight, or throws NoSuchElementException for an absent arc. */
    public double weightEdge(V from, V to)
    {
        if (!hasEdge(from, to))
            throw new NoSuchElementException("Unknown arc: " + from + " -> " + to);
        return edges.get(from).get(to);
    }

    /** Removes one arc, retaining its endpoints; returns false if absent. */
    public boolean removeEdge(V from, V to)
    {
        if (!hasEdge(from, to))
            return false;
        edges.get(from).remove(to);
        edgeCount--;
        return true;
    }

    /** Removes the vertex and all incoming/outgoing arcs; false if absent. */
    public boolean removeVertex(V vertex)
    {
        if (vertex == null)
            throw new NullPointerException("Vertex must not be null");
        Map<V, Double> removed = edges.remove(vertex);
        if (removed == null)
            return false;
        edgeCount -= removed.size();
        for (Map<V, Double> adjacency : edges.values())
        {
            if (adjacency.remove(vertex) != null)
                edgeCount--;
        }
        return true;
    }

    /** Returns a live read-only view, ordered by the graph comparator. */
    public Set<V> vertices()
    {
        return Collections.unmodifiableSet(edges.keySet());
    }

    /**
     * Returns a read-only neighbour view. Unknown vertices are rejected.
     * Obtain a new view after removing and re-adding the vertex.
     */
    public Set<V> adjacentsTo(V vertex)
    {
        checkVertex(vertex);
        return Collections.unmodifiableSet(edges.get(vertex).keySet());
    }

    /** Returns the outgoing degree of an existing vertex. */
    public int degreeOut(V vertex)
    {
        return adjacentsTo(vertex).size();
    }

    /** Counts incoming arcs by inspecting every adjacency map. */
    public int degreeIn(V vertex)
    {
        checkVertex(vertex);
        int count = 0;
        for (Map<V, Double> neighbours : edges.values())
        {
            if (neighbours.containsKey(vertex))
                count++;
        }
        return count;
    }

    private void checkVertex(V vertex)
    {
        if (!hasVertex(vertex))
            throw new IllegalArgumentException("Unknown vertex: " + vertex);
    }

    // Validate the entire graph, including components unreachable from start.
    private void checkDijkstra(V start)
    {
        checkVertex(start);
        for (Map<V, Double> neighbours : edges.values())
        {
            for (double weight : neighbours.values())
            {
                if (weight < 0)
                    throw new IllegalArgumentException("Dijkstra requires nonnegative weights");
            }
        }
    }

    // Mutable working state belongs to one search, never to the graph itself.
    private class Search
    {
        private final SortedMap<V, Double> distances = new TreeMap<>(order);
        private final Map<V, V> previous = new TreeMap<>(order);

        private Search(V start)
        {
            for (V vertex : edges.keySet())
            {
                distances.put(vertex, Double.POSITIVE_INFINITY);
            }
            distances.put(start, 0.0);
        }
    }

    /**
     * Dijkstra using a linear scan to select each minimum-distance vertex.
     * Unreachable distances remain positive infinity. Negative weights anywhere
     * in the graph are rejected. A nonrepresentable candidate sum throws
     * ArithmeticException. The returned result is an independent snapshot.
     */
    public ShortestPaths<V> dijkstra(V start)
    {
        checkDijkstra(start);
        Search result = new Search(start);
        Set<V> settled = new HashSet<>();
        while (true)
        {
            V selected = null;
            double minimum = Double.POSITIVE_INFINITY;
            for (Map.Entry<V, Double> entry : result.distances.entrySet())
            {
                if (!settled.contains(entry.getKey()) && entry.getValue() < minimum)
                {
                    selected = entry.getKey();
                    minimum = entry.getValue();
                }
            }
            // No finite candidate remains: the remaining vertices are unreachable.
            if (selected == null)
                break;
            settled.add(selected);
            for (Map.Entry<V, Double> edge : edges.get(selected).entrySet())
            {
                if (!settled.contains(edge.getKey()))
                    relax(result, selected, minimum, edge);
            }
        }
        return new ShortestPaths<>(result.distances, result.previous);
    }

    // Queue priorities are immutable snapshots, not references to mutable distances.
    private class QueueEntry implements Comparable<QueueEntry>
    {
        private final V vertex;
        private final double distance;

        private QueueEntry(V vertex, double distance)
        {
            this.vertex = vertex;
            this.distance = distance;
        }

        @Override
        public int compareTo(QueueEntry other)
        {
            int comparison = Double.compare(distance, other.distance);
            if (comparison != 0)
                return comparison;
            return order.compare(vertex, other.vertex);
        }
    }

    /**
     * Dijkstra with a priority queue, with the same contract as dijkstra.
     * Every improvement inserts a new entry. Old entries are skipped when their
     * vertex has already been settled; no decrease-key operation is required.
     */
    public ShortestPaths<V> dijkstraHeap(V start)
    {
        checkDijkstra(start);
        Search result = new Search(start);
        Set<V> settled = new HashSet<>();
        PriorityQueue<QueueEntry> queue = new PriorityQueue<>();
        queue.add(new QueueEntry(start, 0.0));
        while (!queue.isEmpty())
        {
            QueueEntry selected = queue.remove();
            if (settled.contains(selected.vertex))
                continue;
            settled.add(selected.vertex);
            for (Map.Entry<V, Double> edge : edges.get(selected.vertex).entrySet())
            {
                if (settled.contains(edge.getKey()))
                    continue;
                if (relax(result, selected.vertex, selected.distance, edge))
                    queue.add(new QueueEntry(edge.getKey(), result.distances.get(edge.getKey())));
            }
        }
        return new ShortestPaths<>(result.distances, result.previous);
    }

    // Both selection strategies use exactly the same relaxation rule.
    private boolean relax(Search result, V from, double distance, Map.Entry<V, Double> edge)
    {
        double candidate = distance + edge.getValue();
        if (candidate == Double.POSITIVE_INFINITY)
            throw new ArithmeticException("Path cost overflow");
        if (candidate >= result.distances.get(edge.getKey()))
            return false;
        result.distances.put(edge.getKey(), candidate);
        result.previous.put(edge.getKey(), from);
        return true;
    }
}
