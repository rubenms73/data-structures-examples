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

    // Checks the heap order on the array shown by toString.
    private static void isHeap(MinHeap<Integer> heap)
    {
        String text = heap.toString();
        List<Integer> a = new ArrayList<>();
        if (text.length() > 2)
        {
            for (String part : text.substring(1, text.length() - 1).split(", "))
            {
                a.add(Integer.parseInt(part));
            }
        }
        equal(heap.size(), a.size());
        for (int i = 1; i < a.size(); i++)
        {
            checks++;
            if (a.get((i - 1) / 2) > a.get(i))
                throw new AssertionError("Heap order broken: " + a);
        }
    }

    public static void main(String[] args)
    {
        // Random insertions and removals compared with java.util.PriorityQueue.
        Random random = new Random(5);
        for (int round = 0; round < 500; round++)
        {
            MinHeap<Integer> heap = new MinHeap<>(Comparator.naturalOrder());
            PriorityQueue<Integer> ref = new PriorityQueue<>();
            for (int step = 0; step < 60; step++)
            {
                if (ref.isEmpty() || random.nextInt(3) > 0)
                {
                    int value = random.nextInt(40);
                    equal(ref.add(value), heap.add(value));
                }
                else
                    equal(ref.remove(), heap.remove());
                equal(ref.size(), heap.size());
                equal(ref.isEmpty(), heap.isEmpty());
                if (!ref.isEmpty())
                    equal(ref.element(), heap.element());
                isHeap(heap);
            }
            heap.clear();
            equal(0, heap.size());
        }

        // Bottom-up construction gives a valid heap with the same elements.
        for (int n = 0; n <= 40; n++)
        {
            List<Integer> values = new ArrayList<>();
            for (int i = 0; i < n; i++)
            {
                values.add(random.nextInt(20));
            }
            MinHeap<Integer> built = new MinHeap<>(values, Comparator.naturalOrder());
            isHeap(built);
            List<Integer> sorted = new ArrayList<>(values);
            Collections.sort(sorted);
            List<Integer> removed = new ArrayList<>();
            while (!built.isEmpty())
            {
                removed.add(built.remove());
            }
            equal(sorted, removed);
        }

        MinHeap<Integer> max = new MinHeap<>(List.of(3, 9, 1), Comparator.reverseOrder());
        equal(9, max.remove());
        equal(3, max.remove());

        MinHeap<Integer> heap = new MinHeap<>(Comparator.naturalOrder());
        rejects(NoSuchElementException.class, heap::element);
        rejects(NoSuchElementException.class, heap::remove);
        rejects(NullPointerException.class, () -> heap.add(null));
        rejects(NullPointerException.class, () -> new MinHeap<Integer>(null));
        rejects(NullPointerException.class,
                () -> new MinHeap<Integer>(Arrays.asList(1, null), Comparator.naturalOrder()));
        System.out.println("All " + checks + " checks passed.");
    }
}
