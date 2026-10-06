package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import ds.ArrayBag;

public final class Main
{
    public static void main(String[] args)
    {
        Collection<String> list = new ArrayList<>(Arrays.asList("pear", "fig", "pear"));
        Collection<String> set = new LinkedHashSet<>(list);
        print("List", list);
        print("Set", set);
        System.out.println("Same arrivals: list size = " + list.size() + "; set size = " + set.size());
        System.out.println("list.add(pear): " + list.add("pear") + "; list = " + list);
        System.out.println("set.add(pear): " + set.add("pear") + "; set = " + set);

        ArrayBag<String> bag = new ArrayBag<>(list);
        System.out.println("Bag copied from the list: " + bag + "; size = " + bag.size());
        list.clear();
        System.out.println("Source after clear(): " + list + "; size = " + list.size());
        print("Bag after clearing its source", bag);
        System.out.println("Inherited contains(fig): " + bag.contains("fig"));
        System.out.println("Inherited isEmpty(): " + bag.isEmpty());
        try
        {
            bag.add("plum");
        }
        catch (UnsupportedOperationException e)
        {
            System.out.println("Adding to the bag: " + e.getClass().getSimpleName());
            System.out.println("Bag after rejected add: " + bag + "; size = " + bag.size());
        }
    }

    private static void print(String label, Iterable<String> values)
    {
        System.out.print(label + ":");
        for (String value : values)
        {
            System.out.print(" " + value);
        }
        System.out.println();
    }
}

