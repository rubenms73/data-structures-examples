package ds;

/** A nonempty rooted tree with ordered children; no search ordering is assumed. */
public interface Tree<E>
{
    E label();
    Iterable<? extends Tree<E>> children();
    int size();
    int height();
}
