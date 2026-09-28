package ds;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A collection backed by singly linked nodes, built on AbstractCollection.
 * The name describes its storage; this class does not implement List.
 * Null elements are allowed. During traversal, modify only through the iterator.
 */
public class SinglyLinkedList<E> extends AbstractCollection<E>
{
    private class Node
    {
        E value;
        Node next;

        Node(E value, Node next)
        {
            this.value = value;
            this.next = next;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    @Override
    public int size()
    {
        return size;
    }

    /** Append in constant time by retaining the last node. */
    @Override
    public boolean add(E value)
    {
        Node node = new Node(value, null);
        if (tail == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
        return true;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ForwardIterator();
    }

    private class ForwardIterator implements Iterator<E>
    {
        private Node next = head;
        private Node previous;
        private Node lastReturned;
        private Node beforeLast;

        @Override
        public boolean hasNext()
        {
            return next != null;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            beforeLast = previous;
            lastReturned = next;
            previous = next;
            next = next.next;
            return lastReturned.value;
        }

        @Override
        public void remove()
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            if (beforeLast == null)
                head = next;
            else
                beforeLast.next = next;
            if (lastReturned == tail)
                tail = beforeLast;
            previous = beforeLast;
            lastReturned = null;
            size--;
        }
    }
}
