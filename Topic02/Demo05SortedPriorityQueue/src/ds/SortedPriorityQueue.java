package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

/** A priority queue backed by a sorted list; smallest elements leave first. */
public class SortedPriorityQueue<E> extends AbstractQueue<E>
{
    private final LinkedList<E> data = new LinkedList<>();
    private final Comparator<? super E> order;

    public SortedPriorityQueue()
    {
        order = null;
    }

    public SortedPriorityQueue(Comparator<? super E> order)
    {
        this.order = order;
    }

    public SortedPriorityQueue(Collection<? extends E> source, Comparator<? super E> order)
    {
        this(order);
        if (source == null)
            throw new NullPointerException("Source must not be null");
        addAll(source);
    }

    @SuppressWarnings("unchecked")
    private int compare(E first, E second)
    {
        if (order != null)
            return order.compare(first, second);
        if (!(first instanceof Comparable<?>))
            throw new ClassCastException("Elements must implement Comparable");
        return ((Comparable<? super E>) first).compareTo(second);
    }

    @Override
    public boolean offer(E value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        // Validate before mutation, including insertion into an empty queue.
        compare(value, value);
        ListIterator<E> it = data.listIterator();
        while (it.hasNext())
        {
            if (compare(value, it.next()) < 0)
            {
                it.previous();
                it.add(value);
                return true;
            }
        }
        // Equal priorities retain arrival order.
        data.addLast(value);
        return true;
    }

    @Override
    public E poll()
    {
        if (data.isEmpty())
            return null;
        return data.removeFirst();
    }

    @Override
    public E peek()
    {
        if (data.isEmpty())
            return null;
        return data.getFirst();
    }

    @Override
    public int size()
    {
        return data.size();
    }

    @Override
    public Iterator<E> iterator()
    {
        return data.iterator();
    }
}
