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
        Comparator<String> length = new LengthComparator();
        String[] words = {"pear", "banana", "fig"};
        String[] expected = {"fig", "pear", "banana"};
        Arrays.sort(words, length);
        equal(Arrays.asList(expected), Arrays.asList(words));

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
