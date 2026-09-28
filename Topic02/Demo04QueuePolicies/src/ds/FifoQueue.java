package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/** FIFO policy: insert at the end and extract at the beginning. */
public class FifoQueue<E> extends AbstractQueue<E>
{
    private final List<E> data = new DoublyLinkedList<>();

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
        data.add(value);
        return true;
    }

    @Override
    public E poll()
    {
        if (data.isEmpty())
            return null;
        return data.remove(0);
    }

    @Override
    public E peek()
    {
        if (data.isEmpty())
            return null;
        return data.get(0);
    }

    @Override
    public int size()
    {
        return data.size();
    }

    /** During traversal, modify the queue only through this iterator. */
    @Override
    public Iterator<E> iterator()
    {
        return data.iterator();
    }
}
