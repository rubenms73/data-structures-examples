package app;

import ds.NavigationNetwork;
import ds.Road;
import ds.Route;
import io.NavigationNetworkReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;

/** A classroom navigation demo: remove a road, restore it and penalise it temporarily. */
public final class Main
{
    private Main()
    {
    }

    /** Optional arguments: JSON file, origin, destination and an optional incident road ID. */
    public static void main(String[] args) throws IOException
    {
        if (args.length != 0 && args.length != 3 && args.length != 4)
            throw new IllegalArgumentException("Use: Main [network.json origin destination [road-id]]");
        Path file = Path.of("data/northern-spain.json");
        String from = "Gijón";
        String to = "Madrid";
        if (args.length >= 3)
        {
            file = Path.of(args[0]);
            from = args[1];
            to = args[2];
        }
        NavigationNetwork network = NavigationNetworkReader.read(file);
        System.out.println("Network: " + network.localities().size() + " localities, "
                + network.roads().size() + " directed road alternatives");
        System.out.println("Map-derived distances: OpenStreetMap / OSRM snapshot; no live traffic.");
        System.out.println("Cost = kilometres + teaching penalty (equivalent-distance units).");
        System.out.println("Parallel alternatives from Oviedo to León:");
        for (Road road : network.roads())
        {
            if (road.from().equals("Oviedo") && road.to().equals("León"))
                System.out.printf(Locale.ROOT, "  %s | %s | %.3f km%n", road.id(), road.name(), road.kilometres());
        }
        Route baseline = network.route(from, to);
        show("1. Original route", baseline);
        if (!baseline.reachable() || baseline.legs.isEmpty())
            return;
        String roadId = baseline.legs.get(0).roadId;
        String corridor = null;
        // Prefer Pajares: the real kilometre weights make it shorter than AP-66.
        for (String candidate : List.of("N-630-Pajares", "AP-66"))
        {
            for (Route.Leg leg : baseline.legs)
            {
                if (network.road(leg.roadId).usesCorridor(candidate))
                {
                    roadId = leg.roadId;
                    corridor = candidate;
                    break;
                }
            }
            if (corridor != null)
                break;
        }
        if (args.length == 4)
            roadId = args[3];
        List<Road> affected = new ArrayList<>();
        if (args.length != 4 && corridor != null)
        {
            // Corridors overlap. An incident must affect EVERY connection using it,
            // including the separately measured reverse directions.
            for (Road road : network.roads())
            {
                if (road.usesCorridor(corridor))
                    affected.add(road);
            }
            System.out.println("\nIncident: " + corridor + " in both directions (" + affected.size()
                    + " represented connections)");
        }
        else
        {
            affected.add(network.road(roadId));
            System.out.println("\nIncident: directed connection " + roadId);
        }
        for (Road road : affected)
        {
            network.removeRoad(road.id());
        }
        try
        {
            show("2. Affected connections removed", network.route(from, to));
        }
        finally
        {
            for (Road road : affected)
            {
                network.addRoad(road);
            }
        }
        show("3. Connections restored", network.route(from, to));
        // The JSON loader starts every road with zero penalty.
        for (Road road : affected)
        {
            road.setPenalty(1000);
        }
        try
        {
            show("4. Temporary penalty of 1000 per affected connection", network.route(from, to));
        }
        finally
        {
            for (Road road : affected)
            {
                road.setPenalty(0);
            }
        }
        show("5. Penalty removed", network.route(from, to));
        System.out.println("Both algorithms give the same distances for every scenario.");
    }

    private static void show(String title, Route route)
    {
        System.out.println("\n" + title);
        if (!route.reachable())
        {
            System.out.println("No route from " + route.from + " to " + route.to);
            return;
        }
        System.out.println(route.localities);
        for (Route.Leg leg : route.legs)
        {
            System.out.printf(Locale.ROOT, "  %s -> %s | %s | %s | %.3f km | penalty %.3f%n",
                    leg.from, leg.to, leg.roadId, leg.name, leg.kilometres, leg.penalty);
        }
        System.out.printf(Locale.ROOT, "Physical distance: %.3f km; routing cost: %.3f%n",
                route.kilometres(), route.cost);
    }
}
