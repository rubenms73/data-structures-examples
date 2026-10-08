package ds;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** A growing array stack. Null elements are allowed. */
public class ArrayStack<E> implements Stack<E>
{
    // Storage for the stack, with spare capacity after its occupied prefix.
    private E[] data;
    // Number of elements; the top is at size - 1 when nonempty.
    private int size;

    public ArrayStack()
    {
        this(10);
    }

    /** The initial capacity may be zero: the first insertion grows it to one. */
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
        {
            int capacity = data.length == 0 ? 1 : data.length * 2;
            // Copy references into a larger array before replacing the old one.
            data = Arrays.copyOf(data, capacity);
        }
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

    /**
     * Iterable/Iterator is Java's standard enhanced-for protocol.
     * Do not modify the stack while traversing it. Removal is unsupported.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E>
    {
        // Next position to visit, moving downwards from the top of the stack.
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
