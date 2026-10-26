package ds;

import java.util.Iterator;

/**
 * An iterator that returns, one at a time, the subtrees whose roots are the
 * children of the root of an ordered tree. It can also replace and insert
 * children.
 *
 * @param <E> the type of the items
 */
public interface ChildrenIterator<E> extends Iterator<E>
{
    /**
     * Replaces the last element returned by {@code next()} with the specified
     * element (optional operation).
     *
     * @param e the replacement element
     * @throws IllegalStateException if {@code next()} has not been called, or
     *         if {@code remove()} or {@code add} has been called after the last
     *         call to {@code next()}
     * @throws NullPointerException if the tree does not allow {@code null}
     *         elements
     */
    default void set(E e)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Inserts the specified element immediately before the element that
     * {@code next()} would return (optional operation).
     *
     * @param e the element to insert
     * @throws NullPointerException if the tree does not allow {@code null}
     *         elements
     */
    default void add(E e)
    {
        throw new UnsupportedOperationException();
    }
}
