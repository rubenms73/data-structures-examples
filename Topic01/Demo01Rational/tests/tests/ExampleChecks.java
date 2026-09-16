package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        rational();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void rational()
    {
        for (int n : new int[] {0, 3, -7, Integer.MIN_VALUE})
        {
            for (int d : new int[] {4, -2, Integer.MAX_VALUE})
            {
                for (Rational r : new Rational[] {new RationalImp1(n, d), new RationalImp2(n, d)})
                {
                    equal(n, r.numerator());
                    equal(d, r.denominator());
                    equal((double) n / d, r.value());
                }
            }
        }
        throwsType(IllegalArgumentException.class, () -> new RationalImp1(1, 0));
        throwsType(IllegalArgumentException.class, () -> new RationalImp2(1, 0));
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
