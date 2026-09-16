package ds;

import java.util.Comparator;

/** Title order; ties use the movie's natural order. */
public final class TitleComparator implements Comparator<Movie>
{
    @Override
    public int compare(Movie a, Movie b)
    {
        int result = a.title().compareTo(b.title());
        return result != 0 ? result : a.compareTo(b);
    }
}
