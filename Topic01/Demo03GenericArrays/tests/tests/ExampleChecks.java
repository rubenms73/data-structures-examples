package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        generics();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void generics()
    {
        MyArray<String> names = new FixedMyArray<>(4);
        throwsType(IllegalArgumentException.class, () -> GenericAlgorithms.first(names));
        equal(true, names.add("Ana"));
        equal("Ana", GenericAlgorithms.first(names));
        names.set(0, "Ruben");
        equal("Ruben", names.get(0));
        equal(1, names.size());
        names.add(null);
        equal(null, names.get(1));
        throwsType(IndexOutOfBoundsException.class, () -> names.get(2));
        throwsType(IndexOutOfBoundsException.class, () -> names.set(-1, "x"));
        MyArray<Integer> scores = new FixedMyArray<>(4);
        scores.add(8);
        Integer first = GenericAlgorithms.first(scores);
        equal(8, first);

        String[] storage = new String[1];
        MyArray<String> fixed = new FixedMyArray<>(storage);
        fixed.add("kept");
        storage[0] = "changed outside";
        equal("kept", fixed.get(0));
        throwsType(IllegalStateException.class, () -> fixed.add("overflow"));
        equal(1, fixed.size());
        equal("kept", fixed.get(0));
        MyArray<String> bounded = new FixedMyArray<>(1);
        equal(0, bounded.size());
        bounded.add("only");
        throwsType(IllegalStateException.class, () -> bounded.add("overflow"));
        equal("only", bounded.get(0));
        throwsType(IllegalArgumentException.class, () -> new FixedMyArray<String>(-1));
        MyArray<String> empty = new FixedMyArray<>(0);
        equal(0, empty.size());
        throwsType(IllegalStateException.class, () -> empty.add("overflow"));
        throwsType(IndexOutOfBoundsException.class, () -> empty.get(0));
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
