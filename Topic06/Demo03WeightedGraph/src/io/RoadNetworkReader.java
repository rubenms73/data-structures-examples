package io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.Strictness;
import ds.WeightedGraph;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

/**
 * Loads the small road-network JSON schema described in README.md.
 * Gson handles JSON syntax; explicit conditions validate the graph data.
 * This supporting class is separate from the graph and shortest-path algorithms.
 */
public final class RoadNetworkReader
{
    private RoadNetworkReader()
    {
    }

    /** Reads UTF-8 JSON, closing the file even if parsing or validation fails. */
    public static WeightedGraph<String> read(Path path) throws IOException
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
    public static WeightedGraph<String> read(Reader input)
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
        boolean directed = directedValue.getAsBoolean();
        WeightedGraph<String> graph = new WeightedGraph<>(Comparator.naturalOrder());
        for (JsonElement value : array(document, "vertices"))
        {
            String name = name(value);
            if (!graph.addVertex(name))
                throw new IllegalArgumentException("Duplicate locality: " + name);
        }
        for (JsonElement value : array(document, "roads"))
        {
            if (!value.isJsonObject())
                throw new IllegalArgumentException("Each road must be an object");
            JsonObject road = value.getAsJsonObject();
            String from = name(road.get("from"));
            String to = name(road.get("to"));
            if (!graph.hasVertex(from) || !graph.hasVertex(to))
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
            if (!graph.addEdge(from, to, weight))
                throw new IllegalArgumentException("Duplicate road or self-loop: " + from + " -> " + to);
            // One JSON road represents two arcs when the network is undirected.
            if (!directed && !graph.addEdge(to, from, weight))
                throw new IllegalArgumentException("Repeated reverse road: " + to + " -> " + from);
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
