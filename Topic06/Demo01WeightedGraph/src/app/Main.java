package app;

import ds.ShortestPaths;
import ds.WeightedGraph;
import io.RoadNetworkReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;

/** Demonstrates adjacency maps and both Dijkstra implementations on a JSON network. */
public final class Main
{
    private Main()
    {
    }

    /** Optional arguments: JSON filename, source, target (in that order). */
    public static void main(String[] args) throws IOException
    {
        if (args.length != 0 && args.length != 3)
            throw new IllegalArgumentException("Use: Main [network.json source target]");
        Path file = Path.of("data/asturias-leon.json");
        String source = "Gijón";
        String target = "León";
        if (args.length == 3)
        {
            file = Path.of(args[0]);
            source = args[1];
            target = args[2];
        }
        WeightedGraph<String> graph = RoadNetworkReader.read(file);
        System.out.println("Road network: " + graph.vertexCount() + " localities, "
                + graph.edgeCount() + " directed arcs");
        System.out.println("Teaching data: illustrative distances in km, not measured road distances.");
        System.out.println("\nAdjacency lists (destination: km)");
        for (String vertex : graph.vertices())
        {
            System.out.print(vertex + " ->");
            for (String neighbour : graph.adjacentsTo(vertex))
            {
                System.out.printf(Locale.ROOT, " [%s: %.0f]", neighbour,
                        graph.weightEdge(vertex, neighbour));
            }
            System.out.println();
        }
        // Each search computes ALL distances once; pathTo reuses its result.
        ShortestPaths<String> linear = graph.dijkstra(source);
        ShortestPaths<String> heap = graph.dijkstraHeap(source);
        if (!linear.distances().equals(heap.distances()))
            throw new AssertionError("The two algorithms disagree");
        System.out.println("\nDistances from " + source + " (km)");
        for (String vertex : graph.vertices())
        {
            double distance = heap.distanceTo(vertex);
            if (distance == Double.POSITIVE_INFINITY)
                System.out.println(vertex + ": unreachable");
            else
                System.out.printf(Locale.ROOT, "%s: %.0f%n", vertex, distance);
        }
        System.out.println("\nLinear scan path: " + linear.pathTo(target));
        System.out.println("Heap path:        " + heap.pathTo(target));
        if (heap.distanceTo(target) == Double.POSITIVE_INFINITY)
            System.out.println("No route to " + target);
        else
            System.out.printf(Locale.ROOT, "Total distance: %.0f km%n", heap.distanceTo(target));
        System.out.println("Both algorithms give the same distances.");
    }
}
