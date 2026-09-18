package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Named localities and directed road alternatives, including parallel roads.
 * Every query builds a fresh weighted graph from the current incident state.
 * The basic WeightedGraph and its two Dijkstra algorithms remain unchanged.
 */
public final class NavigationNetwork
{
    private final Set<String> localities = new LinkedHashSet<>();
    private final Map<String, Road> roads = new LinkedHashMap<>();

    /** Adds a locality; duplicate names return false. */
    public boolean addLocality(String name)
    {
        if (name == null)
            throw new NullPointerException("Locality must not be null");
        if (name.isBlank())
            throw new IllegalArgumentException("Locality must not be blank");
        return localities.add(name);
    }

    /** Adds one road; endpoints must exist and identifiers must be unique. */
    public void addRoad(Road road)
    {
        if (road == null)
            throw new NullPointerException("Road must not be null");
        if (!localities.contains(road.from()) || !localities.contains(road.to()))
            throw new IllegalArgumentException("Unknown road endpoint");
        if (roads.containsKey(road.id()))
            throw new IllegalArgumentException("Duplicate road identifier: " + road.id());
        roads.put(road.id(), road);
    }

    /** Read-only locality view. */
    public Set<String> localities()
    {
        return Collections.unmodifiableSet(localities);
    }

    /** Independent ordered list; the Road objects are shared so incidents can be set. */
    public List<Road> roads()
    {
        return new ArrayList<>(roads.values());
    }

    /** Looks up an incident target; unknown and null identifiers are rejected. */
    public Road road(String id)
    {
        if (id == null)
            throw new NullPointerException("Road identifier must not be null");
        Road road = roads.get(id);
        if (road == null)
            throw new NoSuchElementException("Unknown road: " + id);
        return road;
    }

    /** Removes one directed alternative permanently, returning the removed object. */
    public Road removeRoad(String id)
    {
        Road removed = road(id);
        roads.remove(id);
        return removed;
    }

    /**
     * Computes the minimum current cost. Both Dijkstra implementations are checked.
     * Parallel roads are retained in this network. For this single additive cost,
     * only the cheapest open road for each ordered pair is needed by Dijkstra.
     * Equal costs keep the first inserted alternative, giving reproducible output.
     */
    public Route route(String from, String to)
    {
        if (from == null || to == null)
            throw new NullPointerException("Endpoints must not be null");
        if (!localities.contains(from) || !localities.contains(to))
            throw new IllegalArgumentException("Unknown locality");
        Map<String, Map<String, Road>> selected = new LinkedHashMap<>();
        for (String locality : localities)
        {
            selected.put(locality, new LinkedHashMap<>());
        }
        for (Road road : roads.values())
        {
            if (road.isClosed())
                continue;
            Map<String, Road> outgoing = selected.get(road.from());
            Road previous = outgoing.get(road.to());
            if (previous == null || road.cost() < previous.cost())
                outgoing.put(road.to(), road);
        }
        WeightedGraph<String> graph = new WeightedGraph<>(Comparator.naturalOrder());
        for (String locality : localities)
        {
            graph.addVertex(locality);
        }
        for (Map<String, Road> outgoing : selected.values())
        {
            for (Road road : outgoing.values())
            {
                graph.addEdge(road.from(), road.to(), road.cost());
            }
        }
        ShortestPaths<String> linear = graph.dijkstra(from);
        ShortestPaths<String> heap = graph.dijkstraHeap(from);
        if (!linear.distances().equals(heap.distances()))
            throw new AssertionError("Dijkstra implementations disagree");
        List<String> vertices = heap.pathTo(to);
        List<Route.Leg> legs = new ArrayList<>();
        for (int i = 1; i < vertices.size(); i++)
        {
            Road road = selected.get(vertices.get(i - 1)).get(vertices.get(i));
            // Snapshot the chosen road and cost so later incidents cannot alter this result.
            legs.add(new Route.Leg(road.id(), road.from(), road.to(), road.name(),
                    road.kilometres(), road.penalty()));
        }
        return new Route(from, to, vertices, legs, heap.distanceTo(to));
    }
}
