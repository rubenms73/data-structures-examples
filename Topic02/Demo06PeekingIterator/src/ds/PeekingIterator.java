package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** One-element lookahead. A separate flag permits null as an actual element. */
public class PeekingIterator<E> implements Iterator<E>
{
    private final Iterator<? extends E> source;
    private boolean available;
    private E next;

    public PeekingIterator(Iterable<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        this.source = source.iterator();
        advance();
    }

    private void advance()
    {
        available = source.hasNext();
        if (available)
            next = source.next();
        else
            next = null;
    }

    @Override
    public boolean hasNext()
    {
        return available;
    }

    public E peek()
    {
        if (!available)
            throw new NoSuchElementException();
        return next;
    }

    @Override
    public E next()
    {
        E result = peek();
        advance();
        return result;
    }
}
