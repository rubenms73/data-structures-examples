package ds;

/** An ordered sequence of integers, accessed through zero-based indices. */
public interface MyIntArray
{
    /** Returns the number of stored elements. */
    int size();

    /**
     * Returns the element at index, without changing the sequence.
     * @throws IndexOutOfBoundsException if index is outside [0, size())
     */
    int get(int index);

    /**
     * Replaces the element at index, without changing the size.
     * @throws IndexOutOfBoundsException if index is outside [0, size())
     */
    void set(int index, int value);

    /**
     * Appends value and increases size by one if successful.
     * A fixed implementation throws IllegalStateException when full,
     * leaving the contents unchanged. A dynamic implementation grows.
     */
    void add(int value);

    /** Returns true if there are no stored elements. */
    boolean isEmpty();

    /** Returns true if at least one stored element equals value. */
    boolean contains(int value);
}
