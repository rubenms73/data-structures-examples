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
    private final Object[] data;

    public ArrayBag(Collection<? extends E> source)
    {
        data = source.toArray();
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
        @SuppressWarnings("unchecked")
        public E next()
        {
            if (!hasNext()) throw new NoSuchElementException();
            // All entries came from Collection<? extends E>; data never changes.
            return (E) data[index++];
        }
    }
}
