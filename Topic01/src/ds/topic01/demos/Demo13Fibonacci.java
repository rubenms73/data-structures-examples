package ds.topic01.demos;

import java.util.Iterator;
import ds.topic01.iteration.Fibonacci;

public final class Demo13Fibonacci {
    public static void main(String[] args) {
        Fibonacci sequence = new Fibonacci(10);
        System.out.print("First ten terms:");
        for (long value : sequence) System.out.print(" " + value);
        System.out.println();
        Iterator<Long> a = sequence.iterator();
        Iterator<Long> b = sequence.iterator();
        System.out.println("Iterator a: " + a.next() + ", " + a.next());
        System.out.println("Iterator b starts at: " + b.next());
        try {
            new Fibonacci(94);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
    }
}
