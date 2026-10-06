package app;

import ds.ChainedHashSet;
import java.util.Iterator;
import java.util.Set;

public final class Main
{
    public static void main(String[] args)
    {
        ChainedHashSet<Integer> set = new ChainedHashSet<>(3, 0.75);
        System.out.println("1. Separate chaining; initial capacity 3, load limit 0.75");
        System.out.println("Initial: " + set + "; size = " + set.size());
        for (int value : new int[] {-1, Integer.MIN_VALUE, 7})
        {
            System.out.println("add(" + value + "): " + set.add(value)
                    + "; contents = " + set + "; size = " + set.size());
        }
        System.out.println("The third distinct insertion exceeds 3 * 0.75 and triggers rehashing.");
        System.out.println("Iteration follows buckets; it is not sorted or arrival order.");
        System.out.println("contains(-1): " + set.contains(-1));
        System.out.println("contains(Integer.MIN_VALUE): " + set.contains(Integer.MIN_VALUE));
        System.out.println("contains(99): " + set.contains(99));
        System.out.println("add(7) again: " + set.add(7) + "; size = " + set.size());
        System.out.println("remove(99): " + set.remove(99) + "; size = " + set.size());

        System.out.println("\n2. Equal hash codes do not imply equal elements");
        Set<String> collisions = new ChainedHashSet<>();
        System.out.println("Aa.hashCode(): " + "Aa".hashCode() + "; BB.hashCode(): " + "BB".hashCode());
        System.out.println("Aa.equals(BB): " + "Aa".equals("BB"));
        collisions.add("Aa");
        collisions.add("BB");
        System.out.println("Both strings remain: " + collisions + "; size = " + collisions.size());
        System.out.println("remove(Aa): " + collisions.remove("Aa")
                + "; contains(BB): " + collisions.contains("BB"));

        System.out.println("\n3. Iterator removal updates the total size");
        Iterator<Integer> it = set.iterator();
        while (it.hasNext())
        {
            int value = it.next();
            // hasNext() can cross a bucket boundary; remove() still targets value.
            boolean more = it.hasNext();
            System.out.println("next(): " + value + "; hasNext(): " + more);
            it.remove();
            System.out.println("iterator.remove(): " + set + "; size = " + set.size());
        }
        System.out.println("Empty: " + set.isEmpty());
        set.add(42);
        System.out.println("Reuse with add(42): " + set + "; size = " + set.size());
    }
}
