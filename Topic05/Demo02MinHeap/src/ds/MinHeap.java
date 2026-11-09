package ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A binary min-heap stored in an array: the children of position i are at
 * 2i + 1 and 2i + 2, and every parent is less than or equal to its children.
 */
public class MinHeap<E> implements MinPriorityQueue<E>
{
    private E[] data;
    private int size;
    private final Comparator<? super E> cmp;

    @SuppressWarnings("unchecked")
    public MinHeap(Comparator<? super E> cmp)
    {
        if (cmp == null)
            throw new NullPointerException("Null comparator");
        this.cmp = cmp;
        data = (E[]) new Object[8];
    }

    /** Builds a heap with the elements of c in O(n) time, bottom-up. */
    @SuppressWarnings("unchecked")
    public MinHeap(Collection<? extends E> c, Comparator<? super E> cmp)
    {
        if (cmp == null)
            throw new NullPointerException("Null comparator");
        this.cmp = cmp;
        data = (E[]) new Object[Math.max(8, c.size())];
        for (E e : c)
        {
            if (e == null)
                throw new NullPointerException("Null element");
            data[size] = e;
            size++;
        }
        // The leaves already satisfy heap order: sift down the internal nodes.
        for (int i = size / 2 - 1; i >= 0; i--)
        {
            siftDown(i, data[i]);
        }
    }

    private void ensureCapacity()
    {
        if (size == data.length)
            data = Arrays.copyOf(data, 2 * data.length);
    }

    @Override
    public boolean add(E e)
    {
        if (e == null)
            throw new NullPointerException("Null element");
        ensureCapacity();
        int pos = size;
        while (pos > 0)
        {
            int parent = (pos - 1) / 2;
            if (cmp.compare(e, data[parent]) >= 0)
                break;
            data[pos] = data[parent]; // move parent into the gap
            pos = parent;
        }
        data[pos] = e;
        size++;
        return true;
    }

    // Places e in the subtree rooted at pos, moving smaller children up.
    private void siftDown(int pos, E e)
    {
        while (pos < size / 2)   // pos has a left child
        {
            int child = 2 * pos + 1;
            int right = child + 1;
            if (right < size && cmp.compare(data[right], data[child]) < 0)
                child = right;
            if (cmp.compare(e, data[child]) <= 0)
                break;
            data[pos] = data[child];
            pos = child;
        }
        data[pos] = e;
    }

    @Override
    public E element()
    {
        if (size == 0)
            throw new NoSuchElementException();
        return data[0];
    }

    @Override
    public E remove()
    {
        E result = element();
        size--;
        E last = data[size];     // the last element replaces the root
        data[size] = null;
        if (size > 0)
            siftDown(0, last);
        return result;
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    @Override
    public void clear()
    {
        for (int i = 0; i < size; i++)
            data[i] = null;
        size = 0;
    }

    /** Returns the array positions in use, for tracing. */
    @Override
    public String toString()
    {
        return Arrays.toString(Arrays.copyOf(data, size));
    }
}
