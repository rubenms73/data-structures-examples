package ds;

import java.util.Collection;
import java.util.Comparator;

/** Mutable bag kept in comparator order, or natural order when none is supplied. */
public final class SortedMutableBag<E> extends MutableBag<E>
{
    private final Comparator<? super E> comparator;

    public SortedMutableBag()
    {
        this(10, null);
    }

    public SortedMutableBag(int capacity)
    {
        this(capacity, null);
    }

    public SortedMutableBag(Comparator<? super E> comparator)
    {
        this(10, comparator);
    }

    public SortedMutableBag(int capacity, Comparator<? super E> comparator)
    {
        super(capacity);
        this.comparator = comparator;
    }

    public SortedMutableBag(Collection<? extends E> source)
    {
        this(source, null);
    }

    public SortedMutableBag(Collection<? extends E> source, Comparator<? super E> comparator)
    {
        this(source.size(), comparator);
        addAll(source);
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b)
    {
        if (comparator != null)
            return comparator.compare(a, b);
        if (!(a instanceof Comparable<?>))
            throw new ClassCastException("Element does not implement Comparable");
        return ((Comparable<? super E>) a).compareTo(b);
    }

    @Override
    public boolean add(E item)
    {
        if (item == null)
            throw new NullPointerException("Null elements are not supported");
        // Validate even the first element. No mutation occurs before comparisons finish.
        compare(item, item);
        int position = 0;
        while (position < numItems && compare(item, data[position]) >= 0)
        {
            position++;
        }
        super.add(item);
        System.arraycopy(data, position, data, position + 1, numItems - position - 1);
        data[position] = item;
        return true;
    }
}
