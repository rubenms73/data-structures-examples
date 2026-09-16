package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        example();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void example()
    {
        Movie early = new Movie("First", Integer.MIN_VALUE, 5);
        Movie late = new Movie("Last", Integer.MAX_VALUE, 5);
        check(early.compareTo(late) < 0, "year comparison must not overflow");
        List<Movie> movies = Arrays.asList(early, late, new Movie("A", 2018, 8),
                new Movie("B", 2018, 8), new Movie("A", 2018, 9), new Movie("A", 2018, 8));
        for (Movie a : movies) for (Movie b : movies)
        {
            equal(a.equals(b), a.compareTo(b) == 0);
            if (a.equals(b)) equal(a.hashCode(), b.hashCode());
        }
        for (Comparator<Movie> order : Arrays.asList(Comparator.<Movie>naturalOrder(),
                new RatingComparator(), new TitleComparator()))
                {
            for (Movie a : movies) for (Movie b : movies)
            {
                equal(Integer.signum(order.compare(a, b)), -Integer.signum(order.compare(b, a)));
                for (Movie c : movies)
                {
                    if (order.compare(a, b) <= 0 && order.compare(b, c) <= 0)
                        check(order.compare(a, c) <= 0, "transitive comparison");
                }
            }
        }
        throwsType(IllegalArgumentException.class, () -> new Movie("Bad", 2020, Double.NaN));
    }

    private static <T> List<T> collect(Iterable<T> values)
    {
        List<T> result = new ArrayList<>();
        for (T value : values) result.add(value);
        return result;
    }

    private static void equal(Object expected, Object actual)
    {
        check(Objects.equals(expected, actual), "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message)
    {
        checks++;
        if (!condition) throw new AssertionError(message);
    }

    private static void throwsType(Class<? extends Throwable> expected, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Throwable actual)
        {
            if (expected.isInstance(actual)) return;
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
