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
        PeekingIterator<String> it = new PeekingIterator<>(Arrays.asList(null, "B"));
        equal(true, it.hasNext());
        equal(null, it.peek());
        equal(null, it.peek());
        equal(null, it.next());
        equal("B", it.next());
        equal(false, it.hasNext());
        rejects(NoSuchElementException.class, it::next);
        rejects(NoSuchElementException.class, it::peek);
        rejects(UnsupportedOperationException.class, it::remove);
        rejects(NullPointerException.class, () -> new PeekingIterator<String>(null));
        equal(false, new PeekingIterator<>(Collections.emptyList()).hasNext());
        System.out.println("All " + checks + " checks passed.");
    }
}
