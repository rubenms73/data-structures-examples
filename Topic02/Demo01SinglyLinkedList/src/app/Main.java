package app;

import ds.SinglyLinkedList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class Main
{
    public static void main(String[] args)
    {
        Collection<String> values = new SinglyLinkedList<>();
        System.out.println("Initial collection: " + values + "; size = " + values.size());
        for (String value : List.of("A", "B", "C"))
        {
            values.add(value);
            System.out.println("add(" + value + "): " + values + "; size = " + values.size());
        }
        System.out.println("\nTraversal with for-each:");
        for (String value : values)
        {
            System.out.println("Visit: " + value);
        }
        System.out.println("After traversal: " + values);
        System.out.println("\nRemove C through the iterator:");
        Iterator<String> it = values.iterator();
        while (it.hasNext())
        {
            String value = it.next();
            System.out.println("next(): " + value);
            if (value.equals("C"))
            {
                // remove() deletes the element returned by the most recent next().
                it.remove();
                System.out.println("iterator.remove(): " + values);
            }
        }
        values.add("D");
        System.out.println("add(D), reusing the tail after removal: " + values);
        System.out.println("\nInherited Collection operations:");
        System.out.println("contains(B): " + values.contains("B"));
        System.out.println("remove(B): " + values.remove("B") + "; contents = " + values);
        System.out.println("remove(X): " + values.remove("X") + "; contents = " + values);
        values.clear();
        System.out.println("clear(): " + values + "; size = " + values.size()
                + "; empty = " + values.isEmpty());
    }
}
