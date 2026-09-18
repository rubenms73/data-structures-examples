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
        for (Queue<Integer> q : Arrays.<Queue<Integer>>asList(new FifoQueue<>(), new LifoQueue<>()))
        {
            equal(null, q.peek());
            equal(null, q.poll());
            rejects(NoSuchElementException.class, q::remove);
            rejects(NullPointerException.class, () -> q.offer(null));
            q.addAll(Arrays.asList(1, 2, 3));
            Iterator<Integer> it = q.iterator();
            rejects(IllegalStateException.class, it::remove);
            it.next();
            it.remove();
            equal(2, q.size());
            q.clear();
            equal(0, q.size());
        }
        FifoQueue<Integer> fifo = new FifoQueue<>(Arrays.asList(1, 2, 3));
        LifoQueue<Integer> lifo = new LifoQueue<>(Arrays.asList(1, 2, 3));
        equal(Arrays.asList(3, 2, 1), collect(lifo));
        for (int i = 1; i <= 3; i++)
        {
            equal(i, fifo.poll());
            equal(4 - i, lifo.poll());
        }
        rejects(NullPointerException.class, () -> new FifoQueue<Integer>(null));
        rejects(NullPointerException.class, () -> new LifoQueue<Integer>(null));
        System.out.println("All " + checks + " checks passed.");
    }
}
