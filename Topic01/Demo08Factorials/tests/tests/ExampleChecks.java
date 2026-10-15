package tests;

import java.util.NoSuchElementException;
import ds.FactorialIterator;

public class ExampleChecks
{
    private static int checks;

    public static void main(String[] args)
    {
        long[] expected = {1, 1, 2, 6, 24, 120};
        FactorialIterator it = new FactorialIterator(expected.length);
        for (long value : expected)
        {
            check(it.hasNext());
            check(it.hasNext()); // Querying must not consume a value.
            check(it.next() == value);
        }
        check(!it.hasNext());
        try
        {
            it.next();
            throw new AssertionError("Expected NoSuchElementException");
        }
        catch (NoSuchElementException expectedException)
        {
            check(!it.hasNext());
        }

        FactorialIterator empty = new FactorialIterator(0);
        check(!empty.hasNext());
        try
        {
            empty.next();
            throw new AssertionError("Expected NoSuchElementException");
        }
        catch (NoSuchElementException expectedException)
        {
            check(!empty.hasNext());
        }

        try
        {
            new FactorialIterator(-1);
            throw new AssertionError("Expected IllegalArgumentException");
        }
        catch (IllegalArgumentException expectedException)
        {
            checks++;
        }

        FactorialIterator single = new FactorialIterator(1);
        check(single.next() == 1L);
        check(!single.hasNext());

        FactorialIterator first = new FactorialIterator(3);
        FactorialIterator second = new FactorialIterator(3);
        first.next();
        first.next();
        check(second.next() == 1L);
        check(first.next() == 2L);

        FactorialIterator largest = new FactorialIterator(21);
        long last = 0;
        while (largest.hasNext())
        {
            last = largest.next();
        }
        check(last == 2432902008176640000L); // 20!
        System.out.println("All " + checks + " checks passed.");
    }

    private static void check(boolean condition)
    {
        checks++;
        if (!condition)
            throw new AssertionError("Check " + checks + " failed");
    }
}
