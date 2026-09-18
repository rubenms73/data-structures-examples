package ds;

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/** A small map implemented through its entrySet view. Null keys/values are allowed. */
public class ListMap<K, V> extends AbstractMap<K, V>
{
    private final ArrayList<Map.Entry<K, V>> entries = new ArrayList<>();

    @Override
    public V put(K key, V value)
    {
        for (Map.Entry<K, V> entry : entries)
        {
            boolean same;
            if (key == null)
                same = entry.getKey() == null;
            else
                same = key.equals(entry.getKey());
            if (same)
                return entry.setValue(value);
        }
        entries.add(new SimpleEntry<>(key, value));
        return null;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return new EntrySet();
    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>>
    {
        @Override
        public int size()
        {
            return entries.size();
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator()
        {
            return entries.iterator();
        }
    }
}
