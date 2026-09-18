package ds;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Independent result of one Dijkstra run: distances and predecessor links.
 * Maps cannot be changed by callers. Vertex objects themselves are shared.
 * @param <V> vertex type
 */
public final class ShortestPaths<V>
{
    private final Map<V, Double> distances;
    private final Map<V, V> predecessors;

    // Only the graph constructs results, after completing a successful search.
    ShortestPaths(Map<V, Double> distances, Map<V, V> predecessors)
    {
        this.distances = Collections.unmodifiableMap(new LinkedHashMap<>(distances));
        this.predecessors = Collections.unmodifiableMap(new LinkedHashMap<>(predecessors));
    }

    /** Ordered, read-only distances; positive infinity means unreachable. */
    public Map<V, Double> distances()
    {
        return distances;
    }

    /** Read-only predecessor links; source and unreachable vertices have no entry. */
    public Map<V, V> predecessors()
    {
        return predecessors;
    }

    /** Returns a distance, rejecting null and vertices absent from this result. */
    public double distanceTo(V target)
    {
        if (target == null)
            throw new NullPointerException("Target must not be null");
        Double distance = distances.get(target);
        if (distance == null)
            throw new IllegalArgumentException("Unknown target: " + target);
        return distance;
    }

    /**
     * Reconstructs a new source-to-target list, without another graph search.
     * Returns an empty list if unreachable, or [source] for the source itself.
     */
    public List<V> pathTo(V target)
    {
        LinkedList<V> path = new LinkedList<>();
        if (distanceTo(target) == Double.POSITIVE_INFINITY)
            return path;
        V current = target;
        while (current != null)
        {
            path.addFirst(current);
            current = predecessors.get(current);
        }
        return path;
    }
}
