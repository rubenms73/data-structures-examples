package tests;

import ds.*;
import java.util.*;

public final class ExampleChecks
{
    private static int checks;

    public static void main(String[] args)
    {
        MutableBag<Integer> bag = new MutableBag<>(Arrays.asList(1, 2, 3, 4));
        Iterator<Integer> iterator = bag.iterator();
        throwsType(IllegalStateException.class, iterator::remove);
        equal(1, iterator.next());
        iterator.remove();
        equal(2, iterator.next());
        iterator.remove();
        throwsType(IllegalStateException.class, iterator::remove);
        equal(3, iterator.next());
        equal(4, iterator.next());
        iterator.remove();
        equal(false, iterator.hasNext());
        throwsType(NoSuchElementException.class, iterator::next);
        equal("[3]", bag.toString());
        bag.clear();
        equal(0, bag.size());
        bag.addAll(Arrays.asList(1, 1, 1));
        bag.removeAll(Arrays.asList(1));
        equal(0, bag.size());
        bag.addAll(Arrays.asList(1, 2, 2, 3));
        bag.retainAll(Arrays.asList(2));
        equal("[2, 2]", bag.toString());
        bag.removeIf(x -> x == 2);
        equal(0, bag.size());
        for (int i = 0; i < 100; i++)
        {
            bag.add(i);
        }
        equal(100, bag.size());
        int expected = 0;
        for (int item : bag)
        {
            equal(expected++, item);
        }
        iterator = bag.iterator();
        bag.add(100);
        throwsType(ConcurrentModificationException.class, iterator::next);
        throwsType(NullPointerException.class, () -> bag.add(null));
        throwsType(IllegalArgumentException.class, () -> new MutableBag<Integer>(-1));

        Integer[] source = {2, -3, 2, 18};
        ImmutableBag<Integer> immutable = new ImmutableBag<>(source);
        source[0] = 99;
        equal("[2, -3, 2, 18]", immutable.toString());
        MutableBag<Integer> original = new MutableBag<>(immutable);
        ImmutableBag<Integer> snapshot = new ImmutableBag<>(original);
        original.clear();
        equal(immutable, snapshot);
        equal(5, immutable.withAdded(7).size());
        equal(4, immutable.size());
        equal(1, immutable.withoutOne(2).occurrences(2));
        equal(2, immutable.occurrences(2));
        equal(immutable, immutable.withoutOne(99));
        throwsType(UnsupportedOperationException.class, () -> immutable.add(1));
        throwsType(UnsupportedOperationException.class, () -> immutable.addAll(List.of()));
        throwsType(UnsupportedOperationException.class, () -> immutable.remove(99));
        throwsType(UnsupportedOperationException.class, () -> immutable.removeAll(List.of()));
        throwsType(UnsupportedOperationException.class, () -> immutable.retainAll(List.of()));
        throwsType(UnsupportedOperationException.class, () -> immutable.removeIf(x -> false));
        throwsType(UnsupportedOperationException.class, immutable::clear);
        Iterator<Integer> fixedIterator = immutable.iterator();
        fixedIterator.next();
        throwsType(UnsupportedOperationException.class, fixedIterator::remove);
        throwsType(NullPointerException.class, () -> new ImmutableBag<Integer>(1, null));
        equal(0, new ImmutableBag<Integer>().size());
        throwsType(NoSuchElementException.class, new ImmutableBag<Integer>().iterator()::next);

        SortedMutableBag<Integer> sorted = new SortedMutableBag<>(immutable);
        equal("[-3, 2, 2, 18]", sorted.toString());
        SortedMutableBag<Integer> reverse = new SortedMutableBag<>(immutable, Comparator.reverseOrder());
        equal("[18, 2, 2, -3]", reverse.toString());
        equal(true, immutable.equals(sorted));
        equal(true, sorted.equals(immutable));
        equal(true, sorted.equals(reverse));
        equal(immutable.hashCode(), sorted.hashCode());
        equal(immutable.hashCode(), reverse.hashCode());
        equal(false, immutable.equals(new ImmutableBag<>(2, -3, 18, 18)));
        equal(false, immutable.equals(Arrays.asList(2, -3, 2, 18)));
        ImmutableBag<Collection<Integer>> nested = new ImmutableBag<>(
                new ImmutableBag<>(2, -3, 2, 18), new ImmutableBag<>(18, 2, 2, -3),
                new ImmutableBag<>(18, 2, -3));
        equal(2, nested.occurrences(immutable));

        SortedMutableBag<Object> invalid = new SortedMutableBag<>();
        throwsType(ClassCastException.class, () -> invalid.add(new Object()));
        equal(0, invalid.size());
        SortedMutableBag<Integer> throwing = new SortedMutableBag<>((a, b) ->
        {
            if (a == 9 && b == 1)
                throw new IllegalArgumentException("Comparison rejected");
            return Integer.compare(a, b);
        });
        throwing.add(1);
        Iterator<Integer> stable = throwing.iterator();
        throwsType(IllegalArgumentException.class, () -> throwing.add(9));
        equal("[1]", throwing.toString());
        equal(1, stable.next());
        sorted.remove(2);
        equal("[-3, 2, 18]", sorted.toString());
        sorted.clear();
        equal(0, sorted.size());

        // Check insertion order after repeated growth and duplicate insertions.
        Random random = new Random(17);
        List<Integer> reference = new ArrayList<>();
        for (int i = 0; i < 200; i++)
        {
            int item = random.nextInt(20);
            sorted.add(item);
            reference.add(item);
        }
        reference.sort(Comparator.naturalOrder());
        equal(reference, new ArrayList<>(sorted));
        Iterator<Integer> a = sorted.iterator();
        Iterator<Integer> b = sorted.iterator();
        a.next();
        a.remove();
        throwsType(ConcurrentModificationException.class, b::next);
        System.out.println("All " + checks + " checks passed.");
    }

    private static void equal(Object expected, Object actual)
    {
        checks++;
        if (!Objects.equals(expected, actual))
            throw new AssertionError("Expected " + expected + ", got " + actual);
    }

    private static void throwsType(Class<? extends Throwable> expected, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (Throwable actual)
        {
            if (expected.isInstance(actual))
                return;
            throw new AssertionError("Unexpected exception", actual);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }
}
