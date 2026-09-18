package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Small undirected network with nonnegative integer installation costs. */
public class Network
{
    private final String[] names;
    // -1 means no cable. Zero is a valid installation cost.
    private final int[][] cost;

    /** Copies unique, nonempty device names. Vertex indices remain stable. */
    public Network(String[] names)
    {
        if (names == null)
            throw new IllegalArgumentException("Null names");
        for (int i = 0; i < names.length; i++)
        {
            if (names[i] == null || names[i].isBlank())
                throw new IllegalArgumentException("Missing device name");
            for (int j = 0; j < i; j++)
            {
                if (names[i].equals(names[j]))
                    throw new IllegalArgumentException("Duplicate device name");
            }
        }
        this.names = names.clone();
        cost = new int[names.length][names.length];
        for (int i = 0; i < names.length; i++)
        {
            for (int j = 0; j < names.length; j++)
            {
                cost[i][j] = -1;
            }
        }
    }

    /** Number of devices, including isolated ones. */
    public int size()
    {
        return names.length;
    }

    /** Device label for an existing vertex index. */
    public String name(int vertex)
    {
        checkVertex(vertex);
        return names[vertex];
    }

    /** Adds a cable in both directions; loops and duplicates return false. */
    public boolean addLink(int from, int to, int installationCost)
    {
        checkVertex(from);
        checkVertex(to);
        if (installationCost < 0)
            throw new IllegalArgumentException("Negative installation cost");
        if (from == to || cost[from][to] >= 0)
            return false;
        cost[from][to] = installationCost;
        cost[to][from] = installationCost;
        return true;
    }

    /** Removes both adjacency entries; returns false for a missing link. */
    public boolean removeLink(int from, int to)
    {
        checkVertex(from);
        checkVertex(to);
        if (cost[from][to] < 0)
            return false;
        cost[from][to] = -1;
        cost[to][from] = -1;
        return true;
    }

    /** Returns the cost, or -1 if absent. */
    public int cost(int from, int to)
    {
        checkVertex(from);
        checkVertex(to);
        return cost[from][to];
    }

    /** One entry per undirected link, in increasing endpoint order. */
    public List<Link> links()
    {
        List<Link> result = new ArrayList<>();
        for (int i = 0; i < size(); i++)
        {
            for (int j = i + 1; j < size(); j++)
            {
                if (cost[i][j] >= 0)
                    result.add(new Link(i, j, cost[i][j]));
            }
        }
        return Collections.unmodifiableList(result);
    }

    /** Immutable selected or available cable; endpoints are vertex indices. */
    public record Link(int from, int to, int cost)
    {
    }

    private void checkVertex(int vertex)
    {
        if (vertex < 0 || vertex >= size())
            throw new IllegalArgumentException("Unknown vertex index");
    }
}
