package ds.topic01.demos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import ds.topic01.movies.Movie;
import ds.topic01.movies.RatingComparator;
import ds.topic01.movies.TitleComparator;

public final class Demo05Movies {
    public static void main(String[] args) {
        // Fictional titles and ratings for this classroom example.
        Movie[] movies = {
            new Movie("The Last Train", 2022, 7.4),
            new Movie("Blue Planet", 2018, 8.6),
            new Movie("A Quiet Harbour", 2020, 8.6),
            new Movie("Winter Lights", 2018, 7.9)
        };
        Arrays.sort(movies);
        print("Natural order: year, then title, then rating", movies);

        Arrays.sort(movies, new RatingComparator());
        print("Rating: highest first", movies);

        Arrays.sort(movies, new TitleComparator());
        print("Title order", movies);

        List<Movie> list = new ArrayList<>(Arrays.asList(movies));
        Collections.sort(list); // Uses the same compareTo method.
        System.out.println("Collections.sort: " + list.get(0).title() + " comes first");
    }

    private static void print(String heading, Movie[] movies) {
        System.out.println(heading);
        for (Movie movie : movies) System.out.println("  " + movie);
    }
}
