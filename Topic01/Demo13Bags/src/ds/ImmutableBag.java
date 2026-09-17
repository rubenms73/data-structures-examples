package ds;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
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
        Objects.requireNonNull(elements);
        data = new Object[elements.length];
        for (int i = 0; i < elements.length; i++)
        {
            data[i] = Objects.requireNonNull(elements[i], "Null elements are not supported");
        }
    }

    public ImmutableBag(Collection<? extends E> source)
    {
        data = Objects.requireNonNull(source).toArray();
        for (Object item : data)
        {
            Objects.requireNonNull(item, "Null elements are not supported");
        }
    }

    // Only private operations pass newly allocated arrays to this constructor.
    private ImmutableBag(Object[] ownedData, boolean owned)
    {
        data = ownedData;
    }

    public ImmutableBag<E> withAdded(E item)
    {
        Objects.requireNonNull(item, "Null elements are not supported");
        Object[] copy = Arrays.copyOf(data, data.length + 1);
        copy[data.length] = item;
        return new ImmutableBag<E>(copy, true);
    }

    public ImmutableBag<E> withoutOne(Object item)
    {
        for (int i = 0; i < data.length; i++)
        {
            if (Objects.equals(data[i], item))
            {
                Object[] copy = new Object[data.length - 1];
                System.arraycopy(data, 0, copy, 0, i);
                System.arraycopy(data, i + 1, copy, i, data.length - i - 1);
                return new ImmutableBag<E>(copy, true);
            }
        }
        return this;
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
