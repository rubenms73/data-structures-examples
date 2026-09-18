package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

/** FIFO policy: insert at the end and extract at the beginning. */
public class FifoQueue<E> extends AbstractQueue<E>
{
    private final LinkedList<E> data = new LinkedList<>();

    public FifoQueue()
    {
    }

    public FifoQueue(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        addAll(source);
    }

    @Override
    public boolean offer(E value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        data.addLast(value);
        return true;
    }

    @Override
    public E poll()
    {
        if (data.isEmpty())
            return null;
        return data.removeFirst();
    }

    @Override
    public E peek()
    {
        if (data.isEmpty())
            return null;
        return data.getFirst();
    }

    @Override
    public int size()
    {
        return data.size();
    }

    @Override
    public Iterator<E> iterator()
    {
        return data.iterator();
    }
}
