package ds;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;

/** An equality-based set backed by a list. Null is supported. */
public class ListSet<E> extends AbstractSet<E>
{
    private final ArrayList<E> data = new ArrayList<>();

    @Override
    public boolean add(E value)
    {
        if (contains(value))
            return false;
        return data.add(value);
    }

    @Override
    public int size()
    {
        return data.size();
    }

    @Override
    public Iterator<E> iterator()
    {
        return data.iterator();
    }
}
