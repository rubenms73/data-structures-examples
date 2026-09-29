package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** One-element lookahead. A separate flag permits null as an actual element. */
public class PeekingIterator<E> implements Iterator<E>
{
    // Underlying iterator; it has already advanced past the cached element.
    private final Iterator<? extends E> source;
    // Whether the cache contains an element, even if that element is null.
    private boolean available;
    // Cached element shared by peek() and the next call to next().
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
        // Save the cached element before fetching its successor.
        // peek() alone never consumes the cached element.
        E result = peek();
        advance();
        return result;
    }
}
