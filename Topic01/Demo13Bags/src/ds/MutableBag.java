package ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** An unordered bag with mutable, array-backed storage. Nulls are rejected. */
public class MutableBag<E> extends AbstractBag<E>
{
    private static final int MIN_CAPACITY = 10;
    protected E[] data;
    protected int numItems;
    private int modificationCount;

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
            int capacity = (int) Math.min((long) data.length * 2, Integer.MAX_VALUE);
            if (capacity <= data.length)
                throw new OutOfMemoryError("Bag cannot grow further");
            data = Arrays.copyOf(data, capacity);
        }
        data[numItems++] = item;
        modificationCount++;
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
        modificationCount++;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new BagIterator();
    }

    private final class BagIterator implements Iterator<E>
    {
        private int current;
        private int lastReturned = -1;
        private int expectedModificationCount = modificationCount;

        private void checkForModification()
        {
            if (expectedModificationCount != modificationCount)
                throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext()
        {
            checkForModification();
            return current < numItems;
        }

        @Override
        public E next()
        {
            checkForModification();
            if (current >= numItems)
                throw new NoSuchElementException();
            lastReturned = current;
            return data[current++];
        }

        @Override
        public void remove()
        {
            checkForModification();
            if (lastReturned < 0)
                throw new IllegalStateException("Call next before remove");
            System.arraycopy(data, lastReturned + 1, data, lastReturned,
                    numItems - lastReturned - 1);
            data[--numItems] = null;
            current = lastReturned;
            lastReturned = -1;
            modificationCount++;
            expectedModificationCount = modificationCount;
        }
    }
}
