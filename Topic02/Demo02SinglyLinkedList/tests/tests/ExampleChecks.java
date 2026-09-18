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
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>();
        rejects(IndexOutOfBoundsException.class, () -> list.get(0));
        rejects(IndexOutOfBoundsException.class, () -> list.insert(-1, 0));
        list.add(1);
        list.add(3);
        list.insert(1, 2);
        equal(Arrays.asList(1, 2, 3), collect(list));
        Iterator<Integer> it = list.iterator();
        rejects(IllegalStateException.class, it::remove);
        while (it.hasNext())
        {
            it.next();
            it.remove();
            rejects(IllegalStateException.class, it::remove);
        }
        equal(0, list.size());
        rejects(NoSuchElementException.class, it::next);
        list.add(null);
        list.add(4);
        it = list.iterator();
        equal(null, it.next());
        equal(4, it.next());
        it.remove();
        list.add(5);
        equal(Arrays.asList(null, 5), collect(list));
        equal(2, list.size());
        System.out.println("All " + checks + " checks passed.");
    }
}
