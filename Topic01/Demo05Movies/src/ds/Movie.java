package ds;

import java.util.Locale;
import java.util.Objects;

/**
 * A movie entry with a classroom rating (not a live review score).
 * For this example the natural order is year, then title, then rating.
 * Two entries are equal when all three values match.
 */
public final class Movie implements Comparable<Movie> {
    private final String title;
    private final int year;
    private final double rating;

    public Movie(String title, int year, double rating) {
        this.title = Objects.requireNonNull(title);
        if (!Double.isFinite(rating) || rating < 0 || rating > 10)
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        this.year = year;
        this.rating = rating;
    }

    public String title() { return title; }
    public int year() { return year; }
    public double rating() { return rating; }

    @Override
    public int compareTo(Movie other) {
        int result = Integer.compare(year, other.year);
        if (result != 0) return result;
        result = title.compareTo(other.title);
        if (result != 0) return result;
        return Double.compare(rating, other.rating);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Movie)) return false;
        Movie movie = (Movie) other;
        return year == movie.year && title.equals(movie.title)
                && Double.compare(rating, movie.rating) == 0;
    }

    @Override public int hashCode() { return Objects.hash(title, year, rating); }

    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s (%d, %.1f)", title, year, rating);
    }
}
