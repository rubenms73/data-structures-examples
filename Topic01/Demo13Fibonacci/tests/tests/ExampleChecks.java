package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        fibonacci();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void fibonacci()
    {
        for (int n : new int[] {0, 1, 2, 3, 10, 92, 93})
        {
            List<Long> terms = collect(new Fibonacci(n));
            equal(n, terms.size());
            BigInteger a = BigInteger.ZERO, b = BigInteger.ONE;
            for (long term : terms)
            {
                equal(a.longValueExact(), term);
                BigInteger next = a.add(b); a = b; b = next;
            }
        }
        throwsType(IllegalArgumentException.class, () -> new Fibonacci(-1));
        throwsType(IllegalArgumentException.class, () -> new Fibonacci(94));
        Iterator<Long> a = new Fibonacci(1).iterator();
        equal(0L, a.next());
        throwsType(NoSuchElementException.class, a::next);
        Fibonacci f = new Fibonacci();
        equal(10, collect(f).size());
        Iterator<Long> first = f.iterator(), second = f.iterator();
        first.next(); first.next();
        equal(0L, second.next());
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
