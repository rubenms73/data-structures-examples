package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static final class FifoQueueProbe extends ds.FifoQueue<Integer>
    {
        private boolean ready = true;

        FifoQueueProbe(java.util.Collection<? extends Integer> source)
        {
            super(source);
        }

        @Override
        public boolean offer(Integer value)
        {
            if (!ready)
                throw new AssertionError("offer override called during construction");
            return super.offer(value);
        }
    }

    private static final class LifoQueueProbe extends ds.LifoQueue<Integer>
    {
        private boolean ready = true;

        LifoQueueProbe(java.util.Collection<? extends Integer> source)
        {
            super(source);
        }

        @Override
        public boolean offer(Integer value)
        {
            if (!ready)
                throw new AssertionError("offer override called during construction");
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
        equal(Arrays.asList(1, 2, 3), new java.util.ArrayList<>(new FifoQueueProbe(Arrays.asList(1, 2, 3))));
        equal(Arrays.asList(3, 2, 1), new java.util.ArrayList<>(new LifoQueueProbe(Arrays.asList(1, 2, 3))));
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
        Queue<Integer> fifo = new FifoQueue<>(Arrays.asList(1, 2, 3));
        Queue<Integer> lifo = new LifoQueue<>(Arrays.asList(1, 2, 3));
        equal(Arrays.asList(3, 2, 1), collect(lifo));
        for (int i = 1; i <= 3; i++)
        {
            equal(i, fifo.poll());
            equal(4 - i, lifo.poll());
        }
        rejects(NullPointerException.class, () -> new FifoQueue<Integer>(null));
        rejects(NullPointerException.class, () -> new LifoQueue<Integer>(null));
        fifoBoundaries();
        lifoBoundaries();
        System.out.println("All " + checks + " checks passed.");
    }

    private static void fifoBoundaries()
    {
        Queue<Integer> fifo = new FifoQueue<>();
        rejects(NoSuchElementException.class, fifo::element);
        rejects(NoSuchElementException.class, () -> fifo.iterator().next());
        equal(true, fifo.offer(1));
        equal(1, fifo.peek());
        rejects(NullPointerException.class, () -> fifo.offer(null));
        equal(Collections.singletonList(1), collect(fifo));
        equal(1, fifo.poll());
        equal(0, fifo.size());
        equal(null, fifo.peek());
        equal(true, fifo.offer(2));
        equal(2, fifo.poll());

        // Removing the only element through the iterator must allow reuse.
        fifo.add(3);
        Iterator<Integer> it = fifo.iterator();
        equal(3, it.next());
        it.remove();
        equal(true, fifo.isEmpty());
        equal(null, fifo.poll());
        rejects(IllegalStateException.class, it::remove);
        rejects(NoSuchElementException.class, it::next);

        fifo.addAll(Arrays.asList(1, 2, 3, 4));
        it = fifo.iterator();
        equal(1, it.next());
        it.remove();
        equal(2, fifo.peek());
        equal(2, it.next());
        equal(3, it.next());
        it.remove();
        equal(4, it.next());
        it.remove();
        equal(false, it.hasNext());
        equal(Collections.singletonList(2), collect(fifo));
        equal(1, fifo.size());
        fifo.offer(5);
        equal(Arrays.asList(2, 5), collect(fifo));
        equal(2, fifo.poll());
        equal(5, fifo.poll());
        equal(null, fifo.poll());

        // Inherited bulk operations use the iterator; clear uses poll.
        fifo.addAll(Arrays.asList(1, 2, 1, 3));
        equal(true, fifo.remove(1));
        equal(Arrays.asList(2, 1, 3), collect(fifo));
        equal(true, fifo.removeAll(Arrays.asList(1, 3)));
        equal(Collections.singletonList(2), collect(fifo));
        equal(1, fifo.size());
        fifo.add(4);
        equal(Arrays.asList(2, 4), collect(fifo));
        equal(true, fifo.retainAll(Collections.emptyList()));
        equal(true, fifo.isEmpty());
        fifo.addAll(Arrays.asList(5, 6));
        fifo.clear();
        fifo.clear();
        equal(null, fifo.peek());
        equal(0, fifo.size());
        fifo.add(7);
        equal(Collections.singletonList(7), collect(fifo));
        equal(7, fifo.remove());
        equal(true, fifo.isEmpty());
    }

    private static void lifoBoundaries()
    {
        Queue<Integer> lifo = new LifoQueue<>(Arrays.asList(1, 2, 1, 3));
        equal(Arrays.asList(3, 1, 2, 1), collect(lifo));
        equal(3, lifo.peek());
        Iterator<Integer> it = lifo.iterator();
        equal(3, it.next());
        it.remove();
        equal(1, lifo.peek());
        equal(1, it.next());
        equal(2, it.next());
        it.remove();
        equal(1, it.next());
        it.remove();
        equal(false, it.hasNext());
        rejects(IllegalStateException.class, it::remove);
        rejects(NoSuchElementException.class, it::next);
        equal(Collections.singletonList(1), collect(lifo));
        equal(1, lifo.size());
        lifo.offer(4);
        equal(Arrays.asList(4, 1), collect(lifo));
        equal(4, lifo.poll());
        equal(1, lifo.poll());
        equal(null, lifo.peek());
        lifo.addAll(Arrays.asList(1, 2, 1, 3));
        equal(true, lifo.remove(1));
        equal(Arrays.asList(3, 2, 1), collect(lifo));
        equal(true, lifo.removeAll(Arrays.asList(1, 3)));
        equal(Collections.singletonList(2), collect(lifo));
        equal(true, lifo.retainAll(Collections.emptyList()));
        equal(true, lifo.isEmpty());
        lifo.offer(5);
        it = lifo.iterator();
        equal(5, it.next());
        it.remove();
        equal(0, lifo.size());
        lifo.addAll(Arrays.asList(6, 7));
        lifo.clear();
        lifo.offer(8);
        equal(8, lifo.remove());
        equal(true, lifo.isEmpty());
    }
}
