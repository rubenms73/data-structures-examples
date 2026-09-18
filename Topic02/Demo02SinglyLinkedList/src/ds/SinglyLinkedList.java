package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A forward-only list: each node owns one link. Null elements are allowed.
 * Modify the list through the active iterator only while traversing it.
 */
public class SinglyLinkedList<E> implements Iterable<E>
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

    public int size()
    {
        return size;
    }

    /** Append in constant time by retaining the last node. */
    public void add(E value)
    {
        Node node = new Node(value, null);
        if (tail == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
    }

    public void insert(int index, E value)
    {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        if (index == size)
        {
            add(value);
            return;
        }
        if (index == 0)
            head = new Node(value, head);
        else
        {
            Node previous = head;
            for (int i = 1; i < index; i++)
            {
                previous = previous.next;
            }
            previous.next = new Node(value, previous.next);
        }
        size++;
    }

    public E get(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        Node current = head;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.value;
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
