package ds;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A set over a finite universe stored as an array of bits: bits[i] is true when
 * the element with index i is present. Subclasses choose the universe by
 * converting between elements and indices.
 */
public abstract class BitArraySet<E> extends AbstractSet<E>
{
    // bits[i] is true when the element with index i is present.
    private final boolean[] bits;
    private int count;

    protected BitArraySet(int universeSize)
    {
        if (universeSize <= 0)
            throw new IllegalArgumentException("The universe must not be empty");
        bits = new boolean[universeSize];
    }

    /** Returns the index of e; equal elements must have the same index. */
    protected abstract int index(E e);

    /** Returns the element with the given index: element(index(e)) equals e. */
    protected abstract E element(int index);

    private int checkedIndex(E e)
    {
        if (e == null)
            throw new NullPointerException("Null element");
        int i = index(e);
        if (i < 0 || i >= bits.length)
            throw new IllegalArgumentException("Outside universe: " + e);
        return i;
    }

    @Override
    public boolean add(E e)
    {
        int i = checkedIndex(e);
        if (bits[i])
            return false;
        bits[i] = true;
        count++;
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o)
    {
        if (o == null)
            throw new NullPointerException("Null element");
        int i = index((E) o);
        // An element outside the universe is simply not in the set.
        return i >= 0 && i < bits.length && bits[i];
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o)
    {
        if (o == null)
            throw new NullPointerException("Null element");
        int i = index((E) o);
        if (i < 0 || i >= bits.length || !bits[i])
            return false;
        bits[i] = false;
        count--;
        return true;
    }

    @Override
    public int size()
    {
        return count;
    }

    @Override
    public void clear()
    {
        Arrays.fill(bits, false);
        count = 0;
    }

    // First index i >= from with bits[i] true, or -1 if there is none.
    private int nextIndex(int from)
    {
        for (int i = from; i < bits.length; i++)
        {
            if (bits[i])
                return i;
        }
        return -1;
    }

    /** Returns the elements in index order; a complete traversal takes O(U). */
    @Override
    public Iterator<E> iterator()
    {
        return new Iterator<>()
        {
            private int next = nextIndex(0);
            private int last = -1;

            @Override
            public boolean hasNext()
            {
                return next >= 0;
            }

            @Override
            public E next()
            {
                if (!hasNext())
                    throw new NoSuchElementException();
                last = next;
                next = nextIndex(next + 1);
                return element(last);
            }

            @Override
            public void remove()
            {
                if (last < 0)
                    throw new IllegalStateException();
                bits[last] = false;
                count--;
                last = -1;
            }
        };
    }
}
