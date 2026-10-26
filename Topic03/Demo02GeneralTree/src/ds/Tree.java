package ds;

/**
 * An ordered general tree: a root label and a sequence of child subtrees.
 *
 * @param <E> the type of the labels
 */
public interface Tree<E>
{
    /**
     * Returns whether the root of this tree is a leaf.
     *
     * @return {@code true} if the root of this tree has no children
     */
    boolean isLeaf();

    /**
     * Returns the label of the root of this tree.
     *
     * @return the root label
     */
    E label();

    /**
     * Returns an iterator over the subtrees whose roots are the children of
     * the root of this tree, in order.
     *
     * @return an iterator over the child subtrees
     */
    ChildrenIterator<Tree<E>> childrenIterator();

    /**
     * Replaces the label of the root of this tree (optional operation).
     *
     * @param e the new root label
     * @throws UnsupportedOperationException if this tree does not support
     *         the operation
     * @throws NullPointerException if this tree does not allow {@code null}
     *         labels
     */
    default void setLabel(E e)
    {
        throw new UnsupportedOperationException();
    }
}
