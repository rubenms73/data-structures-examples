package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        ranges();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void ranges()
    {
        IntRange range = new IntRange(2, 5);
        equal(Arrays.asList(2, 3, 4), collect(range));
        Iterator<Integer> a = range.iterator();
        Iterator<Integer> b = range.iterator();
        equal(true, a.hasNext());
        equal(true, a.hasNext());
        equal(2, a.next()); equal(3, a.next()); equal(2, b.next()); equal(4, a.next());
        equal(false, a.hasNext());
        throwsType(NoSuchElementException.class, a::next);
        throwsType(NoSuchElementException.class, a::next);
        throwsType(UnsupportedOperationException.class, b::remove);
        equal(Arrays.asList(), collect(new IntRange(4, 4)));
        throwsType(NoSuchElementException.class, new IntRange(4, 4).iterator()::next);
        throwsType(IllegalArgumentException.class, () -> new IntRange(5, 4));
        equal(Arrays.asList(Integer.MAX_VALUE - 1), collect(new IntRange(Integer.MAX_VALUE - 1, Integer.MAX_VALUE)));
        equal(Arrays.asList(-2, -1, 0), collect(new IntRange(-2, 1)));
    }

    private static <T> List<T> collect(Iterable<T> values)
    {
        List<T> result = new ArrayList<>();
        for (T value : values)
        {
            result.add(value);
        }
        return result;
    }

    private static void equal(Object expected, Object actual)
    {
        check(Objects.equals(expected, actual), "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message)
    {
        checks++;
        if (!condition)
            throw new AssertionError(message);
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
            if (expected.isInstance(actual))
                return;
            throw new AssertionError("Expected " + expected.getSimpleName() + ", got " + actual, actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
