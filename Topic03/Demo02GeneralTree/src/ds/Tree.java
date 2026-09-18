package ds;

/** A nonempty rooted tree with ordered children; no search ordering is assumed. */
public interface Tree<E>
{
    E label();
    Iterable<? extends Tree<E>> children();
    int size();
    /** Height in nodes, as in Topic 3. This nonempty interface has minimum height 1. */
    int height();
}
