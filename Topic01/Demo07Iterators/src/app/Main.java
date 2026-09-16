package app;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ds.IntRange;

public final class Main
{
    public static void main(String[] args)
    {
        IntRange range = new IntRange(2, 5);
        Iterator<Integer> first = range.iterator();
        Iterator<Integer> second = range.iterator();
        System.out.println("hasNext(): " + first.hasNext());
        System.out.println("hasNext() again: " + first.hasNext());
        System.out.println("first.next(): " + first.next());
        System.out.println("first.next(): " + first.next());
        System.out.println("second.next(): " + second.next());
        System.out.println("first.next(): " + first.next());
        System.out.println("first.hasNext(): " + first.hasNext());
        try
        {
            first.next();
        }
        catch (NoSuchElementException e)
        {
            System.out.println("Next after the end: " + e.getClass().getSimpleName());
        }
        System.out.print("A fresh enhanced for loop:");
        for (int value : range) System.out.print(" " + value);
        System.out.println();
    }
}
