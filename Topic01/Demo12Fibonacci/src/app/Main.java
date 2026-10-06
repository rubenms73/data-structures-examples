package app;

import java.util.Iterator;
import ds.Fibonacci;

public final class Main
{
    public static void main(String[] args)
    {
        Fibonacci sequence = new Fibonacci(10);
        System.out.print("First ten terms:");
        for (long value : sequence)
        {
            System.out.print(" " + value);
        }
        System.out.println();
        System.out.println("A fresh iterator starts again; traversal does not consume the sequence.");
        Iterator<Long> a = sequence.iterator();
        Iterator<Long> b = sequence.iterator();
        System.out.println("Iterator a: " + a.next() + ", " + a.next());
        System.out.println("Iterator b starts at: " + b.next());
        System.out.println("Independent positions: a.next() = " + a.next() + "; b.next() = " + b.next());
        System.out.println("A zero-term sequence hasNext(): " + new Fibonacci(0).iterator().hasNext());
        System.out.println("Request 94 terms, exceeding the safe long range:");
        try
        {
            new Fibonacci(94);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}

