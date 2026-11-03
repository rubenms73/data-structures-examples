package ds;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReadOnlySortedCollection<E> extends AbstractCollection<E>
{
    @SuppressWarnings("unchecked")
    private int compare(E first, E second)
    {
        if (comparator == null)
        {
            if (!(first instanceof Comparable<?>))
                throw new ClassCastException();

            return ((Comparable<E>) first).compareTo(second);
        }
        else
            return comparator.compare(first, second);
    }

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
    private int count;
    private final Comparator<? super E> comparator;

    public ReadOnlySortedCollection()
    {
        comparator = null;
    }

    public ReadOnlySortedCollection(Comparator<? super E> comparator)
    {
        this.comparator = comparator;
    }

    public ReadOnlySortedCollection(Collection<? extends E> source)
    {
        this(source, null);
    }

    @SuppressWarnings("unchecked")
    public ReadOnlySortedCollection(Collection<? extends E> source,
                                    Comparator<? super E> comparator)
    {
        this(comparator);

        if (source == null)
            throw new NullPointerException();

        if (source instanceof ReadOnlySortedCollection<?>)
        {
            // The source is read-only, so widening its element type is safe.
            ReadOnlySortedCollection<E> original = (ReadOnlySortedCollection<E>) source;

            if (comparator == original.comparator)
            {
                head = original.head;
                count = original.count;
                return;
            }
        }

        for (E e : source)
        {
            if (e == null)
                throw new NullPointerException();

            // Even a single element must have a natural order.
            if (head == null && comparator == null)
                compare(e, e);

            Node node = new Node(e);
            Node previous = null;
            Node current = head;

            // Insert after existing ties to preserve the source order.
            while (current != null && compare(current.value, e) <= 0)
            {
                previous = current;
                current = current.next;
            }

            node.next = current;
            if (previous == null)
                head = node;
            else
                previous.next = node;

            count++;
        }
    }

    @Override
    public int size()
    {
        return count;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ReadOnlySortedCollectionIterator();
    }

    private class ReadOnlySortedCollectionIterator implements Iterator<E>
    {
        Node next = head;

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

            E value = next.value;
            next = next.next;

            return value;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
