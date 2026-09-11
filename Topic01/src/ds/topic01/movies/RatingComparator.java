package ds.topic01.movies;

import java.util.Comparator;

/** Highest rating first; ties use the movie's natural order. */
public final class RatingComparator implements Comparator<Movie> {
    @Override
    public int compare(Movie a, Movie b) {
        int result = Double.compare(b.rating(), a.rating());
        return result != 0 ? result : a.compareTo(b);
    }
}
