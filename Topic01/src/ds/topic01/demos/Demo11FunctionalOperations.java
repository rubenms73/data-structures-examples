package ds.topic01.demos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import ds.topic01.movies.Movie;

public final class Demo11FunctionalOperations {
    public static void main(String[] args) {
        List<Movie> movies = Arrays.asList(
                new Movie("The Last Train", 2022, 7.4),
                new Movie("Blue Planet", 2018, 8.6),
                new Movie("A Quiet Harbour", 2020, 8.6));

        Predicate<Movie> highlyRated = movie -> movie.rating() >= 8.0;
        Function<Movie, String> label = movie -> movie.title() + " (" + movie.year() + ")";
        Consumer<String> print = text -> System.out.println("  " + text);
        Supplier<Movie> newExample = () -> new Movie("Winter Lights", 2018, 7.9);

        System.out.println("Movies rated at least 8.0:");
        for (Movie movie : movies) {
            if (highlyRated.test(movie)) print.accept(label.apply(movie));
        }
        System.out.println("Supplier.get(): " + newExample.get());

        // Equivalent method reference, introduced only after the lambda:
        Consumer<String> printLine = System.out::println;
        printLine.accept("Each interface has exactly one abstract operation to implement.");
    }
}
