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
        Collection<Integer> list = new SinglyLinkedList<>();
        rejects(NoSuchElementException.class, () -> list.iterator().next());
        rejects(IllegalStateException.class, () -> list.iterator().remove());
        list.add(1);
        list.add(2);
        list.add(3);
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
        inheritedCollectionOperations();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void inheritedCollectionOperations()
    {
        Collection<Integer> values = new SinglyLinkedList<>();
        equal(true, values.isEmpty());
        equal(false, values.addAll(Collections.emptyList()));
        equal(true, values.add(1));
        equal(true, values.add(1));
        equal(true, values.addAll(Arrays.asList(null, 2, 3)));
        equal(5, values.size());
        equal(true, values.contains(null));
        equal(true, values.containsAll(Arrays.asList(1, 2)));
        equal(false, values.contains(9));
        equal(Arrays.asList(1, 1, null, 2, 3), Arrays.asList(values.toArray()));
        equal(Arrays.asList(1, 1, null, 2, 3),
                Arrays.asList(values.toArray(new Integer[0])));

        // Remove the head, a null in the middle, and the tail by value.
        equal(true, values.remove(1));
        equal(Arrays.asList(1, null, 2, 3), collect(values));
        equal(true, values.remove(null));
        equal(true, values.remove(3));
        equal(false, values.remove(9));
        equal(true, values.add(4));
        equal(Arrays.asList(1, 2, 4), collect(values));
        equal(3, values.size());

        // Bulk removal must preserve the tail and count, including an empty result.
        equal(true, values.addAll(Arrays.asList(2, null, 4)));
        equal(true, values.removeAll(Arrays.asList(2, 4)));
        equal(Arrays.asList(1, null), collect(values));
        equal(2, values.size());
        equal(false, values.removeAll(Collections.singleton(9)));
        equal(true, values.add(5));
        equal(Arrays.asList(1, null, 5), collect(values));
        equal(true, values.retainAll(Collections.singleton(null)));
        equal(Collections.singletonList(null), collect(values));
        equal(1, values.size());
        equal(false, values.retainAll(Collections.singleton(null)));
        equal(true, values.remove(null));
        equal(true, values.isEmpty());
        equal(true, values.add(6));
        equal(Collections.singletonList(6), collect(values));

        values.addAll(Arrays.asList(null, 7));
        values.clear();
        equal(0, values.size());
        equal(false, values.iterator().hasNext());
        values.clear();
        equal(true, values.add(8));
        equal(Collections.singletonList(8), collect(values));
        equal(true, values.retainAll(Collections.emptyList()));
        equal(true, values.isEmpty());
        equal(true, values.add(9));
        equal(Collections.singletonList(9), collect(values));
    }
}
