package tests;

import ds.NavigationNetwork;
import ds.Road;
import ds.Route;
import io.NavigationNetworkReader;
import java.io.StringReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Small exact examples verify incident behaviour without relying on map data. */
final class NavigationChecks
{
    private static int checks;

    private NavigationChecks()
    {
    }

    private static void check(boolean condition)
    {
        checks++;
        if (!condition)
            throw new AssertionError("Navigation contract failed");
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception error)
        {
            if (type.isInstance(error))
                return;
            throw new AssertionError(error);
        }
        throw new AssertionError("Expected " + type.getName());
    }

    static int run() throws IOException
    {
        NavigationNetwork network = new NavigationNetwork();
        for (String name : List.of("A", "B", "C", "D"))
        {
            network.addLocality(name);
        }
        Road motorway = new Road("motorway", "A", "B", "AP-test", 10);
        Road national = new Road("national", "A", "B", "N-test", 15);
        Road finish = new Road("finish", "B", "C", "local", 5);
        network.addRoad(motorway);
        network.addRoad(national);
        network.addRoad(finish);
        Route original = network.route("A", "C");
        check(original.cost == 15 && original.kilometres() == 15);
        check(original.legs.get(0).roadId.equals("motorway"));
        Road removed = network.removeRoad("motorway");
        check(network.route("A", "C").cost == 20);
        network.addRoad(removed);
        check(network.route("A", "C").cost == 15);
        motorway.setClosed(true);
        check(network.route("A", "C").legs.get(0).roadId.equals("national"));
        motorway.setClosed(false);
        motorway.setPenalty(3);
        Route penalised = network.route("A", "C");
        check(penalised.cost == 18 && penalised.kilometres() == 15);
        motorway.setPenalty(1000);
        check(network.route("A", "C").cost == 20);
        check(network.route("A", "C").legs.get(0).roadId.equals("national"));
        motorway.setPenalty(0);
        check(network.route("A", "C").cost == original.cost);
        check(penalised.cost == 18 && penalised.legs.get(0).penalty == 3);
        check(original.legs.get(0).penalty == 0);
        motorway.setClosed(true);
        national.setClosed(true);
        check(!network.route("A", "C").reachable());
        check(network.route("A", "C").kilometres() == Double.POSITIVE_INFINITY);
        check(network.route("A", "A").localities.equals(List.of("A")));
        check(!network.route("C", "A").reachable());
        check(!network.route("A", "D").reachable());
        check(motorway.kilometres() == 10);
        rejects(IllegalArgumentException.class, () -> motorway.setPenalty(-1));
        rejects(IllegalArgumentException.class, () -> motorway.setPenalty(Double.NaN));
        rejects(IllegalArgumentException.class, () -> motorway.setPenalty(Double.POSITIVE_INFINITY));
        check(motorway.penalty() == 0);
        rejects(IllegalArgumentException.class, () -> network.addRoad(motorway));
        rejects(IllegalArgumentException.class, () -> network.route("Z", "A"));
        rejects(UnsupportedOperationException.class, () -> original.legs.clear());
        String json = "{\"directed\":true,\"vertices\":[\"A\",\"B\"],\"roads\":["
                + "{\"id\":\"a\",\"from\":\"A\",\"to\":\"B\",\"name\":\"AP\",\"km\":10},"
                + "{\"id\":\"b\",\"from\":\"A\",\"to\":\"B\",\"name\":\"N\",\"km\":15}]}";
        NavigationNetwork loaded = NavigationNetworkReader.read(new StringReader(json));
        check(loaded.roads().size() == 2);
        loaded.road("a").setClosed(true);
        check(loaded.route("A", "B").cost == 15);
        rejects(IllegalArgumentException.class, () -> NavigationNetworkReader.read(
                new StringReader(json.replace("\"id\":\"b\"", "\"id\":\"a\""))));
        rejects(IllegalArgumentException.class, () -> NavigationNetworkReader.read(
                new StringReader(json.replace("true", "false"))));
        check(motorway.usesReference("AP-test"));
        check(!motorway.usesReference("AP"));
        NavigationNetwork real = NavigationNetworkReader.read(Path.of("data/northern-spain.json"));
        check(real.localities().size() == 42 && real.roads().size() == 120);
        double originalCost = real.route("Gijón", "Madrid").cost;
        check(real.route("Oviedo", "León").legs.get(0).roadId.equals("pajares-south"));
        List<Road> affected = new ArrayList<>();
        for (Road road : real.roads())
        {
            if (road.usesCorridor("N-630-Pajares"))
                affected.add(road);
        }
        check(affected.size() == 4);
        check(!real.road("road-02").usesCorridor("N-630-Pajares"));
        for (Road road : affected)
        {
            real.removeRoad(road.id());
        }
        check(real.route("Oviedo", "León").legs.get(0).roadId.equals("road-59"));
        double detourCost = real.route("Gijón", "Madrid").cost;
        check(detourCost > originalCost);
        for (Road road : affected)
        {
            real.addRoad(road);
            road.setPenalty(1000);
        }
        check(real.route("Gijón", "Madrid").cost == detourCost);
        for (Road road : affected)
        {
            road.setPenalty(0);
        }
        check(real.route("Gijón", "Madrid").cost == originalCost);
        rejects(IllegalArgumentException.class,
                () -> new Road("x", "A", "B", "N", 1, List.of("")));
        return checks;
    }
}
