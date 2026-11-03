package ds;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class UniqueLinkedCollection<E> extends AbstractCollection<E>
{
    private class Node
    {
        E value;
        Node next;

        Node(E value)
        {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int count;

    public UniqueLinkedCollection()
    {
    }

    public UniqueLinkedCollection(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException();

        for (E e : source)
            add(e);
    }

    @Override
    public int size()
    {
        return count;
    }

    @Override
    public boolean add(E e)
    {
        if (e == null)
            throw new NullPointerException();

        if (contains(e))
            return false;

        Node node = new Node(e);

        if (tail == null)
            head = node;
        else
            tail.next = node;

        tail = node;
        count++;

        return true;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new UniqueLinkedCollectionIterator();
    }

    // The predecessor links allow removal without scanning the chain.
    private class UniqueLinkedCollectionIterator implements Iterator<E>
    {
        Node next = head;
        Node predecessor;
        Node returned;
        Node beforeReturned;
        boolean removable;

        @Override
        public boolean hasNext()
        {
            return next != null;
        }

        @Override
        public E next()
        {
            if (next == null)
                throw new NoSuchElementException();

            beforeReturned = predecessor;
            returned = next;
            predecessor = returned;
            next = next.next;
            removable = true;

            return returned.value;
        }

        @Override
        public void remove()
        {
            if (!removable)
                throw new IllegalStateException();

            if (beforeReturned == null)
                head = next;
            else
                beforeReturned.next = next;

            if (returned == tail)
                tail = beforeReturned;

            // After removal, the next node has the same predecessor as the removed node.
            predecessor = beforeReturned;
            count--;
            removable = false;
        }
    }
}
