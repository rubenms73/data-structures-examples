package tests;

import ds.ShortestPaths;
import ds.WeightedGraph;
import io.RoadNetworkReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

/** Contract checks and independent all-pairs verification of both algorithms. */
public final class ExampleChecks
{
    private static int checks;

    private ExampleChecks()
    {
    }

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

    private static void contractChecks()
    {
        WeightedGraph<Integer> graph = new WeightedGraph<>(Comparator.naturalOrder());
        rejects(NullPointerException.class, () -> new WeightedGraph<Integer>((Comparator<Integer>) null));
        rejects(NullPointerException.class, () -> new WeightedGraph<Integer>((WeightedGraph<Integer>) null));
        rejects(IllegalArgumentException.class, () -> graph.dijkstra(1));
        rejects(IllegalArgumentException.class, () -> graph.dijkstraHeap(1));
        for (double value : new double[] {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
        {
            rejects(IllegalArgumentException.class, () -> graph.addEdge(1, 2, value));
        }
        rejects(NullPointerException.class, () -> graph.addEdge(null, 1, 1));
        equal(false, graph.addEdge(1, 1, 0));
        equal(0, graph.vertexCount());
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 1, 4);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 3, 1); // improves the queued distance 10 to 4
        equal(false, graph.addEdge(1, 2, 100));
        equal(3.0, graph.weightEdge(1, 2));
        equal(2, graph.degreeOut(1));
        equal(2, graph.degreeIn(3));
        Set<Integer> vertices = graph.vertices();
        Set<Integer> neighbours = graph.adjacentsTo(1);
        rejects(UnsupportedOperationException.class, vertices::clear);
        rejects(UnsupportedOperationException.class, neighbours::clear);
        graph.addVertex(4);
        equal(true, vertices.contains(4));
        rejects(IllegalArgumentException.class, () -> graph.adjacentsTo(99));
        rejects(java.util.NoSuchElementException.class, () -> graph.weightEdge(4, 1));
        rejects(NullPointerException.class, () -> graph.hasVertex(null));
        WeightedGraph<Integer> copy = new WeightedGraph<>(graph);
        copy.removeVertex(1);
        equal(1, copy.edgeCount());
        equal(4, graph.edgeCount());
        equal(false, copy.removeVertex(99));
        equal(true, copy.removeEdge(2, 3));
        equal(false, copy.removeEdge(2, 3));
        equal(0, copy.edgeCount());
        ShortestPaths<Integer> result = graph.dijkstraHeap(1);
        equal(List.of(1, 2, 3), result.pathTo(3));
        equal(4.0, result.distanceTo(3));
        equal(List.of(1), result.pathTo(1));
        equal(List.of(), result.pathTo(4));
        equal(Double.POSITIVE_INFINITY, result.distanceTo(4));
        rejects(IllegalArgumentException.class, () -> result.pathTo(99));
        rejects(NullPointerException.class, () -> result.pathTo(null));
        rejects(UnsupportedOperationException.class, () -> result.distances().clear());
        rejects(UnsupportedOperationException.class, () -> result.predecessors().clear());
        result.pathTo(3).clear();
        equal(List.of(1, 2, 3), result.pathTo(3));
        graph.removeVertex(2);
        equal(1, graph.edgeCount());
        equal(4.0, result.distanceTo(3)); // independent result, not a live graph cache
        equal(10.0, graph.dijkstra(1).distanceTo(3));
        graph.addEdge(8, 9, -1); // negative edge in an unreachable component
        rejects(IllegalArgumentException.class, () -> graph.dijkstra(1));
        rejects(IllegalArgumentException.class, () -> graph.dijkstraHeap(1));
        WeightedGraph<Integer> overflow = new WeightedGraph<>(Comparator.naturalOrder());
        overflow.addEdge(1, 2, Double.MAX_VALUE);
        overflow.addEdge(2, 3, Double.MAX_VALUE);
        rejects(ArithmeticException.class, () -> overflow.dijkstra(1));
        rejects(ArithmeticException.class, () -> overflow.dijkstraHeap(1));
        Comparator<Number> order = (a, b) -> Double.compare(a.doubleValue(), b.doubleValue());
        WeightedGraph<Integer> broadComparator = new WeightedGraph<>(order);
        broadComparator.addEdge(1, 2, 0);
        equal(0.0, broadComparator.dijkstraHeap(1).distanceTo(2));
    }

    private static void randomChecks()
    {
        Random random = new Random(17);
        for (int trial = 0; trial < 20; trial++)
        {
            WeightedGraph<Integer> graph = new WeightedGraph<>(Comparator.naturalOrder());
            double[][] expected = new double[8][8];
            for (int i = 0; i < 8; i++)
            {
                graph.addVertex(i);
                Arrays.fill(expected[i], Double.POSITIVE_INFINITY);
                expected[i][i] = 0;
            }
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    if (i != j && random.nextBoolean())
                    {
                        double weight = random.nextInt(10);
                        graph.addEdge(i, j, weight);
                        expected[i][j] = weight;
                    }
                }
            }
            // Floyd-Warshall is an independent oracle, not a second Dijkstra.
            for (int k = 0; k < 8; k++)
            {
                for (int i = 0; i < 8; i++)
                {
                    for (int j = 0; j < 8; j++)
                    {
                        expected[i][j] = Math.min(expected[i][j], expected[i][k] + expected[k][j]);
                    }
                }
            }
            for (int i = 0; i < 8; i++)
            {
                ShortestPaths<Integer> linear = graph.dijkstra(i);
                ShortestPaths<Integer> heap = graph.dijkstraHeap(i);
                equal(linear.distances(), heap.distances());
                for (ShortestPaths<Integer> result : List.of(linear, heap))
                {
                    for (int j = 0; j < 8; j++)
                    {
                        equal(expected[i][j], result.distanceTo(j));
                        List<Integer> path = result.pathTo(j);
                        equal(expected[i][j] != Double.POSITIVE_INFINITY, !path.isEmpty());
                        if (!path.isEmpty())
                        {
                            equal(i, path.get(0));
                            equal(j, path.get(path.size() - 1));
                            double cost = 0;
                            for (int step = 1; step < path.size(); step++)
                            {
                                cost += graph.weightEdge(path.get(step - 1), path.get(step));
                            }
                            equal(expected[i][j], cost);
                        }
                    }
                }
            }
        }
    }

    private static void jsonChecks() throws IOException
    {
        WeightedGraph<String> roads = RoadNetworkReader.read(Path.of("data/asturias-leon.json"));
        equal(29, roads.vertexCount());
        equal(80, roads.edgeCount());
        for (String from : roads.vertices())
        {
            for (String to : roads.adjacentsTo(from))
            {
                equal(roads.weightEdge(from, to), roads.weightEdge(to, from));
            }
            equal(roads.dijkstra(from).distances(), roads.dijkstraHeap(from).distances());
        }
        String valid = "{\"directed\":true,\"vertices\":[\"A\",\"B\",\"C\"],"
                + "\"roads\":[{\"from\":\"A\",\"to\":\"B\",\"km\":0}]}";
        WeightedGraph<String> directed = RoadNetworkReader.read(new StringReader(valid));
        equal(1, directed.edgeCount());
        equal(List.of(), directed.dijkstraHeap("B").pathTo("A"));
        equal(List.of(), directed.dijkstra("A").pathTo("C"));
        equal(0.0, directed.dijkstra("A").distanceTo("B"));
        for (String invalid : List.of("null", "{}", valid.replace("\"km\":0", "\"km\":-1"),
                valid.replace("\"km\":0", "\"km\":1e999"),
                valid.replace("\"km\":0", "\"km\":\"2\""),
                valid.replace("\"km\":0", "\"km\":null"),
                valid.replace("\"from\":\"A\"", "\"from\":\"Z\""),
                valid.replace("\"to\":\"B\"", "\"to\":\"A\""),
                valid.replace("\"A\",\"B\",\"C\"", "\"A\",\"B\",\"A\""),
                valid.replace("\"A\",\"B\",\"C\"", "\" A\",\"B\",\"C\""),
                valid.replace("\"directed\":true", "\"directed\":\"true\"")))
        {
            rejects(IllegalArgumentException.class, () -> RoadNetworkReader.read(new StringReader(invalid)));
        }
        rejects(com.google.gson.JsonParseException.class,
                () -> RoadNetworkReader.read(new StringReader(valid + " trailing")));
        String duplicate = valid.replace("}]}", "},{\"from\":\"A\",\"to\":\"B\",\"km\":3}]}");
        rejects(IllegalArgumentException.class, () -> RoadNetworkReader.read(new StringReader(duplicate)));
        String reverse = valid.replace("true", "false")
                .replace("}]}", "},{\"from\":\"B\",\"to\":\"A\",\"km\":3}]}");
        rejects(IllegalArgumentException.class, () -> RoadNetworkReader.read(new StringReader(reverse)));
    }

    public static void main(String[] args) throws IOException
    {
        contractChecks();
        randomChecks();
        jsonChecks();
        System.out.println("All " + checks + " checks passed.");
    }
}
