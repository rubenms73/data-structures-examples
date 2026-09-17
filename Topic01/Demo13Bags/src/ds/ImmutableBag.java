package ds;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/** Structurally immutable snapshot. Element objects themselves are not deep-copied. */
public final class ImmutableBag<E> extends AbstractBag<E>
{
    private final Object[] data;

    public ImmutableBag()
    {
        data = new Object[0];
    }

    @SafeVarargs
    public ImmutableBag(E... elements)
    {
        data = new Object[elements.length];
        for (int i = 0; i < elements.length; i++)
        {
            if (elements[i] == null)
                throw new NullPointerException("Null elements are not supported");
            data[i] = elements[i];
        }
    }

    public ImmutableBag(Collection<? extends E> source)
    {
        data = source.toArray();
        for (Object item : data)
        {
            if (item == null)
                throw new NullPointerException("Null elements are not supported");
        }
    }

    public ImmutableBag<E> withAdded(E item)
    {
        MutableBag<E> copy = new MutableBag<>(this);
        copy.add(item);
        return new ImmutableBag<>(copy);
    }

    public ImmutableBag<E> withoutOne(Object item)
    {
        MutableBag<E> copy = new MutableBag<>(this);
        if (!copy.remove(item))
            return this;
        return new ImmutableBag<>(copy);
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
        private int current;

        @Override
        public boolean hasNext()
        {
            return current < data.length;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) data[current++];
        }
    }

    @Override
    public boolean add(E item)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public boolean addAll(Collection<? extends E> source)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public boolean remove(Object item)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public boolean removeAll(Collection<?> source)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public boolean retainAll(Collection<?> source)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public boolean removeIf(Predicate<? super E> condition)
    {
        throw new UnsupportedOperationException("Immutable bag");
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("Immutable bag");
    }
}
