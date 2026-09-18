package io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ds.Road;
import ds.Route;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Exports already computed routes to a standalone offline HTML map.
 * This presentation helper does not run Dijkstra or modify the network.
 * Geometry and country outlines are display data, never algorithm inputs.
 */
public final class NavigationMapWriter
{
    private NavigationMapWriter()
    {
    }

    /**
     * Writes scenario snapshots and their affected road IDs into the HTML template.
     * All assets are embedded: opening the result needs no server or internet.
     * Gson's default HTML escaping prevents names from closing the JSON script tag.
     * Unreachable costs are omitted from JSON; the viewer displays "No route".
     * Missing optional geographic metadata is reported by the viewer, not invented.
     *
     * @param networkFile the same JSON document used to load the navigation network
     * @param routes original, removed, restored, penalised and restored snapshots;
     *               a single snapshot is allowed when no incident can be applied
     * @param affected roads affected by the incident, including reverse connections
     * @param output destination HTML file; its parent directory is created if needed
     * @throws IOException if an input asset cannot be read or the output cannot be written
     */
    public static void write(Path networkFile, List<Route> routes, List<Road> affected,
            Path output) throws IOException
    {
        if (networkFile == null || routes == null || affected == null || output == null)
            throw new NullPointerException("Map arguments must not be null");
        if (routes.isEmpty())
            throw new IllegalArgumentException("At least one route snapshot is required");
        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        payload.add("network", JsonParser.parseString(Files.readString(networkFile)));
        payload.add("context", JsonParser.parseString(Files.readString(Path.of("data/map-context.json"))));
        JsonArray scenarios = new JsonArray();
        for (Route route : routes)
        {
            JsonObject scenario = new JsonObject();
            scenario.addProperty("from", route.from);
            scenario.addProperty("to", route.to);
            scenario.addProperty("reachable", route.reachable());
            if (route.reachable())
            {
                scenario.addProperty("kilometres", route.kilometres());
                scenario.addProperty("cost", route.cost);
            }
            scenario.add("localities", gson.toJsonTree(route.localities));
            scenario.add("legs", gson.toJsonTree(route.legs));
            scenarios.add(scenario);
        }
        payload.add("scenarios", scenarios);
        JsonArray incidents = new JsonArray();
        for (Road road : affected)
        {
            incidents.add(road.id());
        }
        payload.add("affected", incidents);
        String template = Files.readString(Path.of("web/navigation-map.html"));
        String marker = "__NAVIGATION_DATA__";
        if (template.indexOf(marker) < 0 || template.indexOf(marker) != template.lastIndexOf(marker))
            throw new IOException("Map template must contain exactly one data marker");
        String html = template.replace(marker, gson.toJson(payload));
        if (output.getParent() != null)
            Files.createDirectories(output.getParent());
        Files.writeString(output, html, StandardCharsets.UTF_8);
    }
}
