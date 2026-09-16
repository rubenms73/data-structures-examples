package tests;

import java.util.*;
import java.math.BigInteger;
import ds.*;

public final class ExampleChecks
{
    private static int checks;
    public static void main(String[] args)
    {
        arrays();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void arrays()
    {
        for (MyIntArray a : new MyIntArray[] {new FixedMyIntArray(3), new DynamicMyIntArray(0)})
        {
            equal(true, a.isEmpty());
            equal(false, a.contains(0));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(0));
            throwsType(IndexOutOfBoundsException.class, () -> a.set(0, 1));
            a.add(4); a.add(8); a.add(12);
            equal(3, a.size());
            equal(24, ArrayAlgorithms.sum(a));
            equal(true, a.contains(8));
            a.set(1, 10);
            equal(3, a.size());
            equal(10, a.get(1));
            equal(false, a.contains(8));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(-1));
            throwsType(IndexOutOfBoundsException.class, () -> a.get(a.size()));
            throwsType(IndexOutOfBoundsException.class, () -> a.set(a.size(), 9));
        }
        FixedMyIntArray fixed = new FixedMyIntArray(1);
        fixed.add(7);
        throwsType(IllegalStateException.class, () -> fixed.add(9));
        equal(1, fixed.size());
        equal(7, fixed.get(0));
        FixedMyIntArray zero = new FixedMyIntArray(0);
        throwsType(IllegalStateException.class, () -> zero.add(1));
        equal(0, zero.size());
        throwsType(IllegalArgumentException.class, () -> new FixedMyIntArray(-1));
        throwsType(IllegalArgumentException.class, () -> new DynamicMyIntArray(-1));

        // Compare mixed updates with an independent library sequence across many resizes.
        Random random = new Random(2026);
        DynamicMyIntArray dynamic = new DynamicMyIntArray(0);
        List<Integer> expected = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            int value = random.nextInt(200) - 100;
            dynamic.add(value);
            expected.add(value);
            if (i % 3 == 0)
            {
                int index = random.nextInt(expected.size());
                dynamic.set(index, -value);
                expected.set(index, -value);
            }
            equal(expected.size(), dynamic.size());
            equal(expected.contains(value), dynamic.contains(value));
        }
        for (int i = 0; i < expected.size(); i++)
        {
            equal(expected.get(i), dynamic.get(i));
        }
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
