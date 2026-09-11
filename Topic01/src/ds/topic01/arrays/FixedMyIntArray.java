package ds.topic01.arrays;

/** An integer sequence with fixed backing storage. */
public final class FixedMyIntArray extends AbstractMyIntArray {
    private final int[] data;
    private int size;

    /** @throws IllegalArgumentException if initialCapacity is negative */
    public FixedMyIntArray(int initialCapacity) {
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Negative capacity");
        data = new int[initialCapacity];
    }

    @Override public int size() { return size; }

    @Override
    public int get(int index) {
        checkIndex(index);
        return data[index];
    }

    @Override
    public void set(int index, int value) {
        checkIndex(index);
        data[index] = value;
    }

    @Override
    public void add(int value) {
        if (size == data.length)
            throw new IllegalStateException("Array is full");
        data[size++] = value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index);
    }

}
