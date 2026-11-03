package tests;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import ds.UniqueLinkedCollection;

public class UniqueLinkedCollectionTest
{
    @SafeVarargs
    private static <T> void check(Collection<T> actual, T... expected)
    {
        assertEquals(expected.length, actual.size());
        assertArrayEquals(expected, actual.toArray());
        assertEquals(expected.length == 0, actual.isEmpty());
    }

    @Test public void emptyCollection()
    {
        UniqueLinkedCollection<String> c = new UniqueLinkedCollection<>();
        check(c);
        assertFalse(c.iterator().hasNext());
        assertFalse(c.contains("A"));
    }

    @Test public void insertionAndDuplicates()
    {
        UniqueLinkedCollection<String> c = new UniqueLinkedCollection<>();
        assertTrue(c.add(new String("A")));
        assertTrue(c.add("B"));
        assertFalse(c.add(new String("A")));
        assertTrue(c.add("C"));
        check(c, "A", "B", "C");
        assertTrue(c.contains(new String("B")));
    }

    @Test public void rejectedNullLeavesCollectionUnchanged()
    {
        UniqueLinkedCollection<String> c = new UniqueLinkedCollection<>(Arrays.asList("A"));
        assertThrows(NullPointerException.class, () -> c.add(null));
        check(c, "A");
    }

    @Test public void conversionKeepsFirstOccurrences()
    {
        String first = new String("A");
        UniqueLinkedCollection<String> c = new UniqueLinkedCollection<>(
                Arrays.asList(first, "B", new String("A"), "C", "B"));
        check(c, "A", "B", "C");
        assertSame(first, c.iterator().next());
    }

    @Test public void conversionFromEmptySource()
    {
        check(new UniqueLinkedCollection<String>(Collections.emptyList()));
    }

    @Test public void conversionAcceptsSubtypeSource()
    {
        UniqueLinkedCollection<Number> c = new UniqueLinkedCollection<>(Arrays.asList(2, 1, 2));
        check(c, 2, 1);
    }

    @Test public void invalidSources()
    {
        assertThrows(NullPointerException.class,
                () -> new UniqueLinkedCollection<String>(null));
        assertThrows(NullPointerException.class,
                () -> new UniqueLinkedCollection<>(Arrays.asList("A", null, "B")));
    }

    @Test public void sourceAndCopyAreIndependent()
    {
        UniqueLinkedCollection<String> source =
                new UniqueLinkedCollection<>(Arrays.asList("A", "B"));
        UniqueLinkedCollection<String> copy = new UniqueLinkedCollection<>(source);
        source.remove("A");
        source.add("C");
        check(copy, "A", "B");
        copy.remove("B");
        check(source, "B", "C");
    }

    @Test public void independentIteratorsAndRepeatedHasNext()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> a = c.iterator(), b = c.iterator();
        assertNotSame(a, b);
        assertTrue(a.hasNext());
        assertTrue(a.hasNext());
        assertEquals(Integer.valueOf(1), a.next());
        assertEquals(Integer.valueOf(2), a.next());
        assertEquals(Integer.valueOf(1), b.next());
        assertEquals(Integer.valueOf(3), a.next());
        assertEquals(Integer.valueOf(2), b.next());
        assertEquals(Integer.valueOf(3), b.next());
        assertFalse(a.hasNext());
        assertFalse(b.hasNext());
    }

    @Test public void exhaustedIterators()
    {
        Iterator<Integer> empty = new UniqueLinkedCollection<Integer>().iterator();
        assertThrows(NoSuchElementException.class, empty::next);
        Iterator<Integer> it = new UniqueLinkedCollection<>(Arrays.asList(1)).iterator();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(NoSuchElementException.class, it::next);
        assertFalse(it.hasNext());
    }

    @Test public void illegalRemoval()
    {
        Iterator<Integer> it = new UniqueLinkedCollection<>(Arrays.asList(1, 2)).iterator();
        assertThrows(IllegalStateException.class, it::remove);
        it.next();
        it.remove();
        assertThrows(IllegalStateException.class, it::remove);
        assertEquals(Integer.valueOf(2), it.next());
    }

    @Test public void removeFirstWithoutSkipping()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2, 3));
        Iterator<Integer> it = c.iterator();
        assertEquals(Integer.valueOf(1), it.next());
        it.remove();
        check(c, 2, 3);
        assertEquals(Integer.valueOf(2), it.next());
        assertEquals(Integer.valueOf(3), it.next());
        assertFalse(it.hasNext());
    }

    @Test public void removeMiddleWithoutSkipping()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> it = c.iterator();
        it.next();
        it.next();
        it.remove();
        check(c, 1, 3, 4);
        assertEquals(Integer.valueOf(3), it.next());
        it.remove();
        check(c, 1, 4);
        assertEquals(Integer.valueOf(4), it.next());
    }

    @Test public void removeLastThenAppend()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2));
        Iterator<Integer> it = c.iterator();
        it.next();
        it.next();
        it.remove();
        assertFalse(it.hasNext());
        assertTrue(c.add(3));
        check(c, 1, 3);
    }

    @Test public void removeOnlyElementThenReuse()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1));
        Iterator<Integer> it = c.iterator();
        it.next();
        it.remove();
        check(c);
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(IllegalStateException.class, it::remove);
        assertTrue(c.add(2));
        check(c, 2);
    }

    @Test public void hasNextDoesNotAffectRemoval()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2));
        Iterator<Integer> it = c.iterator();
        it.next();
        assertTrue(it.hasNext());
        assertTrue(it.hasNext());
        it.remove();
        assertTrue(it.hasNext());
        assertThrows(IllegalStateException.class, it::remove);
        assertEquals(Integer.valueOf(2), it.next());
        assertFalse(it.hasNext());
        it.remove();
        check(c);
    }

    @Test public void failedNextPreservesRemovalPermission()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1));
        Iterator<Integer> it = c.iterator();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
        it.remove();
        check(c);
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(IllegalStateException.class, it::remove);
    }

    @Test public void removeAllThroughIterator()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> it = c.iterator();
        int count = 0;
        while (it.hasNext())
        {
            assertEquals(Integer.valueOf(++count), it.next());
            it.remove();
        }
        assertEquals(4, count);
        check(c);
        c.add(5);
        check(c, 5);
    }

    @Test public void collectionOperations()
    {
        UniqueLinkedCollection<Integer> c = new UniqueLinkedCollection<>();
        assertTrue(c.addAll(Arrays.asList(1, 2, 1, 3, 4)));
        assertFalse(c.addAll(Arrays.asList(1, 2)));
        assertTrue(c.containsAll(Arrays.asList(2, 3)));
        assertTrue(c.remove(Integer.valueOf(2)));
        assertFalse(c.remove(Integer.valueOf(9)));
        assertTrue(c.removeAll(Arrays.asList(1, 4)));
        check(c, 3);
        c.addAll(Arrays.asList(5, 6));
        assertTrue(c.retainAll(Arrays.asList(3, 6)));
        check(c, 3, 6);
        c.clear();
        check(c);
        c.add(7);
        check(c, 7);
    }

    @Test public void typedArray()
    {
        UniqueLinkedCollection<String> c = new UniqueLinkedCollection<>(Arrays.asList("A", "B"));
        assertArrayEquals(new String[] {"A", "B"}, c.toArray(new String[0]));
        String[] destination = {"X", "X", "X", "X"};
        assertSame(destination, c.toArray(destination));
        assertArrayEquals(new String[] {"A", "B", null, "X"}, destination);
    }
}
