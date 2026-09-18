package ds;

import java.util.List;

/** One directed road alternative. Kilometres and its identifier never change. */
public final class Road
{
    private final String id;
    private final String from;
    private final String to;
    private final String name;
    private final double kilometres;
    private final List<String> corridors;
    private boolean closed;
    private double penalty;

    /** Creates an open road with zero penalty; rejects missing names and invalid distance. */
    public Road(String id, String from, String to, String name, double kilometres)
    {
        this(id, from, to, name, kilometres, List.of());
    }

    /** Adds immutable corridor tags for grouping overlapping roads during an incident. */
    public Road(String id, String from, String to, String name, double kilometres,
            List<String> corridors)
    {
        if (corridors == null)
            throw new NullPointerException("Corridors must not be null");
        for (String corridor : corridors)
        {
            if (corridor == null || corridor.isBlank())
                throw new IllegalArgumentException("Corridor tags must not be null or blank");
        }
        this.corridors = List.copyOf(corridors);
        if (id == null || from == null || to == null || name == null)
            throw new NullPointerException("Road fields must not be null");
        if (id.isBlank() || from.isBlank() || to.isBlank() || name.isBlank())
            throw new IllegalArgumentException("Road fields must not be blank");
        if (from.equals(to))
            throw new IllegalArgumentException("A road must connect different localities");
        if (kilometres < 0 || Double.isNaN(kilometres) || Double.isInfinite(kilometres))
            throw new IllegalArgumentException("Distance must be finite and nonnegative");
        this.id = id;
        this.from = from;
        this.to = to;
        this.name = name;
        this.kilometres = kilometres;
    }

    /** Unique key, also used to select an incident from the command line. */
    public String id()
    {
        return id;
    }

    /** Origin locality. */
    public String from()
    {
        return from;
    }

    /** Destination locality. */
    public String to()
    {
        return to;
    }

    /** Road references reported by the routing source, including access roads. */
    public String name()
    {
        return name;
    }

    /** Tests a complete road reference, such as AP-66, in this corridor. */
    public boolean usesReference(String reference)
    {
        if (reference == null)
            throw new NullPointerException("Road reference must not be null");
        for (String part : name.split(","))
        {
            if (part.trim().equals(reference))
                return true;
        }
        return false;
    }

    /** Tests a tagged physical corridor, rather than every section sharing a road number. */
    public boolean usesCorridor(String corridor)
    {
        if (corridor == null)
            throw new NullPointerException("Corridor must not be null");
        return corridors.contains(corridor);
    }

    /** Actual map-derived distance; traffic changes never modify this value. */
    public double kilometres()
    {
        return kilometres;
    }

    /** True when this directed alternative is temporarily unavailable. */
    public boolean isClosed()
    {
        return closed;
    }

    /** Sets a closure. The opposite direction is a separate road. */
    public void setClosed(boolean closed)
    {
        this.closed = closed;
    }

    /** Current teaching penalty, expressed in equivalent-distance cost units. */
    public double penalty()
    {
        return penalty;
    }

    /** Changes only the cost penalty; invalid changes leave this road unchanged. */
    public void setPenalty(double penalty)
    {
        if (penalty < 0 || Double.isNaN(penalty) || Double.isInfinite(penalty)
                || kilometres + penalty == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Penalty and resulting cost must be finite and nonnegative");
        this.penalty = penalty;
    }

    /** Weight used by Dijkstra: map distance plus the current penalty. */
    public double cost()
    {
        return kilometres + penalty;
    }
}
