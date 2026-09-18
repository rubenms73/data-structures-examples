package ds;

/** A last-in, first-out collection. Iteration starts at the top. */
public interface Stack<E> extends Iterable<E>
{
    void push(E item);
    E pop();
    E peek();
    int size();
    boolean isEmpty();
}
