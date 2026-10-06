package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static final class ConstructorProbe extends ds.SortedPriorityQueue<Integer>
    {
        private boolean ready = true;

        ConstructorProbe(java.util.Collection<? extends Integer> source)
        {
            super(source, null);
        }

        @Override
        public boolean offer(Integer value)
        {
            if (!ready)
                throw new AssertionError("Insertion override called during construction");
            return super.offer(value);
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
        equal(Arrays.asList(1, 2, 3), new java.util.ArrayList<>(new ConstructorProbe(Arrays.asList(3, 1, 2))));
        Queue<Integer> q = new SortedPriorityQueue<>();
        Queue<Integer> ref = new PriorityQueue<>();
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
        Queue<Integer> reversed = new SortedPriorityQueue<>(Arrays.asList(1, 3, 2), descending);
        equal(Arrays.asList(3, 2, 1), collect(reversed));
        Queue<String> stable = new SortedPriorityQueue<>((a, b) -> Integer.compare(a.length(), b.length()));
        stable.addAll(Arrays.asList("cat", "dog", "ox"));
        equal(Arrays.asList("ox", "cat", "dog"), collect(stable));
        Queue<Object> bad = new SortedPriorityQueue<>();
        rejects(ClassCastException.class, () -> bad.add(new Object()));
        equal(0, bad.size());
        removalAndReuse();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void removalAndReuse()
    {
        Queue<Integer> queue = new SortedPriorityQueue<>();
        equal(null, queue.peek());
        rejects(NoSuchElementException.class, queue::element);
        queue.addAll(Arrays.asList(4, 2, 3, 1));
        Iterator<Integer> it = queue.iterator();
        rejects(IllegalStateException.class, it::remove);
        equal(1, it.next());
        it.remove();
        equal(2, queue.peek());
        equal(2, it.next());
        equal(3, it.next());
        it.remove();
        equal(4, it.next());
        it.remove();
        equal(Collections.singletonList(2), collect(queue));
        equal(1, queue.size());
        rejects(IllegalStateException.class, it::remove);
        rejects(NoSuchElementException.class, it::next);
        queue.addAll(Arrays.asList(5, 1, 3, 3));
        equal(Arrays.asList(1, 2, 3, 3, 5), collect(queue));
        equal(true, queue.remove(3));
        equal(Arrays.asList(1, 2, 3, 5), collect(queue));
        equal(true, queue.removeAll(Arrays.asList(1, 5)));
        equal(Arrays.asList(2, 3), collect(queue));
        equal(true, queue.retainAll(Collections.emptyList()));
        equal(true, queue.isEmpty());
        queue.offer(6);
        equal(6, queue.poll());
        queue.addAll(Arrays.asList(8, 7));
        queue.clear();
        equal(0, queue.size());
        queue.offer(9);
        equal(9, queue.remove());
        equal(null, queue.peek());
    }
}
