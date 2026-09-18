package app;

import ds.DoublyLinkedList;
import java.util.List;
import java.util.ListIterator;

public final class Main
{
    public static void main(String[] args)
    {
        List<Integer> values = new DoublyLinkedList<>(1, 2, 3);
        System.out.println("Initial: " + values);
        ListIterator<Integer> it = values.listIterator();
        it.add(0);
        System.out.println("Insert at start: " + values);
        System.out.println("Next: " + it.next());
        it.remove();
        System.out.println("Remove after next: " + values);
        System.out.println("Previous: " + it.previous());
        it.set(10);
        System.out.println("Set after previous: " + values);
        it.remove();
        System.out.println("Remove after previous: " + values);
        List<Number> copy = new DoublyLinkedList<>(values);
        copy.set(0, 2.5);
        System.out.println("Independent copy: " + copy);
        System.out.println("Original: " + values);
        values.clear();
        System.out.println("Inherited clear: " + values);
    }
}
