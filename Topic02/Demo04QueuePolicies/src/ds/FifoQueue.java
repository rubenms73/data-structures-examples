package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** FIFO policy: insert at the end and extract at the beginning. */
public class FifoQueue<E> extends AbstractQueue<E>
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
    private int size;

    public FifoQueue()
    {
    }

    public FifoQueue(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        addAll(source);
    }

    @Override
    public boolean offer(E value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        Node node = new Node(value);
        if (tail == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
        return true;
    }

    @Override
    public E poll()
    {
        if (head == null)
            return null;
        E value = head.value;
        head = head.next;
        if (head == null)
            tail = null;
        size--;
        return value;
    }

    @Override
    public E peek()
    {
        if (head == null)
            return null;
        return head.value;
    }

    @Override
    public int size()
    {
        return size;
    }

    /** During traversal, modify the queue only through this iterator. */
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
