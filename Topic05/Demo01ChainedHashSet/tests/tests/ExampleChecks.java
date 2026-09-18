package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception e)
        {
            if (type.isInstance(e))
                return;
            throw new AssertionError(e);
        }
        throw new AssertionError("Expected " + type);
    }

    private static <E> List<E> collect(Iterable<E> source)
    {
        List<E> result = new ArrayList<>();
        for (E value : source)
        {
            result.add(value);
        }
        return result;
    }

    public static void main(String[] args)
    {
        rejects(IllegalArgumentException.class, () -> new ChainedHashSet<Integer>(0, 0.75));
        rejects(IllegalArgumentException.class, () -> new ChainedHashSet<Integer>(1, Double.NaN));
        ChainedHashSet<Integer> set = new ChainedHashSet<>(1, 0.75);
        HashSet<Integer> expected = new HashSet<>();
        Random random = new Random(17);
        set.add(Integer.MIN_VALUE);
        expected.add(Integer.MIN_VALUE);
        for (int i = 0; i < 1000; i++)
        {
            int value = random.nextInt(100) - 50;
            if (random.nextBoolean())
                equal(expected.add(value), set.add(value));
            else
                equal(expected.remove(value), set.remove(value));
            equal(expected, set);
            equal(expected.size(), set.size());
        }
        Iterator<Integer> it = set.iterator();
        rejects(IllegalStateException.class, it::remove);
        while (it.hasNext())
        {
            it.next();
            it.hasNext();
            it.remove();
            rejects(IllegalStateException.class, it::remove);
        }
        equal(0, set.size());
        rejects(NoSuchElementException.class, it::next);
        rejects(NullPointerException.class, () -> set.add(null));
        set.add(1);
        set.clear();
        equal(0, set.size());
        System.out.println("All " + checks + " checks passed.");
    }
}
