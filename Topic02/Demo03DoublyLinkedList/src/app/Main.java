package app;

import ds.DoublyLinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Main
{
    public static void main(String[] args)
    {
        List<Integer> values = new DoublyLinkedList<>(1, 2, 3);
        ListIterator<Integer> it = values.listIterator();
        show("Initial list and cursor", values, it);
        // The cursor lies between elements; nextIndex() identifies its right side.
        it.add(0);
        show("iterator.add(0): insert before the cursor", values, it);
        System.out.println("next(): " + it.next());
        show("After moving forwards", values, it);
        it.remove();
        show("remove(): delete the element returned by next()", values, it);
        System.out.println("previous(): " + it.previous());
        show("After moving backwards", values, it);
        it.set(10);
        show("set(10): replace the element returned by previous()", values, it);
        it.remove();
        show("remove(): delete that element without skipping its successor", values, it);

        System.out.println("\nIndexed operations inherited from AbstractSequentialList:");
        System.out.println("get(1): " + values.get(1));
        List<Number> copy = new DoublyLinkedList<>(values);
        System.out.println("Copy before modification: " + copy);
        System.out.println("copy.set(0, 2.5) replaced: " + copy.set(0, 2.5));
        System.out.println("Copy after modification: " + copy);
        System.out.println("Original remains: " + values);
        values.clear();
        System.out.println("Original after clear(): " + values + "; size = " + values.size());
        System.out.println("Copy after clearing original: " + copy);
    }

    private static void show(String operation, List<Integer> values, ListIterator<Integer> it)
    {
        System.out.println(operation + ": " + values + "; size = " + values.size());
        System.out.println("  Cursor: previousIndex = " + it.previousIndex()
                + ", nextIndex = " + it.nextIndex()
                + ", hasPrevious = " + it.hasPrevious() + ", hasNext = " + it.hasNext());
    }
}
