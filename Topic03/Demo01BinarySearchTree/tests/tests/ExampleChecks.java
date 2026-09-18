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
        equal(false, tree.add(2));
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
        equal(false, lengths.add("dog"));
        equal(true, lengths.contains("fox"));
        Random random = new Random(17);
        for (Comparator<Integer> order : Arrays.<Comparator<Integer>>asList(null, Comparator.reverseOrder()))
        {
            BinarySearchTree<Integer> actual = new BinarySearchTree<>(order);
            TreeSet<Integer> expected = new TreeSet<>(order);
            for (int i = 0; i < 10000; i++)
            {
                int value = random.nextInt(100);
                int operation = random.nextInt(3);
                if (operation == 0)
                    equal(expected.add(value), actual.add(value));
                else if (operation == 1)
                    equal(expected.remove(value), actual.remove(value));
                else
                    equal(expected.contains(value), actual.contains(value));
                equal(expected.size(), actual.size());
                equal(new ArrayList<>(expected), new ArrayList<>(actual));
            }
            actual.clear();
            equal(0, actual.size());
            equal(false, actual.iterator().hasNext());
        }
        System.out.println("All " + checks + " checks passed.");
    }
}
