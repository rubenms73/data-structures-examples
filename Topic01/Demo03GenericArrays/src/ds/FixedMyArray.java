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
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity must not be negative");
        data = (E[]) new Object[capacity];
    }

    /** The supplied array determines the capacity; a private copy is used. */
    public FixedMyArray(E[] storage)
    {
        if (storage == null)
            throw new NullPointerException("Storage must not be null");
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
