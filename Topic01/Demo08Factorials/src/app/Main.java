package app;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ds.FactorialIterator;

public class Main
{
    public static void main(String[] args)
    {
        int n = 6;

        List<Long> repeated = new LinkedList<>();
        for (int k = 0; k < n; k++)
        {
            repeated.add(factorial(k));
        }
        System.out.println("Recomputing factorials: " + repeated);

        List<Long> incremental = new LinkedList<>();
        Iterator<Long> it = new FactorialIterator(n);
        while (it.hasNext())
        {
            incremental.add(it.next());
        }
        System.out.println("Using the iterator: " + incremental);
        System.out.println("More values: " + it.hasNext());
    }

    private static long factorial(int n)
    {
        long fact = 1;
        int k = 2;
        while (k <= n)
        {
            fact *= k;
            k++;
        }
        return fact;
    }
}
