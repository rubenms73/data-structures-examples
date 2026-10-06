package app;

import ds.ListMap;
import ds.ListSet;
import java.util.Map;
import java.util.Set;

public final class Main
{
    public static void main(String[] args)
    {
        Set<String> set = new ListSet<>();
        System.out.println("1. A set stores each distinct element once");
        System.out.println("Initial: " + set + "; size = " + set.size());
        for (String value : new String[] {"A", "A", "B"})
        {
            System.out.println("add(" + value + "): " + set.add(value)
                    + "; set = " + set + "; size = " + set.size());
        }
        System.out.println("contains(A): " + set.contains("A") + "; contains(X): " + set.contains("X"));
        System.out.println("remove(A): " + set.remove("A") + "; set = " + set);
        System.out.println("remove(A) again: " + set.remove("A") + "; set = " + set);

        Map<String, Integer> map = new ListMap<>();
        System.out.println("\n2. A map associates one value with each key");
        System.out.println("put(A, 1), previous value: " + map.put("A", 1) + "; map = " + map);
        System.out.println("put(A, 2), previous value: " + map.put("A", 2)
                + "; map = " + map + "; size = " + map.size());
        System.out.println("put(B, 3), previous value: " + map.put("B", 3) + "; map = " + map);
        System.out.println("get(A): " + map.get("A") + "; get(X): " + map.get("X"));

        // These are live views: removing a key or value removes its map entry.
        Set<String> keys = map.keySet();
        System.out.println("\n3. Collection views are connected to the map");
        System.out.println("Keys: " + keys + "; values: " + map.values() + "; entries: " + map.entrySet());
        System.out.println("keys.remove(B): " + keys.remove("B") + "; map = " + map);
        map.put("C", 4);
        System.out.println("put(C, 4): map = " + map + "; existing key view = " + keys);
        System.out.println("values().remove(2): " + map.values().remove(2) + "; map = " + map);
        map.clear();
        System.out.println("clear(): map = " + map + "; existing key view = " + keys);
    }
}
