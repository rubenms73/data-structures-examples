package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception e)
        {
            if (type.isInstance(e))
                return;
            throw new AssertionError(e);
        }
        throw new AssertionError("Expected " + type);
    }

    private static <E> List<E> collect(Iterable<E> source)
    {
        List<E> result = new ArrayList<>();
        for (E value : source)
        {
            result.add(value);
        }
        return result;
    }

    public static void main(String[] args)
    {
        WeightedGraph<Integer> graph = new WeightedGraph<>();
        rejects(IllegalArgumentException.class, () -> graph.addEdge(1, 2, -1));
        equal(0, graph.vertexCount());
        rejects(IllegalArgumentException.class, () -> graph.addEdge(1, 2, Double.NaN));
        rejects(NullPointerException.class, () -> graph.addEdge(null, 1, 1));
        graph.addEdge(1, 1, 0);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 1, 4);
        equal(3, graph.edgeCount());
        WeightedGraph<Integer> copy = new WeightedGraph<>(graph);
        copy.removeVertex(1);
        equal(0, copy.edgeCount());
        equal(3, graph.edgeCount());
        graph.vertices().clear();
        equal(2, graph.vertexCount());
        rejects(IllegalArgumentException.class, () -> graph.distancesFrom(99));
        Random random = new Random(17);
        for (int trial = 0; trial < 20; trial++)
        {
            WeightedGraph<Integer> g = new WeightedGraph<>();
            double[][] distances = new double[8][8];
            for (int i = 0; i < 8; i++)
            {
                g.addVertex(i);
                Arrays.fill(distances[i], Double.POSITIVE_INFINITY);
                distances[i][i] = 0;
            }
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (i != j && random.nextBoolean())
                    {
                        double weight = random.nextInt(10);
                        g.addEdge(i, j, weight);
                        distances[i][j] = weight;
                    }
                }
            }
            for (int k = 0; k < 8; k++)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                    }
                }
            }
            for (int i = 0; i < 8; i++)
            {
                Map<Integer, Double> actual = g.distancesFrom(i);
                for (int j = 0; j < 8; j++)
                {
                    equal(distances[i][j], actual.get(j));
                    List<Integer> path = g.shortestPath(i, j);
                    equal(distances[i][j] != Double.POSITIVE_INFINITY, !path.isEmpty());
                    if (!path.isEmpty())
                    {
                        equal(i, path.get(0));
                        equal(j, path.get(path.size() - 1));
                    }
                }
            }
        }
        System.out.println("All " + checks + " checks passed.");
    }
}
