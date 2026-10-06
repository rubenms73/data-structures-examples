package app;

import ds.PeekingIterator;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public final class Main
{
    public static void main(String[] args)
    {
        List<String> source = Arrays.asList("A", null, "B");
        PeekingIterator<String> it = new PeekingIterator<>(source);
        System.out.println("Source: " + source);
        System.out.println("Repeated peek() keeps the same cached element:");
        while (it.hasNext())
        {
            System.out.println("hasNext(): " + it.hasNext());
            System.out.println("  First peek(): " + it.peek());
            System.out.println("  Second peek(): " + it.peek());
            System.out.println("  next(), consuming that element: " + it.next());
        }
        // A null element is valid; hasNext(), rather than peek() == null,
        // distinguishes an available element from an exhausted iterator.
        System.out.println("After traversal, hasNext(): " + it.hasNext());
        try
        {
            it.peek();
        }
        catch (NoSuchElementException ex)
        {
            System.out.println("Exhausted peek(): NoSuchElementException");
        }
        try
        {
            it.next();
        }
        catch (NoSuchElementException ex)
        {
            System.out.println("Exhausted next(): NoSuchElementException");
        }
        System.out.println("Source after traversal: " + source);
    }
}
