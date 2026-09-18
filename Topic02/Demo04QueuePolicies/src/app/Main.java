package app;

import ds.*;
import java.util.*;

public final class Main
{
    public static void main(String[] args)
    {
        Queue<Integer> fifo = new FifoQueue<>(Arrays.asList(1, 2, 3));
        Queue<Integer> lifo = new LifoQueue<>(Arrays.asList(1, 2, 3));
        System.out.println("FIFO iteration: " + fifo);
        System.out.println("LIFO iteration: " + lifo);
        while (!fifo.isEmpty())
        {
            System.out.println("FIFO/LIFO: " + fifo.remove() + "/" + lifo.remove());
        }
        System.out.println("Empty poll: " + fifo.poll());
    }
}
