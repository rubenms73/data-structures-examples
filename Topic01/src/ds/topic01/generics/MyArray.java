package ds.topic01.generics;

/** The generic array interface used in this topic. Nulls are permitted. */
public interface MyArray<E> {
    int size();

    /** @throws IndexOutOfBoundsException if index is outside [0, size()) */
    E get(int index);

    /** Replaces an element; invalid indices throw IndexOutOfBoundsException. */
    void set(int index, E value);

    /** Appends value and returns true. */
    boolean add(E value);
}
