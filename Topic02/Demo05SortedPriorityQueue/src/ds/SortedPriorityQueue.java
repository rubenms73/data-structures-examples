package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/** A priority queue backed by a sorted list; smallest elements leave first. */
public class SortedPriorityQueue<E> extends AbstractQueue<E>
{
    // Elements in priority order; index 0 is the next element to leave.
    private final List<E> data = new DoublyLinkedList<>();
    // Comparison rule; null uses natural ordering. Smaller values leave first.
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
        // Initialise through a private helper, avoiding overridable offer/add.
        for (E value : source)
        {
            insert(value);
        }
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
        return insert(value);
    }

    // Shared insertion logic for the constructor and the public queue operation.
    private boolean insert(E value)
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
                // next() crossed the first larger element. Step back so add()
                // inserts before it, preserving the sorted order.
                it.previous();
                it.add(value);
                return true;
            }
        }
        // Equal priorities retain arrival order.
        it.add(value);
        return true;
    }

    @Override
    public E poll()
    {
        if (data.isEmpty())
            return null;
        return data.remove(0);
    }

    @Override
    public E peek()
    {
        if (data.isEmpty())
            return null;
        return data.get(0);
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
