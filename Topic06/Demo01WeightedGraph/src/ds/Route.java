package ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Immutable snapshot of a route, with physical distance separate from routing cost. */
public final class Route
{
    /** One selected directed road, capturing its penalty at query time. */
    public static final class Leg
    {
        public final String roadId;
        public final String from;
        public final String to;
        public final String name;
        public final double kilometres;
        public final double penalty;

        // Only the network constructs these snapshots from already validated roads.
        Leg(String roadId, String from, String to, String name, double kilometres, double penalty)
        {
            this.roadId = roadId;
            this.from = from;
            this.to = to;
            this.name = name;
            this.kilometres = kilometres;
            this.penalty = penalty;
        }
    }

    public final String from;
    public final String to;
    public final List<String> localities;
    public final List<Leg> legs;
    public final double cost;

    Route(String from, String to, List<String> localities, List<Leg> legs, double cost)
    {
        this.from = from;
        this.to = to;
        this.localities = Collections.unmodifiableList(new ArrayList<>(localities));
        this.legs = Collections.unmodifiableList(new ArrayList<>(legs));
        this.cost = cost;
    }

    /** Unreachable destinations have infinite cost and empty paths. */
    public boolean reachable()
    {
        return cost != Double.POSITIVE_INFINITY;
    }

    /** Sums physical distances only; an unreachable route has infinite distance. */
    public double kilometres()
    {
        if (!reachable())
            return Double.POSITIVE_INFINITY;
        double sum = 0;
        for (Leg leg : legs)
        {
            sum += leg.kilometres;
        }
        return sum;
    }
}
