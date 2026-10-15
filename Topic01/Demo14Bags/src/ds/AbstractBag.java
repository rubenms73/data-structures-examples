package ds;

import java.util.AbstractCollection;
import java.util.Objects;

/** Shared multiset equality. Only bags in this hierarchy are equal to bags. */
public abstract class AbstractBag<E> extends AbstractCollection<E>
{
    public final int occurrences(Object value)
    {
        int count = 0;
        for (E item : this)
        {
            if (Objects.equals(item, value))
                count++;
        }
        return count;
    }

    @Override
    public final boolean equals(Object other)
    {
        if (this == other)
            return true;
        if (!(other instanceof AbstractBag<?> bag) || size() != bag.size())
            return false;
        for (E item : this)
        {
            if (occurrences(item) != bag.occurrences(item))
                return false;
        }
        return true;
    }

    @Override
    public final int hashCode()
    {
        int hash = 0;
        for (E item : this)
        {
            hash += Objects.hashCode(item);
        }
        return hash;
    }
}
