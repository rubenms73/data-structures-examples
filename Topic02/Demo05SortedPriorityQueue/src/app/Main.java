package app;

import ds.SortedPriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public final class Main
{
    public static void main(String[] args)
    {
        demonstrate("Natural order: smallest value leaves first", new SortedPriorityQueue<>());
        System.out.println();
        demonstrate("Reverse comparator: largest value leaves first",
                new SortedPriorityQueue<>(Comparator.reverseOrder()));
    }

    private static void demonstrate(String title, Queue<Integer> queue)
    {
        System.out.println(title);
        System.out.println("Initial: " + queue + "; size = " + queue.size());
        for (int value : List.of(7, 2, 5, 2))
        {
            queue.offer(value);
            System.out.println("offer(" + value + "): " + queue + "; size = " + queue.size());
        }
        // This implementation iterates in sorted order; both copies of 2 remain.
        System.out.println("peek(): " + queue.peek() + "; contents unchanged = " + queue);
        while (!queue.isEmpty())
        {
            System.out.println("remove(): " + queue.remove() + "; remaining = " + queue
                    + "; size = " + queue.size());
        }
        System.out.println("Empty peek(): " + queue.peek() + "; empty poll(): " + queue.poll());
    }
}
