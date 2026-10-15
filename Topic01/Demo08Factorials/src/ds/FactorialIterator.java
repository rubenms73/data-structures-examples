package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Computes 0!, 1!, ..., (limit - 1)! as the iterator advances. */
public class FactorialIterator implements Iterator<Long>
{
    private long currentFactorial = 1;
    private int index = 0;
    private int limit;

    public FactorialIterator(int limit)
    {
        if (limit < 0)
            throw new IllegalArgumentException();

        this.limit = limit;
    }

    @Override
    public boolean hasNext()
    {
        return index < limit;
    }

    @Override
    public Long next()
    {
        if (!hasNext())
            throw new NoSuchElementException();

        long result = currentFactorial;

        index++;
        currentFactorial = currentFactorial * index;

        return result;
    }
}
