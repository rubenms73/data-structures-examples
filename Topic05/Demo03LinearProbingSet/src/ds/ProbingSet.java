package ds;

/**
 * A fixed-capacity set with open addressing and linear probing. Each slot is
 * EMPTY, OCCUPIED or DELETED; a removed key leaves DELETED so that searches
 * continue past it.
 */
public class ProbingSet<E>
{
    private static final byte EMPTY = 0, OCCUPIED = 1, DELETED = 2;
    private E[] keys;
    private byte[] state;
    private int size;

    @SuppressWarnings("unchecked")
    public ProbingSet(int capacity)
    {
        if (capacity <= 0)
            throw new IllegalArgumentException("Positive capacity required");
        keys = (E[]) new Object[capacity];
        state = new byte[capacity];   // all zeros: every slot is EMPTY
    }

    // First slot to try for key: its hash code reduced to a valid index.
    private int start(Object key)
    {
        if (key == null)
            throw new NullPointerException("Null key");
        int index = key.hashCode() % keys.length;
        if (index < 0)
            index += keys.length;
        return index;
    }

    // Slot that holds key, or -1. An EMPTY slot ends the search.
    private int find(Object key)
    {
        int pos = start(key);
        for (int probes = 0; probes < keys.length; probes++)
        {
            if (state[pos] == EMPTY)
                return -1;
            if (state[pos] == OCCUPIED && key.equals(keys[pos]))
                return pos;
            pos = (pos + 1) % keys.length;
        }
        return -1;
    }

    public boolean contains(Object key)
    {
        return find(key) >= 0;
    }

    private void insertAt(int pos, E key)
    {
        keys[pos] = key;
        state[pos] = OCCUPIED;
        size++;
    }

    public boolean add(E key)
    {
        int pos = start(key);
        int firstDeleted = -1;      // first DELETED slot seen, if any
        for (int probes = 0; probes < keys.length; probes++)
        {
            if (state[pos] == OCCUPIED && key.equals(keys[pos]))
                return false;
            if (state[pos] == DELETED && firstDeleted < 0)
                firstDeleted = pos;
            if (state[pos] == EMPTY)
            {
                // The key is absent: reuse a DELETED slot if we passed one.
                int target = firstDeleted >= 0 ? firstDeleted : pos;
                insertAt(target, key);
                return true;
            }
            pos = (pos + 1) % keys.length;
        }
        // Every slot was visited without finding the key or an EMPTY slot.
        if (firstDeleted < 0)
            throw new IllegalStateException("Full");
        insertAt(firstDeleted, key);
        return true;
    }

    public boolean remove(Object key)
    {
        int pos = find(key);
        if (pos < 0)
            return false;
        keys[pos] = null;
        state[pos] = DELETED;
        size--;
        return true;
    }

    public int size()
    {
        return size;
    }

    /** Shows every slot: a key, - for EMPTY or DEL for DELETED. */
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < keys.length; i++)
        {
            if (i > 0)
                result.append(", ");
            if (state[i] == OCCUPIED)
                result.append(keys[i]);
            else if (state[i] == DELETED)
                result.append("DEL");
            else
                result.append('-');
        }
        return result.append(']').toString();
    }
}
