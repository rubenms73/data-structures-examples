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

    public static void main(String[] args)
    {
        // Random operations compared with a HashSet. Negative keys check the
        // index correction, and the small capacity forces long probe runs.
        Random random = new Random(6);
        for (int round = 0; round < 1000; round++)
        {
            int capacity = 1 + random.nextInt(9);
            ProbingSet<Integer> set = new ProbingSet<>(capacity);
            Set<Integer> ref = new HashSet<>();
            for (int step = 0; step < 60; step++)
            {
                int key = random.nextInt(31) - 15;
                if (random.nextBoolean())
                {
                    if (!ref.contains(key) && ref.size() == capacity)
                        rejects(IllegalStateException.class, () -> set.add(key));
                    else
                        equal(ref.add(key), set.add(key));
                }
                else
                    equal(ref.remove(key), set.remove(key));
                equal(ref.size(), set.size());
                for (int k = -15; k <= 15; k++)
                {
                    equal(ref.contains(k), set.contains(k));
                }
            }
        }

        ProbingSet<Integer> set = new ProbingSet<>(3);
        set.add(Integer.MIN_VALUE);
        equal(true, set.contains(Integer.MIN_VALUE));
        rejects(NullPointerException.class, () -> set.add(null));
        rejects(NullPointerException.class, () -> set.contains(null));
        rejects(IllegalArgumentException.class, () -> new ProbingSet<Integer>(0));
        System.out.println("All " + checks + " checks passed.");
    }
}
