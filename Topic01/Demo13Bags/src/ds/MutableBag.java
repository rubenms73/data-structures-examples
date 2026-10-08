package ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** An unordered bag with mutable, array-backed storage. Nulls are rejected. */
public class MutableBag<E> extends AbstractBag<E>
{
    // Smallest allocated array, including for a requested capacity of zero.
    private static final int MIN_CAPACITY = 10;
    // Storage for the occupied prefix data[0..numItems-1] and spare capacity.
    protected E[] data;
    // Number of elements in the occupied prefix.
    protected int numItems;

    public MutableBag()
    {
        this(MIN_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public MutableBag(int capacity)
    {
        if (capacity < 0)
            throw new IllegalArgumentException("Negative capacity");
        // Java cannot create new E[]; keep the unchecked cast at allocation.
        data = (E[]) new Object[Math.max(capacity, MIN_CAPACITY)];
    }

    public MutableBag(Collection<? extends E> source)
    {
        this(source.size());
        // Do not call an overridable method from a constructor.
        for (E item : source)
        {
            append(item);
        }
    }

    @Override
    public int size()
    {
        return numItems;
    }

    private void append(E item)
    {
        if (item == null)
            throw new NullPointerException("Null elements are not supported");
        if (numItems == data.length)
        {
            int capacity = data.length * 2;
            data = Arrays.copyOf(data, capacity);
        }
        data[numItems++] = item;
    }

    @Override
    public boolean add(E item)
    {
        append(item);
        return true;
    }

    @Override
    public void clear()
    {
        if (numItems == 0)
            return;
        Arrays.fill(data, 0, numItems, null);
        numItems = 0;
    }

    /** Modify through this iterator only while traversing the bag. */
    @Override
    public Iterator<E> iterator()
    {
        return new BagIterator();
    }

    private final class BagIterator implements Iterator<E>
    {
        // Index of the next element to return.
        private int current;
        // Index eligible for removal; -1 means that next() must be called first.
        private int lastReturned = -1;

        @Override
        public boolean hasNext()
        {
            return current < numItems;
        }

        @Override
        public E next()
        {
            if (current >= numItems)
                throw new NoSuchElementException();
            lastReturned = current;
            return data[current++];
        }

        @Override
        public void remove()
        {
            if (lastReturned < 0)
                throw new IllegalStateException("Call next before remove");
            // Close the gap while preserving the order of the remaining elements.
            System.arraycopy(data, lastReturned + 1, data, lastReturned,
                    numItems - lastReturned - 1);
            data[--numItems] = null;
            // The next unvisited element now occupies the removed position.
            current = lastReturned;
            lastReturned = -1;
        }
    }
}
