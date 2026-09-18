package ds;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/** Simple directed graph. The comparator must be consistent with equals. */
public class Graph<V>
{
    private final Comparator<? super V> order;
    private final SortedMap<V, Set<V>> adjacency;

    /** Creates an empty graph with deterministic vertex and neighbour order. */
    public Graph(Comparator<? super V> order)
    {
        if (order == null)
            throw new IllegalArgumentException("Null comparator");
        this.order = order;
        adjacency = new TreeMap<>(order);
    }

    /** Adds a non-null vertex; returns false if already present. */
    public boolean addVertex(V vertex)
    {
        if (vertex == null)
            throw new IllegalArgumentException("Null vertex");
        if (adjacency.containsKey(vertex))
            return false;
        adjacency.put(vertex, new TreeSet<>(order));
        return true;
    }

    /** Adds an arc and missing endpoints. Loops and duplicates return false. */
    public boolean addEdge(V from, V to)
    {
        if (from == null || to == null)
            throw new IllegalArgumentException("Null endpoint");
        if (order.compare(from, to) == 0)
            return false;
        addVertex(from);
        addVertex(to);
        return adjacency.get(from).add(to);
    }

    /** Returns a read-only view in comparator order. */
    public Set<V> vertices()
    {
        return Collections.unmodifiableSet(adjacency.keySet());
    }

    /** Returns a read-only outgoing-neighbour view; rejects unknown vertices. */
    public Set<V> neighbours(V vertex)
    {
        if (vertex == null || !adjacency.containsKey(vertex))
            throw new IllegalArgumentException("Unknown vertex");
        return Collections.unmodifiableSet(adjacency.get(vertex));
    }
}
