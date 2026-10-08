package ds;

import java.util.Arrays;

/** An integer sequence with growing backing storage. */
public final class DynamicMyIntArray extends AbstractMyIntArray
{
    // Resizable storage; the first size positions contain the logical elements.
    private int[] data;
    // Number of elements; data.length is the current capacity.
    private int size;

    /** @throws IllegalArgumentException if initialCapacity is negative */
    public DynamicMyIntArray(int initialCapacity)
    {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Negative capacity");
        data = new int[initialCapacity];
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public int get(int index)
    {
        checkIndex(index);
        return data[index];
    }

    @Override
    public void set(int index, int value)
    {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public void add(int value)
    {
        ensureCapacity();
        data[size++] = value;
    }

    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index);
    }

    private void ensureCapacity()
    {
        if (size < data.length)
            return;
        int newCapacity = data.length == 0 ? 1 : data.length * 2;
        data = Arrays.copyOf(data, newCapacity);
    }
}
