package ds;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Directed weighted graph. Vertex equality/hashCode must remain stable. */
public class WeightedGraph<V>
{
    private final Map<V, Map<V, Double>> edges = new LinkedHashMap<>();
    private int edgeCount;

    public WeightedGraph()
    {
    }

    /** Copies adjacency maps as well as the outer map; vertex objects are shared. */
    public WeightedGraph(WeightedGraph<V> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        for (V vertex : source.edges.keySet())
        {
            edges.put(vertex, new LinkedHashMap<>(source.edges.get(vertex)));
        }
        edgeCount = source.edgeCount;
    }

    public boolean addVertex(V vertex)
    {
        if (vertex == null)
            throw new NullPointerException("Vertex must not be null");
        if (edges.containsKey(vertex))
            return false;
        edges.put(vertex, new LinkedHashMap<>());
        return true;
    }

    /** Add missing endpoints. An existing edge is left unchanged. */
    public boolean addEdge(V from, V to, double weight)
    {
        if (from == null || to == null)
            throw new NullPointerException("Endpoints must not be null");
        if (!(weight >= 0) || weight == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Weight must be nonnegative and finite");
        addVertex(from);
        addVertex(to);
        if (edges.get(from).containsKey(to))
            return false;
        edges.get(from).put(to, weight);
        edgeCount++;
        return true;
    }

    public int vertexCount()
    {
        return edges.size();
    }

    public int edgeCount()
    {
        return edgeCount;
    }

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

    /** Return a copy so callers cannot mutate the graph through a key-set view. */
    public Set<V> vertices()
    {
        return new LinkedHashSet<>(edges.keySet());
    }

    private void checkVertex(V vertex)
    {
        if (vertex == null)
            throw new NullPointerException("Vertex must not be null");
        if (!edges.containsKey(vertex))
            throw new IllegalArgumentException("Unknown vertex: " + vertex);
    }

    /** Dijkstra with a linear minimum search: O(V² + E), without a heap. */
    public Map<V, Double> distancesFrom(V start)
    {
        return shortestPaths(start).distances;
    }

    public List<V> shortestPath(V start, V end)
    {
        checkVertex(end);
        Search result = shortestPaths(start);
        List<V> path = new ArrayList<>();
        if (result.distances.get(end) == Double.POSITIVE_INFINITY)
            return path;
        V current = end;
        while (!current.equals(start))
        {
            path.add(current);
            current = result.previous.get(current);
        }
        path.add(start);
        List<V> forward = new ArrayList<>();
        for (int i = path.size() - 1; i >= 0; i--)
        {
            forward.add(path.get(i));
        }
        return forward;
    }

    private class Search
    {
        final Map<V, Double> distances = new LinkedHashMap<>();
        final Map<V, V> previous = new LinkedHashMap<>();
    }

    private Search shortestPaths(V start)
    {
        checkVertex(start);
        Search result = new Search();
        Set<V> visited = new LinkedHashSet<>();
        for (V vertex : edges.keySet())
        {
            result.distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        result.distances.put(start, 0.0);
        while (visited.size() < edges.size())
        {
            V selected = null;
            double minimum = Double.POSITIVE_INFINITY;
            for (V vertex : edges.keySet())
            {
                double distance = result.distances.get(vertex);
                if (!visited.contains(vertex) && distance < minimum)
                {
                    minimum = distance;
                    selected = vertex;
                }
            }
            // No reachable vertex remains; disconnected vertices stay at infinity.
            if (selected == null)
                break;
            visited.add(selected);
            for (Map.Entry<V, Double> edge : edges.get(selected).entrySet())
            {
                if (visited.contains(edge.getKey()))
                    continue;
                double candidate = minimum + edge.getValue();
                if (candidate == Double.POSITIVE_INFINITY)
                    throw new ArithmeticException("Path cost overflow");
                if (candidate < result.distances.get(edge.getKey()))
                {
                    result.distances.put(edge.getKey(), candidate);
                    result.previous.put(edge.getKey(), selected);
                }
            }
        }
        return result;
    }
}
