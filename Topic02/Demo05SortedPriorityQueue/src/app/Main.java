package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        SortedPriorityQueue<Integer> queue = new SortedPriorityQueue<>();
        queue.addAll(Arrays.asList(7, 2, 5, 2));
        System.out.println("Sorted: " + queue);
        while (!queue.isEmpty())
        {
            System.out.println("Extract: " + queue.remove());
        }
    }
}
