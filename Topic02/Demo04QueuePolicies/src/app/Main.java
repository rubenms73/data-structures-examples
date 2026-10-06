package app;

import ds.FifoQueue;
import ds.LifoQueue;
import java.util.NoSuchElementException;
import java.util.Queue;

public final class Main
{
    public static void main(String[] args)
    {
        Queue<Integer> fifo = new FifoQueue<>();
        Queue<Integer> lifo = new LifoQueue<>();
        System.out.println("1. Same arrivals, different insertion policies");
        show(fifo, lifo);
        for (int value = 1; value <= 3; value++)
        {
            fifo.offer(value);
            lifo.offer(value);
            System.out.println("offer(" + value + ") to both queues:");
            show(fifo, lifo);
        }

        System.out.println("\n2. Inspect and traverse without removing");
        System.out.println("FIFO peek(), oldest arrival: " + fifo.peek());
        System.out.println("LIFO peek(), newest arrival: " + lifo.peek());
        traverse("FIFO", fifo);
        traverse("LIFO", lifo);
        System.out.println("After inspection and traversal:");
        show(fifo, lifo);

        System.out.println("\n3. Extract, then accept a new arrival");
        removeBoth(fifo, lifo);
        fifo.offer(4);
        lifo.offer(4);
        System.out.println("offer(4) to both queues:");
        show(fifo, lifo);
        while (!fifo.isEmpty())
        {
            removeBoth(fifo, lifo);
        }

        System.out.println("\n4. Empty queues: special values and exceptions");
        emptyOperations("FIFO", fifo);
        emptyOperations("LIFO", lifo);
        // AbstractQueue.add() delegates to offer(); clear() uses the iterator.
        fifo.add(9);
        lifo.add(9);
        System.out.println("add(9): both queues can be reused");
        show(fifo, lifo);
        fifo.clear();
        lifo.clear();
        System.out.println("clear():");
        show(fifo, lifo);
    }

    private static void show(Queue<Integer> fifo, Queue<Integer> lifo)
    {
        System.out.println("  FIFO: " + fifo + "; size = " + fifo.size());
        System.out.println("  LIFO: " + lifo + "; size = " + lifo.size());
    }

    private static void traverse(String label, Queue<Integer> queue)
    {
        System.out.print(label + " for-each:");
        for (int value : queue)
        {
            System.out.print(" " + value);
        }
        System.out.println();
    }

    private static void removeBoth(Queue<Integer> fifo, Queue<Integer> lifo)
    {
        System.out.println("FIFO remove(): " + fifo.remove() + "; remaining = " + fifo);
        System.out.println("LIFO remove(): " + lifo.remove() + "; remaining = " + lifo);
    }

    private static void emptyOperations(String label, Queue<Integer> queue)
    {
        System.out.println(label + " peek(): " + queue.peek() + "; poll(): " + queue.poll());
        try
        {
            queue.element();
        }
        catch (NoSuchElementException ex)
        {
            System.out.println(label + " element(): NoSuchElementException");
        }
        try
        {
            queue.remove();
        }
        catch (NoSuchElementException ex)
        {
            System.out.println(label + " remove(): NoSuchElementException");
        }
        try
        {
            // null is reserved for the empty result of peek() and poll().
            queue.offer(null);
        }
        catch (NullPointerException ex)
        {
            System.out.println(label + " offer(null): NullPointerException; contents = " + queue);
        }
    }
}
