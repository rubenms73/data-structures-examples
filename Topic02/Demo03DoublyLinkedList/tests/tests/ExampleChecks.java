package tests;

import ds.DoublyLinkedList;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static Object step(ListIterator<Integer> it, int op, Integer value)
    {
        try
        {
            switch (op)
            {
                case 0: return it.next();
                case 1: return it.previous();
                case 2: it.add(value); return null;
                case 3: it.remove(); return null;
                case 4: it.set(value); return null;
                default: throw new AssertionError();
            }
        }
        catch (NoSuchElementException | IllegalStateException e)
        {
            return e.getClass();
        }
    }

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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>(1, 2, 3);
        rejects(IndexOutOfBoundsException.class, () -> list.listIterator(-1));
        rejects(IndexOutOfBoundsException.class, () -> list.listIterator(4));
        rejects(NullPointerException.class, () -> new DoublyLinkedList<Integer>((Integer[]) null));
        rejects(NullPointerException.class, () -> new DoublyLinkedList<Integer>((Collection<Integer>) null));
        List<Number> copy = new DoublyLinkedList<>(list);
        copy.set(0, 2.5);
        equal(1, list.get(0));
        equal(2.5, copy.get(0));
        list.clear();
        equal(0, list.size());
        list.add(null);
        equal(Arrays.asList((Integer) null), list);
        // Compare mixed cursor movements and edits with java.util.LinkedList.
        Random random = new Random(17);
        for (int trial = 0; trial < 100; trial++)
        {
            List<Integer> expected = new LinkedList<>(Arrays.asList(1, null, 3));
            List<Integer> actual = new DoublyLinkedList<>(expected);
            int start = random.nextInt(4);
            ListIterator<Integer> a = expected.listIterator(start);
            ListIterator<Integer> b = actual.listIterator(start);
            for (int i = 0; i < 100; i++)
            {
                int op = random.nextInt(5);
                Integer value = i % 7 == 0 ? null : i;
                equal(step(a, op, value), step(b, op, value));
                equal(expected, actual);
                equal(expected.size(), actual.size());
                equal(a.nextIndex(), b.nextIndex());
                equal(a.previousIndex(), b.previousIndex());
                equal(a.hasNext(), b.hasNext());
                equal(a.hasPrevious(), b.hasPrevious());
            }
        }
        System.out.println("All " + checks + " checks passed.");
    }
}
