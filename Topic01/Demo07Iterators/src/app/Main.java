package app;

import java.util.Iterator;
import java.util.NoSuchElementException;
import ds.FixedMyArray;
import ds.MyArray;

public final class Main
{
    public static void main(String[] args)
    {
        // Start with the same generic vector used in Demo03.
        MyArray<String> names = new FixedMyArray<>(4);
        names.add("Ana");
        names.add("Ruben");

        Iterator<String> first = names.iterator();
        Iterator<String> second = names.iterator();
        System.out.println("hasNext(): " + first.hasNext());
        System.out.println("hasNext() again: " + first.hasNext());
        System.out.println("first.next(): " + first.next());
        System.out.println("first.next(): " + first.next());
        System.out.println("second.next(): " + second.next());
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
        for (String name : names)
        {
            System.out.print(" " + name);
        }
        System.out.println();

        MyArray<Integer> scores = new FixedMyArray<>(4);
        scores.add(8);
        scores.add(10);
        // Copy through the MyArray interface, using its iterator.
        MyArray<Integer> copy = new FixedMyArray<>(scores);
        int sum = 0;
        for (int score : copy)
        {
            sum += score;
        }
        System.out.println("Integer vector sum: " + sum);
    }
}
