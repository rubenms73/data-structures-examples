package ds;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/** Separate chaining with equality-based buckets; no Comparable requirement. */
public class ChainedHashSet<E> extends AbstractSet<E>
{
    private ArrayList<LinkedList<E>> buckets;
    private int size;
    private final double loadLimit;

    public ChainedHashSet()
    {
        this(11, 0.75);
    }

    public ChainedHashSet(int capacity, double loadLimit)
    {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity must be positive");
        if (!(loadLimit > 0) || loadLimit == Double.POSITIVE_INFINITY)
            throw new IllegalArgumentException("Load limit must be positive and finite");
        this.loadLimit = loadLimit;
        buckets = newBuckets(capacity);
    }

    private ArrayList<LinkedList<E>> newBuckets(int capacity)
    {
        ArrayList<LinkedList<E>> result = new ArrayList<>();
        for (int i = 0; i < capacity; i++)
        {
            result.add(new LinkedList<>());
        }
        return result;
    }

    private int index(Object value, int capacity)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        int result = value.hashCode() % capacity;
        // This also works for Integer.MIN_VALUE; Math.abs would not.
        if (result < 0)
            result += capacity;
        return result;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean contains(Object value)
    {
        return buckets.get(index(value, buckets.size())).contains(value);
    }

    @Override
    public boolean add(E value)
    {
        if (contains(value))
            return false;
        if ((size + 1.0) / buckets.size() > loadLimit)
            grow();
        buckets.get(index(value, buckets.size())).add(value);
        size++;
        return true;
    }

    private void grow()
    {
        if (buckets.size() > (Integer.MAX_VALUE - 1) / 2)
            throw new IllegalStateException("Table cannot grow further");
        ArrayList<LinkedList<E>> larger = newBuckets(buckets.size() * 2 + 1);
        for (LinkedList<E> bucket : buckets)
        {
            for (E value : bucket)
            {
                larger.get(index(value, larger.size())).add(value);
            }
        }
        buckets = larger;
    }

    @Override
    public boolean remove(Object value)
    {
        if (!buckets.get(index(value, buckets.size())).remove(value))
            return false;
        size--;
        return true;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new TableIterator();
    }

    private class TableIterator implements Iterator<E>
    {
        private int bucketIndex;
        private Iterator<E> current = buckets.get(0).iterator();
        private Iterator<E> lastUsed;

        @Override
        public boolean hasNext()
        {
            while (!current.hasNext() && bucketIndex + 1 < buckets.size())
            {
                current = buckets.get(++bucketIndex).iterator();
            }
            return current.hasNext();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            E result = current.next();
            lastUsed = current;
            return result;
        }

        @Override
        public void remove()
        {
            if (lastUsed == null)
                throw new IllegalStateException();
            // hasNext may have moved to a different bucket since the last next.
            lastUsed.remove();
            lastUsed = null;
            size--;
        }
    }
}
