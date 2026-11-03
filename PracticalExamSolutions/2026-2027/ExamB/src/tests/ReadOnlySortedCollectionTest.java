package tests;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import ds.ReadOnlySortedCollection;

public class ReadOnlySortedCollectionTest
{
    @SafeVarargs
    private static <T> void check(Collection<T> actual, T... expected)
    {
        assertEquals(expected.length, actual.size());
        assertArrayEquals(expected, actual.toArray());
        assertEquals(expected.length == 0, actual.isEmpty());
    }

    private static final class Item
    {
        final int priority;
        final String name;
        Item(int priority, String name)
        {
            this.priority = priority;
            this.name = name;
        }
        @Override public String toString() { return name; }
    }

    private static final class Word implements Comparable<Word>
    {
        final String text;
        Word(String text) { this.text = text; }
        @Override public int compareTo(Word other) { return text.compareTo(other.text); }
    }

    @Test public void emptyConstructors()
    {
        check(new ReadOnlySortedCollection<Integer>());
        check(new ReadOnlySortedCollection<Integer>(Comparator.reverseOrder()));
        check(new ReadOnlySortedCollection<Integer>((Comparator<Integer>) null));
        check(new ReadOnlySortedCollection<Integer>(Collections.emptyList()));
        check(new ReadOnlySortedCollection<Integer>(Collections.emptyList(),
                Comparator.reverseOrder()));
    }

    @Test public void naturalOrderAndDuplicates()
    {
        ReadOnlySortedCollection<Integer> c =
                new ReadOnlySortedCollection<>(Arrays.asList(5, 2, 5, 3, 2));
        check(c, 2, 2, 3, 5, 5);
        assertTrue(c.contains(3));
        assertFalse(c.contains(4));
        assertTrue(c.containsAll(Arrays.asList(2, 5)));
    }

    @Test public void nullComparatorUsesNaturalOrder()
    {
        check(new ReadOnlySortedCollection<>(Arrays.asList(3, 1, 2), null), 1, 2, 3);
    }

    @Test public void reverseOrder()
    {
        check(new ReadOnlySortedCollection<>(Arrays.asList(2, 5, 1, 5), Comparator.reverseOrder()),
                5, 5, 2, 1);
    }

    @Test public void acceptsSubtypeSourceAndSupertypeComparator()
    {
        Comparator<Number> comparator = (a, b) -> Double.compare(a.doubleValue(), b.doubleValue());
        ReadOnlySortedCollection<Number> c =
                new ReadOnlySortedCollection<>(Arrays.asList(3, 1, 2), comparator);
        check(c, 1, 2, 3);
    }

    @Test public void comparatorCanOrderNonComparableElements()
    {
        Item a = new Item(3, "A"), b = new Item(1, "B"), c = new Item(2, "C");
        check(new ReadOnlySortedCollection<>(Arrays.asList(a, b, c),
                (x, y) -> Integer.compare(x.priority, y.priority)), b, c, a);
    }

    @Test public void comparatorOverridesNaturalOrder()
    {
        check(new ReadOnlySortedCollection<>(Arrays.asList("a", "zzz", "bb"),
                (a, b) -> Integer.compare(b.length(), a.length())), "zzz", "bb", "a");
    }

    @Test public void stableComparatorOrder()
    {
        Item a = new Item(2, "A"), b = new Item(1, "B"), c = new Item(2, "C"), d = new Item(1, "D");
        ReadOnlySortedCollection<Item> values = new ReadOnlySortedCollection<>(
                Arrays.asList(a, b, c, d, a), (x, y) -> Integer.compare(x.priority, y.priority));
        Iterator<Item> it = values.iterator();
        for (Item expected : Arrays.asList(b, d, a, c, a))
            assertSame(expected, it.next());
        assertFalse(it.hasNext());
        assertEquals(5, values.size());
    }

    @Test public void stableNaturalOrder()
    {
        Word a = new Word("B"), b = new Word("A"), c = new Word("B");
        Iterator<Word> it = new ReadOnlySortedCollection<>(Arrays.asList(a, b, c)).iterator();
        assertSame(b, it.next());
        assertSame(a, it.next());
        assertSame(c, it.next());
    }

    @Test public void allEqualElementsRetainOrder()
    {
        Item a = new Item(1, "A"), b = new Item(1, "B"), c = new Item(1, "C");
        check(new ReadOnlySortedCollection<>(Arrays.asList(a, b, c), (x, y) -> 0), a, b, c);
    }

    @Test public void singletonAndAlreadyOrderedSources()
    {
        check(new ReadOnlySortedCollection<>(Arrays.asList(3)), 3);
        check(new ReadOnlySortedCollection<>(Arrays.asList(1, 2, 3)), 1, 2, 3);
        check(new ReadOnlySortedCollection<>(Arrays.asList(3, 2, 1)), 1, 2, 3);
    }

    @Test public void sourceStructureIsIndependent()
    {
        List<Integer> source = new ArrayList<>(Arrays.asList(3, 1, 2));
        ReadOnlySortedCollection<Integer> c = new ReadOnlySortedCollection<>(source);
        assertEquals(Arrays.asList(3, 1, 2), source);
        source.clear();
        source.add(9);
        check(c, 1, 2, 3);
    }

    @Test public void elementObjectsArePreserved()
    {
        Item a = new Item(2, "A"), b = new Item(1, "B");
        Iterator<Item> it = new ReadOnlySortedCollection<>(Arrays.asList(a, b),
                (x, y) -> Integer.compare(x.priority, y.priority)).iterator();
        assertSame(b, it.next());
        assertSame(a, it.next());
    }

    @Test public void conversionFromReadOnlyNaturalSource()
    {
        ReadOnlySortedCollection<Integer> source =
                new ReadOnlySortedCollection<>(Arrays.asList(3, 1, 2));
        ReadOnlySortedCollection<Number> copy = new ReadOnlySortedCollection<>(source);
        check(copy, 1, 2, 3);
        assertThrows(UnsupportedOperationException.class, () -> source.remove(2));
        check(copy, 1, 2, 3);
        check(new ReadOnlySortedCollection<>(source, null), 1, 2, 3);
    }

    @Test public void conversionFromReadOnlyComparatorSource()
    {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        ReadOnlySortedCollection<Integer> source =
                new ReadOnlySortedCollection<>(Arrays.asList(1, 3, 2), reverse);
        check(new ReadOnlySortedCollection<>(source, reverse), 3, 2, 1);
    }

    @Test public void conversionCanChangeOrdering()
    {
        Comparator<Integer> reverse = Comparator.reverseOrder();
        ReadOnlySortedCollection<Integer> source =
                new ReadOnlySortedCollection<>(Arrays.asList(1, 3, 2), reverse);
        check(new ReadOnlySortedCollection<>(source), 1, 2, 3);
        check(new ReadOnlySortedCollection<>(source, Comparator.naturalOrder()), 1, 2, 3);
        check(source, 3, 2, 1);
    }

    @Test public void nullSources()
    {
        assertThrows(NullPointerException.class,
                () -> new ReadOnlySortedCollection<Integer>((Collection<Integer>) null));
        assertThrows(NullPointerException.class,
                () -> new ReadOnlySortedCollection<Integer>(null, Comparator.reverseOrder()));
    }

    @Test public void nullElements()
    {
        assertThrows(NullPointerException.class,
                () -> new ReadOnlySortedCollection<Integer>(Arrays.asList((Integer) null)));
        assertThrows(NullPointerException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(1, null, 2)));
        assertThrows(NullPointerException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(1,
                        null), Comparator.nullsFirst(Comparator.naturalOrder())));
    }

    @Test public void naturalOrderRejectsFirstNonComparableElement()
    {
        assertThrows(ClassCastException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(new Object())));
        assertThrows(ClassCastException.class,
                () -> new ReadOnlySortedCollection<>(Arrays.asList(new Object()), null));
    }

    @Test public void incompatibleNaturalOrderElements()
    {
        assertThrows(ClassCastException.class,
                () -> new ReadOnlySortedCollection<Object>(Arrays.asList("A", 1)));
        assertThrows(ClassCastException.class,
                () -> new ReadOnlySortedCollection<Object>(Arrays.asList(1, "A")));
    }

    @Test public void independentIteratorsAndHasNext()
    {
        ReadOnlySortedCollection<Integer> c =
                new ReadOnlySortedCollection<>(Arrays.asList(3, 1, 2));
        Iterator<Integer> a = c.iterator(), b = c.iterator();
        assertNotSame(a, b);
        assertTrue(a.hasNext());
        assertTrue(a.hasNext());
        assertEquals(Integer.valueOf(1), a.next());
        assertEquals(Integer.valueOf(2), a.next());
        assertEquals(Integer.valueOf(1), b.next());
        assertEquals(Integer.valueOf(3), a.next());
        assertFalse(a.hasNext());
        assertEquals(Integer.valueOf(2), b.next());
        assertEquals(Integer.valueOf(3), b.next());
        assertFalse(b.hasNext());
    }

    @Test public void exhaustedIterators()
    {
        Iterator<Integer> empty = new ReadOnlySortedCollection<Integer>().iterator();
        assertThrows(NoSuchElementException.class, empty::next);
        Iterator<Integer> it = new ReadOnlySortedCollection<>(Arrays.asList(1)).iterator();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
        assertThrows(NoSuchElementException.class, it::next);
        assertFalse(it.hasNext());
    }

    @Test public void iteratorRemovalIsUnsupported()
    {
        ReadOnlySortedCollection<Integer> c = new ReadOnlySortedCollection<>(Arrays.asList(2, 1));
        Iterator<Integer> it = c.iterator();
        assertThrows(UnsupportedOperationException.class, it::remove);
        assertEquals(Integer.valueOf(1), it.next());
        assertThrows(UnsupportedOperationException.class, it::remove);
        assertEquals(Integer.valueOf(2), it.next());
        assertThrows(UnsupportedOperationException.class, it::remove);
        check(c, 1, 2);
    }

    @Test public void modifyingOperationsAreUnsupported()
    {
        ReadOnlySortedCollection<Integer> c =
                new ReadOnlySortedCollection<>(Arrays.asList(2, 1, 3));
        assertThrows(UnsupportedOperationException.class, () -> c.add(4));
        assertThrows(UnsupportedOperationException.class, () -> c.addAll(Arrays.asList(4, 5)));
        assertThrows(UnsupportedOperationException.class, () -> c.remove(Integer.valueOf(2)));
        assertThrows(UnsupportedOperationException.class, () -> c.removeAll(Arrays.asList(2)));
        assertThrows(UnsupportedOperationException.class, () -> c.retainAll(Arrays.asList(2)));
        assertThrows(UnsupportedOperationException.class, () -> c.removeIf(x -> x == 2));
        assertThrows(UnsupportedOperationException.class, c::clear);
        check(c, 1, 2, 3);
    }

    @Test public void typedArray()
    {
        ReadOnlySortedCollection<String> c =
                new ReadOnlySortedCollection<>(Arrays.asList("B", "A"));
        assertArrayEquals(new String[] {"A", "B"}, c.toArray(new String[0]));
        String[] destination = {"X", "X", "X", "X"};
        assertSame(destination, c.toArray(destination));
        assertArrayEquals(new String[] {"A", "B", null, "X"}, destination);
    }
}
