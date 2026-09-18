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
        SortedPriorityQueue<Integer> q = new SortedPriorityQueue<>();
        PriorityQueue<Integer> ref = new PriorityQueue<>();
        Random r = new Random(17);
        for (int i = 0; i < 500; i++)
        {
            int value = r.nextInt(30);
            q.offer(value);
            ref.offer(value);
        }
        while (!ref.isEmpty())
        {
            equal(ref.poll(), q.poll());
        }
        equal(null, q.poll());
        rejects(NullPointerException.class, () -> q.offer(null));
        rejects(NullPointerException.class, () -> new SortedPriorityQueue<Integer>(null, null));
        Comparator<Number> descending = (a, b) -> Double.compare(b.doubleValue(), a.doubleValue());
        SortedPriorityQueue<Integer> reversed = new SortedPriorityQueue<>(Arrays.asList(1, 3, 2), descending);
        equal(Arrays.asList(3, 2, 1), collect(reversed));
        SortedPriorityQueue<String> stable = new SortedPriorityQueue<>((a, b) -> Integer.compare(a.length(), b.length()));
        stable.addAll(Arrays.asList("cat", "dog", "ox"));
        equal(Arrays.asList("ox", "cat", "dog"), collect(stable));
        SortedPriorityQueue<Object> bad = new SortedPriorityQueue<>();
        rejects(ClassCastException.class, () -> bad.add(new Object()));
        equal(0, bad.size());
        System.out.println("All " + checks + " checks passed.");
    }
}
