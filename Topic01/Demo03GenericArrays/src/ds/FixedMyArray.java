package ds;

/** Fixed-capacity storage using an ordinary array. */
public final class FixedMyArray<E> implements MyArray<E>
{
    private final E[] data;
    private int size;

    /** Creates an empty vector with the requested capacity. */
    @SuppressWarnings("unchecked")
    public FixedMyArray(int capacity)
    {
        data = (E[]) new Object[capacity];
    }

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

    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
    }
}
