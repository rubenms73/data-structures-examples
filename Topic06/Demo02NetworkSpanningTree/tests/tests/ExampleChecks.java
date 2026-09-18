package tests;

import java.util.List;
import java.util.Random;
import app.ExampleNetwork;
import ds.Network;
import ds.Prim;

/** Checks optimality against exhaustive edge subsets on small networks. */
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
        expect(IllegalArgumentException.class, () -> new Network(null));
        expect(IllegalArgumentException.class, () -> new Network(new String[] { "A", "A" }));
        expect(IllegalArgumentException.class, () -> new Network(new String[] { null }));
        expect(IllegalArgumentException.class, () -> Prim.minimumSpanningTree(null));
        check(Prim.minimumSpanningTree(new Network(new String[0])).totalCost() == 0);
        check(Prim.minimumSpanningTree(new Network(new String[] { "A" })).links().isEmpty());
        String[] names = { "A", "B", "C" };
        Network network = new Network(names);
        names[0] = "changed";
        check(network.name(0).equals("A"));
        expect(IllegalArgumentException.class, () -> network.addLink(-1, 0, 1));
        expect(IllegalArgumentException.class, () -> network.addLink(0, 3, 1));
        expect(IllegalArgumentException.class, () -> network.addLink(0, 1, -1));
        check(!network.addLink(0, 0, 1));
        check(network.addLink(0, 1, 0));
        check(network.cost(1, 0) == 0);
        check(!network.addLink(1, 0, 20));
        check(network.cost(0, 1) == 0);
        expect(IllegalStateException.class, () -> Prim.minimumSpanningTree(network));
        network.addLink(1, 2, Integer.MAX_VALUE);
        check(Prim.minimumSpanningTree(network).totalCost() == Integer.MAX_VALUE);
        network.removeLink(0, 1);
        network.addLink(0, 1, Integer.MAX_VALUE);
        check(Prim.minimumSpanningTree(network).totalCost() == 2L * Integer.MAX_VALUE);
        check(network.removeLink(1, 0));
        check(network.cost(0, 1) == -1 && network.cost(1, 0) == -1);
        check(!network.removeLink(0, 1));
        Network example = ExampleNetwork.create();
        check(example.size() == 6 && example.links().size() == 9);
        check(Prim.minimumSpanningTree(example).totalCost() == 11);
        verify(example, Prim.minimumSpanningTree(example));
        check(bruteForce(example) == 11);
        Prim.Result saved = Prim.minimumSpanningTree(example);
        example.removeLink(1, 2);
        check(Prim.minimumSpanningTree(example).totalCost() == 14);
        check(bruteForce(example) == 14);
        check(saved.totalCost() == 11 && saved.links().size() == 5);
        expect(UnsupportedOperationException.class, () -> saved.links().clear());
        example.addLink(1, 2, 1);
        check(Prim.minimumSpanningTree(example).equals(saved));
        Random random = new Random(606);
        for (int trial = 0; trial < 150; trial++)
        {
            Network graph = new Network(new String[] { "A", "B", "C", "D", "E" });
            for (int i = 0; i < graph.size(); i++)
            {
                for (int j = i + 1; j < graph.size(); j++)
                {
                    if (random.nextBoolean())
                        graph.addLink(i, j, random.nextInt(6));
                }
            }
            long best = bruteForce(graph);
            if (best == Long.MAX_VALUE)
                expect(IllegalStateException.class, () -> Prim.minimumSpanningTree(graph));
            else
            {
                List<Network.Link> before = graph.links();
                Prim.Result result = Prim.minimumSpanningTree(graph);
                verify(graph, result);
                check(result.totalCost() == best);
                check(graph.links().equals(before));
            }
        }
        System.out.println("All " + checks + " checks passed.");
    }

    private static void verify(Network graph, Prim.Result result)
    {
        check(result.links().size() == graph.size() - 1);
        boolean[][] connected = new boolean[graph.size()][graph.size()];
        long total = 0;
        for (Network.Link link : result.links())
        {
            check(graph.cost(link.from(), link.to()) == link.cost());
            connected[link.from()][link.to()] = true;
            connected[link.to()][link.from()] = true;
            total += link.cost();
        }
        check(isConnected(connected));
        check(total == result.totalCost());
    }

    // Enumerate all edge subsets of size V-1; connected ones are trees.
    private static long bruteForce(Network graph)
    {
        List<Network.Link> edges = graph.links();
        long best = Long.MAX_VALUE;
        for (int mask = 0; mask < (1 << edges.size()); mask++)
        {
            if (Integer.bitCount(mask) != graph.size() - 1)
                continue;
            boolean[][] connected = new boolean[graph.size()][graph.size()];
            long total = 0;
            for (int e = 0; e < edges.size(); e++)
            {
                if ((mask & (1 << e)) != 0)
                {
                    Network.Link link = edges.get(e);
                    connected[link.from()][link.to()] = true;
                    connected[link.to()][link.from()] = true;
                    total += link.cost();
                }
            }
            if (isConnected(connected) && total < best)
                best = total;
        }
        return best;
    }

    // Boolean transitive closure is independent of Prim's greedy selection.
    private static boolean isConnected(boolean[][] connected)
    {
        for (int k = 0; k < connected.length; k++)
        {
            for (int i = 0; i < connected.length; i++)
            {
                for (int j = 0; j < connected.length; j++)
                {
                    connected[i][j] |= connected[i][k] && connected[k][j];
                }
            }
        }
        for (int i = 1; i < connected.length; i++)
        {
            if (!connected[0][i])
                return false;
        }
        return true;
    }
}
