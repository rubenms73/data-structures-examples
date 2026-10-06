package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/** FIFO policy: insert at the end and extract at the beginning. */
public class FifoQueue<E> extends AbstractQueue<E>
{
    // Queue contents in arrival order: remove at index 0, append at the end.
    private final List<E> data = new DoublyLinkedList<>();

    public FifoQueue()
    {
    }

    public FifoQueue(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        // Initialise through a private helper, avoiding overridable offer/add.
        for (E value : source)
        {
            insert(value);
        }
    }

    @Override
    public boolean offer(E value)
    {
        return insert(value);
    }

    // Shared insertion logic for the constructor and the public queue operation.
    private boolean insert(E value)
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
