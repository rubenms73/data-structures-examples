package ds;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;

/** LIFO policy represented as a Queue. Iteration follows extraction order. */
public class LifoQueue<E> extends AbstractQueue<E>
{
    private final ArrayList<E> data = new ArrayList<>();

    public LifoQueue()
    {
    }

    public LifoQueue(Collection<? extends E> source)
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
        return data.remove(data.size() - 1);
    }

    @Override
    public E peek()
    {
        if (data.isEmpty())
            return null;
        return data.get(data.size() - 1);
    }

    @Override
    public int size()
    {
        return data.size();
    }

    @Override
    public Iterator<E> iterator()
    {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E>
    {
        private final java.util.ListIterator<E> cursor = data.listIterator(data.size());

        @Override
        public boolean hasNext()
        {
            return cursor.hasPrevious();
        }

        @Override
        public E next()
        {
            return cursor.previous();
        }

        @Override
        public void remove()
        {
            cursor.remove();
        }
    }
}
