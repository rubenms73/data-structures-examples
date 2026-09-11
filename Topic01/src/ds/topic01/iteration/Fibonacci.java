package ds.topic01.iteration;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** An iterable prefix of F0=0, F1=1, F2=1, ...; each iterator starts again. */
public final class Fibonacci implements Iterable<Long> {
    private static final int DEFAULT = 10;
    private final int num;

    public Fibonacci() { this(DEFAULT); }

    /** @throws IllegalArgumentException unless 0 <= n <= 93 */
    public Fibonacci(int n) {
        if (n < 0 || n > 93)
            throw new IllegalArgumentException("Expected 0 to 93 terms");
        num = n;
    }

    @Override public Iterator<Long> iterator() { return new FibIterator(); }

    private final class FibIterator implements Iterator<Long> {
        private int n = Fibonacci.this.num;
        private long a = 0;
        private long b = 1;

        @Override public boolean hasNext() { return n > 0; }

        @Override
        public Long next() {
            if (!hasNext()) throw new NoSuchElementException();
            long current = a;
            a = b;
            // Do not compute an unneeded term beyond the requested prefix.
            if (n > 2) b += current;
            n--;
            return current;
        }
    }
}
