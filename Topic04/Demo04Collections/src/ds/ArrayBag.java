package ds;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A snapshot of a collection's element references, preserving duplicates and nulls.
 * Elements cannot be added or removed; the element objects are not copied.
 */
public final class ArrayBag<E> extends AbstractCollection<E>
{
    private final E[] data;

    /** Copy the element references into independent array storage. */
    @SuppressWarnings("unchecked")
    public ArrayBag(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        data = (E[]) new Object[source.size()];
        int index = 0;
        for (E element : source)
        {
            data[index++] = element;
        }
    }

    @Override
    public int size()
    {
        return data.length;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new BagIterator();
    }

    private final class BagIterator implements Iterator<E>
    {
        private int index;

        @Override
        public boolean hasNext()
        {
            return index < data.length;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return data[index++];
        }
    }
}
