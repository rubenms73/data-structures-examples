package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Strictness;
import ds.NavigationNetwork;
import ds.Road;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Loads the directed navigation JSON schema described in README.md.
 * Gson handles JSON syntax; explicit conditions validate the graph data.
 * This supporting class is separate from the graph and shortest-path algorithms.
 */
public final class NavigationNetworkReader
{
    private NavigationNetworkReader()
    {
    }

    /** Reads UTF-8 JSON, closing the file even if parsing or validation fails. */
    public static NavigationNetwork read(Path path) throws IOException
    {
        if (path == null)
            throw new NullPointerException("Path must not be null");
        try (Reader input = Files.newBufferedReader(path, StandardCharsets.UTF_8))
        {
            return read(input);
        }
    }

    /**
     * Reads one complete JSON document; the caller owns and closes the reader.
     * Invalid JSON raises a Gson parsing exception; invalid graph data raises
     * IllegalArgumentException. No partially built graph is returned.
     */
    public static NavigationNetwork read(Reader input)
    {
        if (input == null)
            throw new NullPointerException("Reader must not be null");
        Gson gson = new GsonBuilder().setStrictness(Strictness.STRICT).create();
        JsonElement root = gson.fromJson(input, JsonElement.class);
        if (root == null || !root.isJsonObject())
            throw new IllegalArgumentException("Expected a JSON object");
        JsonObject document = root.getAsJsonObject();
        JsonElement directedValue = document.get("directed");
        if (directedValue == null || !directedValue.isJsonPrimitive()
                || !directedValue.getAsJsonPrimitive().isBoolean())
            throw new IllegalArgumentException("directed must be a boolean");
        if (!directedValue.getAsBoolean())
            throw new IllegalArgumentException("Navigation roads must be explicitly directed");
        NavigationNetwork graph = new NavigationNetwork();
        for (JsonElement value : array(document, "vertices"))
        {
            String name = name(value);
            if (!graph.addLocality(name))
                throw new IllegalArgumentException("Duplicate locality: " + name);
        }
        for (JsonElement value : array(document, "roads"))
        {
            if (!value.isJsonObject())
                throw new IllegalArgumentException("Each road must be an object");
            JsonObject road = value.getAsJsonObject();
            String from = name(road.get("from"));
            String to = name(road.get("to"));
            if (!graph.localities().contains(from) || !graph.localities().contains(to))
                throw new IllegalArgumentException("Road endpoint not listed in vertices");
            JsonElement km = road.get("km");
            if (km == null || !km.isJsonPrimitive())
                throw new IllegalArgumentException("km must be a number");
            JsonPrimitive number = km.getAsJsonPrimitive();
            if (!number.isNumber())
                throw new IllegalArgumentException("km must be a number, not text");
            double weight = number.getAsDouble();
            if (weight < 0 || Double.isNaN(weight) || Double.isInfinite(weight))
                throw new IllegalArgumentException("km must be finite and nonnegative");
            String id = name(road.get("id"));
            String roadName = name(road.get("name"));
            List<String> corridors = new ArrayList<>();
            if (road.has("corridors"))
            {
                for (JsonElement corridor : array(road, "corridors"))
                {
                    corridors.add(name(corridor));
                }
            }
            graph.addRoad(new Road(id, from, to, roadName, weight, corridors));
        }
        return graph;
    }

    private static JsonArray array(JsonObject document, String key)
    {
        JsonElement value = document.get(key);
        if (value == null || !value.isJsonArray())
            throw new IllegalArgumentException(key + " must be an array");
        return value.getAsJsonArray();
    }

    private static String name(JsonElement value)
    {
        if (value == null || !value.isJsonPrimitive()
                || !value.getAsJsonPrimitive().isString())
            throw new IllegalArgumentException("Locality names must be strings");
        String name = value.getAsString();
        if (name.isBlank() || !name.equals(name.strip()))
            throw new IllegalArgumentException("Locality names must be nonempty, without outer spaces");
        return name;
    }
}
