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
        Comparator<String> length = (a, b) -> Integer.compare(a.length(), b.length());
        String[] words = {"pear", "banana", "fig"};
        equal("banana", Maximum.max(words, length));
        equal("pear", Maximum.max(words, Comparator.naturalOrder()));
        equal("pear", words[0]);
        equal("pear", Maximum.max(new String[] {"pear", "plum"}, length));
        Comparator<Number> numeric = (a, b) -> Double.compare(a.doubleValue(), b.doubleValue());
        Integer largest = Maximum.max(new Integer[] {2, 9, 4}, numeric);
        equal(9, largest);
        throwsType(IllegalArgumentException.class, () -> Maximum.max(new String[0], length));
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
