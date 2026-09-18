package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void rejects(Class<? extends Exception> type, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Exception e)
        {
            if (type.isInstance(e))
                return;
            throw new AssertionError(e);
        }
        throw new AssertionError("Expected " + type);
    }

    private static <E> List<E> collect(Iterable<E> source)
    {
        List<E> result = new ArrayList<>();
        for (E value : source)
        {
            result.add(value);
        }
        return result;
    }

    public static void main(String[] args)
    {
        ListSet<Integer> set = new ListSet<>();
        set.addAll(Arrays.asList(1, 1, null, 2));
        Set<Integer> expected = new HashSet<>(Arrays.asList(1, null, 2));
        equal(expected, set);
        equal(expected.hashCode(), set.hashCode());
        ListMap<Integer, Integer> map = new ListMap<>();
        Map<Integer, Integer> ref = new LinkedHashMap<>();
        Random random = new Random(17);
        for (int i = 0; i < 300; i++)
        {
            Integer key = i % 7 == 0 ? null : random.nextInt(20);
            Integer value = i % 5 == 0 ? null : i;
            equal(ref.put(key, value), map.put(key, value));
            equal(ref, map);
            equal(ref.hashCode(), map.hashCode());
        }
        map.put(99, 5);
        map.values().remove(5);
        equal(false, map.containsKey(99));
        Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
        Map.Entry<Integer, Integer> entry = it.next();
        entry.setValue(-1);
        equal(-1, map.get(entry.getKey()));
        int size = map.size();
        it.remove();
        equal(size - 1, map.size());
        map.clear();
        equal(0, map.size());
        System.out.println("All " + checks + " checks passed.");
    }
}
