package ds;

import java.util.Collection;
import java.util.Comparator;

/** Mutable bag kept in comparator order, or natural order when none is supplied. */
public final class SortedMutableBag<E> extends MutableBag<E>
{
    // Ordering used on insertion; null selects the elements' natural ordering.
    private final Comparator<? super E> cmp;

    public SortedMutableBag()
    {
        this(10, null);
    }

    public SortedMutableBag(int capacity)
    {
        this(capacity, null);
    }

    public SortedMutableBag(Comparator<? super E> cmp)
    {
        this(10, cmp);
    }

    public SortedMutableBag(int capacity, Comparator<? super E> cmp)
    {
        super(capacity);
        this.cmp = cmp;
    }

    public SortedMutableBag(Collection<? extends E> source)
    {
        this(source, null);
    }

    public SortedMutableBag(Collection<? extends E> source, Comparator<? super E> cmp)
    {
        this(source.size(), cmp);
        addAll(source);
    }

    @SuppressWarnings("unchecked")
    private int compare(E a, E b)
    {
        if (cmp != null)
            return cmp.compare(a, b);
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
        // Let the base class grow the array and update its size.
        // Then move the appended item to the sorted insertion position.
        super.add(item);
        System.arraycopy(data, position, data, position + 1, numItems - position - 1);
        data[position] = item;
        return true;
    }
}
