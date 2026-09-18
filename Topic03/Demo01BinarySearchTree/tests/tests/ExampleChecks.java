package tests;

import ds.BinarySearchTree;
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

    private static void checkInvariant(BinarySearchTree<Integer> tree,
                                       Comparator<Integer> order)
    {
        try
        {
            java.lang.reflect.Field root = BinarySearchTree.class.getDeclaredField("root");
            root.setAccessible(true);
            equal(true, valid(root.get(tree), null, null, order));
        }
        catch (ReflectiveOperationException exception)
        {
            throw new AssertionError(exception);
        }
    }

    private static boolean valid(Object node, Integer lower, Integer upper,
                                 Comparator<Integer> order)
        throws ReflectiveOperationException
    {
        if (node == null)
            return true;
        Class<?> type = node.getClass();
        java.lang.reflect.Field value = type.getDeclaredField("value");
        java.lang.reflect.Field left = type.getDeclaredField("left");
        java.lang.reflect.Field right = type.getDeclaredField("right");
        value.setAccessible(true);
        left.setAccessible(true);
        right.setAccessible(true);
        int item = (Integer) value.get(node);
        Comparator<Integer> comparison = order == null ? Comparator.naturalOrder() : order;
        if (lower != null && comparison.compare(item, lower) <= 0)
            return false;
        if (upper != null && comparison.compare(item, upper) > 0)
            return false;
        return valid(left.get(node), lower, item, order)
            && valid(right.get(node), item, upper, order);
    }

    public static void main(String[] args)
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        rejects(NoSuchElementException.class, () -> tree.iterator().next());
        rejects(NullPointerException.class, () -> tree.add(null));
        rejects(NullPointerException.class, () -> tree.contains(null));
        rejects(NullPointerException.class, () -> tree.remove(null));
        rejects(NullPointerException.class,
            () -> new BinarySearchTree<Integer>((Collection<Integer>) null));
        BinarySearchTree<Object> invalid = new BinarySearchTree<>();
        rejects(ClassCastException.class, () -> invalid.add(new Object()));
        equal(0, invalid.size());
        equal(true, tree.add(2));
        equal(1, tree.size());
        equal(true, tree.add(2));
        equal(2, tree.size());
        equal(true, tree.remove(2));
        equal(true, tree.contains(2));
        equal(1, tree.size());
        equal(true, tree.remove(2));
        equal(0, tree.size());
        for (int value : new int[] {2, 1, 3, 4})
        {
            tree.add(value);
        }
        equal(true, tree.remove(2));
        equal(Arrays.asList(1, 3, 4), new ArrayList<>(tree));
        tree.clear();
        tree.add(2);
        tree.add(1);
        tree.remove(2);
        equal(Arrays.asList(1), new ArrayList<>(tree));
        tree.clear();
        tree.add(2);
        tree.add(3);
        tree.remove(2);
        equal(Arrays.asList(3), new ArrayList<>(tree));
        Iterator<Integer> it = tree.iterator();
        equal(3, it.next());
        rejects(NoSuchElementException.class, it::next);
        rejects(UnsupportedOperationException.class, it::remove);
        BinarySearchTree<Integer> copy = new BinarySearchTree<>(tree);
        copy.clear();
        equal(1, tree.size());
        Comparator<Number> numberOrder = (a, b) -> Double.compare(a.doubleValue(), b.doubleValue());
        BinarySearchTree<Integer> numbers = new BinarySearchTree<>(tree, numberOrder);
        equal(Arrays.asList(3), new ArrayList<>(numbers));
        BinarySearchTree<String> lengths = new BinarySearchTree<>((a, b) -> Integer.compare(a.length(), b.length()));
        equal(true, lengths.add("cat"));
        equal(true, lengths.add("dog"));
        equal(Arrays.asList("dog", "cat"), new ArrayList<>(lengths));
        equal(true, lengths.remove("fox"));
        equal(Arrays.asList("dog"), new ArrayList<>(lengths));
        equal(true, lengths.contains("fox"));
        // A successor replacement would leave another 9 on the right of 9.
        BinarySearchTree<Integer> repeated = new BinarySearchTree<>(
            Arrays.asList(8, 3, 10, 9, 9, 3, 2, 7, 7, 6));
        equal(true, repeated.remove(8));
        equal(Arrays.asList(2, 3, 3, 6, 7, 7, 9, 9, 10), new ArrayList<>(repeated));
        checkInvariant(repeated, null);
        BinarySearchTree<Integer> occurrences = new BinarySearchTree<>(repeated);
        equal(new ArrayList<>(repeated), new ArrayList<>(occurrences));
        while (!repeated.isEmpty())
        {
            int value = repeated.iterator().next();
            int before = repeated.size();
            equal(true, repeated.remove(value));
            equal(before - 1, repeated.size());
            checkInvariant(repeated, null);
        }
        equal(9, occurrences.size());
        Random random = new Random(17);
        for (Comparator<Integer> order : Arrays.<Comparator<Integer>>asList(null, Comparator.reverseOrder()))
        {
            BinarySearchTree<Integer> actual = new BinarySearchTree<>(order);
            List<Integer> expected = new ArrayList<>();
            for (int i = 0; i < 10000; i++)
            {
                int value = random.nextInt(100);
                int operation = random.nextInt(3);
                if (operation == 0)
                {
                    equal(expected.add(value), actual.add(value));
                    expected.sort(order);
                }
                else if (operation == 1)
                    equal(expected.remove(Integer.valueOf(value)), actual.remove(value));
                else
                    equal(expected.contains(value), actual.contains(value));
                equal(expected.size(), actual.size());
                equal(expected, new ArrayList<>(actual));
                checkInvariant(actual, order);

            }
            actual.clear();
            equal(0, actual.size());
            equal(false, actual.iterator().hasNext());
        }
        System.out.println("All " + checks + " checks passed.");
    }
}
