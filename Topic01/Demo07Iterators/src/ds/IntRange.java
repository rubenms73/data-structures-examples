package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

/** Integers from start (inclusive) to end (exclusive). */
public final class IntRange implements Iterable<Integer> {
    private final int start;
    private final int end;

    /** @throws IllegalArgumentException if start is greater than end */
    public IntRange(int start, int end) {
        if (start > end)
            throw new IllegalArgumentException("Start is greater than end");
        this.start = start;
        this.end = end;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new RangeIterator();
    }

    private final class RangeIterator implements Iterator<Integer> {
        private int current = start;

        @Override public boolean hasNext() { return current < end; }

        @Override
        public Integer next() {
            if (!hasNext()) throw new NoSuchElementException();
            return current++;
        }
    }
}
