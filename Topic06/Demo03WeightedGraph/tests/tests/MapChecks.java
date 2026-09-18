package tests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ds.NavigationNetwork;
import ds.Route;
import io.NavigationMapWriter;
import io.NavigationNetworkReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/** Verifies snapshot export, unreachable routes and safe embedded JSON. */
final class MapChecks
{
    private MapChecks()
    {
    }

    static int run() throws IOException
    {
        Path output = Files.createTempFile("navigation-map-check", ".html");
        Path input = Files.createTempFile("navigation-network-check", ".json");
        try
        {
            String name = "</script><script>alert(1)</script>";
            JsonObject root = new JsonObject();
            root.addProperty("directed", true);
            root.add("vertices", new com.google.gson.Gson().toJsonTree(List.of(name, "B")));
            root.add("roads", new com.google.gson.JsonArray());
            Files.writeString(input, root.toString());
            NavigationNetwork network = NavigationNetworkReader.read(new StringReader(root.toString()));
            Route unreachable = network.route(name, "B");
            Route identity = network.route(name, name);
            NavigationMapWriter.write(input, List.of(unreachable, identity), List.of(), output);
            String html = Files.readString(output);
            if (html.contains(name) || html.contains("__NAVIGATION_DATA__"))
                throw new AssertionError("Unsafe or missing embedded data");
            String start = "<script id=\"navigation-data\" type=\"application/json\">";
            int offset = html.indexOf(start) + start.length();
            String json = html.substring(offset, html.indexOf("</script>", offset));
            JsonObject payload = JsonParser.parseString(json).getAsJsonObject();
            JsonObject first = payload.getAsJsonArray("scenarios").get(0).getAsJsonObject();
            JsonObject second = payload.getAsJsonArray("scenarios").get(1).getAsJsonObject();
            if (first.get("reachable").getAsBoolean() || first.has("cost"))
                throw new AssertionError("Unreachable route must not have a finite cost");
            if (!first.get("from").getAsString().equals(name))
                throw new AssertionError("Escaping must preserve the actual locality name");
            if (second.get("cost").getAsDouble() != 0 || !second.getAsJsonArray("legs").isEmpty())
                throw new AssertionError("Identity route must have zero cost and no legs");
            return 4;
        }
        finally
        {
            Files.deleteIfExists(output);
            Files.deleteIfExists(input);
        }
    }
}
