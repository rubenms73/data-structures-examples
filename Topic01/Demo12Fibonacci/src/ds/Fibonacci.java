package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** An iterable prefix of F0=0, F1=1, F2=1, ...; each iterator starts again. */
public final class Fibonacci implements Iterable<Long>
{
    // Number of terms produced by the no-argument constructor.
    private static final int DEFAULT = 10;
    // Length of the requested prefix, shared by all new iterators.
    private final int num;

    public Fibonacci()
    {
        this(DEFAULT);
    }

    /** @throws IllegalArgumentException unless 0 <= n <= 93 */
    public Fibonacci(int n)
    {
        if (n < 0 || n > 93)
            throw new IllegalArgumentException("Expected 0 to 93 terms");
        num = n;
    }

    @Override
    public Iterator<Long> iterator()
    {
        return new FibIterator();
    }

    private final class FibIterator implements Iterator<Long>
    {
        // Length of the requested prefix, shared by all new iterators.
        // Number of terms this iterator still has to return.
        private int n = Fibonacci.this.num;
        // Next Fibonacci term to return.
        private long a = 0;
        // Following term, used to advance the recurrence while more terms are needed.
        private long b = 1;

        @Override
        public boolean hasNext()
        {
            return n > 0;
        }

        @Override
        public Long next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            long current = a;
            a = b;
            // Do not compute an unneeded term beyond the requested prefix.
            if (n > 2)
                b += current;
            n--;
            return current;
        }
    }
}
