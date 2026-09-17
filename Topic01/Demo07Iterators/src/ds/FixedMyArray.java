package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Fixed-capacity storage using an ordinary array. */
public final class FixedMyArray<E> implements MyArray<E>
{
    private final E[] data;
    private int size;

    /** The supplied array determines the capacity; a private copy is used. */
    public FixedMyArray(E[] storage)
    {
        data = storage.clone();
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public E get(int index)
    {
        checkIndex(index);
        return data[index];
    }

    @Override
    public void set(int index, E value)
    {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public boolean add(E value)
    {
        if (size == data.length)
            throw new IllegalStateException("Array is full");
        data[size++] = value;
        return true;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ArrayIterator();
    }

    private final class ArrayIterator implements Iterator<E>
    {
        private int current;

        @Override
        public boolean hasNext()
        {
            return current < size;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            return data[current++];
        }
    }

    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }
}
