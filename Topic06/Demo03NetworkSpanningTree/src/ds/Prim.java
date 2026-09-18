package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Prim's minimum spanning tree algorithm using a matrix and linear scans. */
public final class Prim
{
    private Prim()
    {
    }

    /** Immutable result. Long total avoids overflow when adding int costs. */
    public record Result(List<Network.Link> links, long totalCost)
    {
        public Result
        {
            if (links == null)
                throw new IllegalArgumentException("Null links");
            links = Collections.unmodifiableList(new ArrayList<>(links));
        }
    }

    /**
     * Connects every device at minimum total cost, without changing the network.
     * Empty and singleton networks return zero cost and no links.
     * A disconnected network throws IllegalStateException: no spanning tree exists.
     * Equal costs are resolved deterministically by vertex index.
     */
    public static Result minimumSpanningTree(Network network)
    {
        if (network == null)
            throw new IllegalArgumentException("Null network");
        int n = network.size();
        boolean[] selected = new boolean[n];
        long[] cheapest = new long[n];
        int[] parent = new int[n];
        for (int i = 0; i < n; i++)
        {
            cheapest[i] = Long.MAX_VALUE;
            parent[i] = -1;
        }
        if (n > 0)
            cheapest[0] = 0;
        List<Network.Link> links = new ArrayList<>();
        long total = 0;
        for (int count = 0; count < n; count++)
        {
            int next = -1;
            long minimum = Long.MAX_VALUE;
            for (int vertex = 0; vertex < n; vertex++)
            {
                if (!selected[vertex] && cheapest[vertex] < minimum)
                {
                    next = vertex;
                    minimum = cheapest[vertex];
                }
            }
            if (next == -1)
                throw new IllegalStateException("Disconnected network: no spanning tree");
            selected[next] = true;
            if (parent[next] != -1)
            {
                links.add(new Network.Link(parent[next], next, (int) minimum));
                total += minimum;
            }
            for (int neighbour = 0; neighbour < n; neighbour++)
            {
                int cableCost = network.cost(next, neighbour);
                // This is the cost of ONE cable, not a source-to-device distance.
                if (!selected[neighbour] && cableCost >= 0 && cableCost < cheapest[neighbour])
                {
                    cheapest[neighbour] = cableCost;
                    parent[neighbour] = next;
                }
            }
        }
        return new Result(links, total);
    }
}
