package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** A fixed-capacity stack. Null elements are allowed. */
public class ArrayStack<E> implements Stack<E>
{
    private final E[] data;
    private int size;

    public ArrayStack()
    {
        this(10);
    }

    /** Zero capacity is valid: such a stack cannot accept any element. */
    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity)
    {
        if (capacity < 0)
            throw new IllegalArgumentException("Capacity must be nonnegative");
        data = (E[]) new Object[capacity];
    }

    @Override
    public void push(E item)
    {
        if (size == data.length)
            throw new IllegalStateException("Stack is full");
        data[size++] = item;
    }

    @Override
    public E pop()
    {
        if (isEmpty())
            throw new NoSuchElementException("Stack is empty");
        E item = data[--size];
        // Release the reference so the removed object can be collected.
        data[size] = null;
        return item;
    }

    @Override
    public E peek()
    {
        if (isEmpty())
            throw new NoSuchElementException("Stack is empty");
        return data[size - 1];
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public boolean isEmpty()
    {
        return size == 0;
    }

    /** Do not modify the stack while traversing it. Removal is unsupported. */
    @Override
    public Iterator<E> iterator()
    {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E>
    {
        private int position = size - 1;

        @Override
        public boolean hasNext()
        {
            return position >= 0;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException("Iterator is exhausted");
            return data[position--];
        }
    }
}
