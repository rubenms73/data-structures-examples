package ds;

import java.util.NoSuchElementException;

/** A queue that always removes a minimum element first. */
public interface MinPriorityQueue<E>
{
    boolean isEmpty();

    int size();

    void clear();

    /**
     * Returns a minimum element without removing it.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    E element();

    /**
     * Removes and returns a minimum element.
     *
     * @throws NoSuchElementException if the queue is empty
     */
    E remove();

    boolean add(E value);
}
