package tests;

import ds.AVLTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeSet;

/** Independent content oracle plus structural audits after every update. */
public final class ExampleChecks
{
    private static int checks;

    private ExampleChecks()
    {
    }

    private static void check(boolean condition)
    {
        checks++;
        if (!condition)
            throw new AssertionError("Failed check " + checks);
    }

    private static void rejects(Class<? extends RuntimeException> expected, Runnable action)
    {
        checks++;
        try
        {
            action.run();
        }
        catch (RuntimeException error)
        {
            if (expected.isInstance(error))
                return;
            throw new AssertionError("Unexpected exception", error);
        }
        throw new AssertionError("Expected " + expected.getSimpleName());
    }

    private static <E> void verify(AVLTree<E> tree, TreeSet<E> oracle)
    {
        check(tree.invariantsHold());
        check(tree.size() == oracle.size());
        check(tree.isEmpty() == oracle.isEmpty());
        List<E> contents = new ArrayList<>();
        for (E value : tree)
        {
            contents.add(value);
        }
        check(contents.equals(new ArrayList<>(oracle)));
        if (oracle.isEmpty())
            check(tree.height() == 0);
        else
        {
            check(tree.first().equals(oracle.first()));
            check(tree.last().equals(oracle.last()));
            // A loose bound valid for both families with node-count height.
            check(tree.height() <= 2 * Math.log(tree.size() + 1) / Math.log(2) + 1e-9);
        }
    }

    private static void edgeCases()
    {
        AVLTree<Integer> tree = new AVLTree<>(Comparator.naturalOrder());
        rejects(NullPointerException.class, () -> new AVLTree<Integer>(null));
        rejects(NullPointerException.class, () -> tree.add(null));
        rejects(NullPointerException.class, () -> tree.contains(null));
        rejects(NullPointerException.class, () -> tree.remove(null));
        rejects(NoSuchElementException.class, () -> tree.first());
        rejects(NoSuchElementException.class, () -> tree.last());
        rejects(NoSuchElementException.class, () -> tree.iterator().next());
        check(!tree.remove(99));
        check(tree.add(4));
        Iterator<Integer> unchanged = tree.iterator();
        check(!tree.add(4));
        check(!tree.remove(99));
        check(unchanged.next() == 4);
        check(!unchanged.hasNext());
        rejects(NoSuchElementException.class, () -> unchanged.next());
        rejects(UnsupportedOperationException.class, () -> unchanged.remove());
        Iterator<Integer> changed = tree.iterator();
        tree.add(2);
        rejects(ConcurrentModificationException.class, () -> changed.hasNext());
        rejects(ConcurrentModificationException.class, () -> changed.next());
        Iterator<Integer> removed = tree.iterator();
        tree.remove(2);
        rejects(ConcurrentModificationException.class, () -> removed.next());
        Iterator<Integer> cleared = tree.iterator();
        tree.clear();
        rejects(ConcurrentModificationException.class, () -> cleared.hasNext());
        Iterator<Integer> empty = tree.iterator();
        tree.clear();
        check(!empty.hasNext());
        check(tree.invariantsHold());

        // Comparator<? super Integer> accepts a comparator of Number.
        Comparator<Number> numeric = Comparator.comparingDouble(Number::doubleValue);
        AVLTree<Integer> wider = new AVLTree<>(numeric);
        wider.add(Integer.MIN_VALUE);
        wider.add(Integer.MAX_VALUE);
        check(wider.first() == Integer.MIN_VALUE && wider.last() == Integer.MAX_VALUE);
        check(wider.invariantsHold());

        // Equal comparison keys need not be equal according to String.equals.
        AVLTree<String> words = new AVLTree<>(String.CASE_INSENSITIVE_ORDER);
        check(words.add("Oak"));
        check(!words.add("OAK"));
        check(words.first().equals("Oak"));
        check(words.contains("oak"));
        check(words.remove("oAk"));
        check(words.isEmpty() && words.invariantsHold());
    }

    private static void orderedUpdates(boolean descending, boolean removeMinimum)
    {
        AVLTree<Integer> tree = new AVLTree<>(Comparator.naturalOrder());
        TreeSet<Integer> oracle = new TreeSet<>();
        for (int i = 0; i < 256; i++)
        {
            int value = descending ? 255 - i : i;
            check(tree.add(value) == oracle.add(value));
            verify(tree, oracle);
        }
        while (!oracle.isEmpty())
        {
            int value = removeMinimum ? oracle.first() : oracle.last();
            check(tree.remove(value) == oracle.remove(value));
            verify(tree, oracle);
        }
    }

    private static void randomUpdates(Comparator<Integer> order, long seed)
    {
        AVLTree<Integer> tree = new AVLTree<>(order);
        TreeSet<Integer> oracle = new TreeSet<>(order);
        Random random = new Random(seed);
        for (int i = 0; i < 6000; i++)
        {
            int value = random.nextInt(400) - 200;
            if (random.nextBoolean())
                check(tree.add(value) == oracle.add(value));
            else
                check(tree.remove(value) == oracle.remove(value));
            check(tree.contains(value) == oracle.contains(value));
            verify(tree, oracle);
        }
        List<Integer> remaining = new ArrayList<>(oracle);
        Collections.shuffle(remaining, random);
        for (int value : remaining)
        {
            check(tree.remove(value) == oracle.remove(value));
            verify(tree, oracle);
        }
    }

    // Every insertion order for six distinct keys; delete in that same order
    // and also in reverse. This covers small shapes that random tests may miss.
    private static void permutations(int[] values, int position)
    {
        if (position == values.length)
        {
            for (boolean reverse : new boolean[] {false, true})
            {
                AVLTree<Integer> tree = new AVLTree<>(Comparator.naturalOrder());
                TreeSet<Integer> oracle = new TreeSet<>();
                for (int value : values)
                {
                    check(tree.add(value) == oracle.add(value));
                    verify(tree, oracle);
                }
                for (int i = 0; i < values.length; i++)
                {
                    int value = values[reverse ? values.length - 1 - i : i];
                    check(tree.remove(value) == oracle.remove(value));
                    verify(tree, oracle);
                }
            }
            return;
        }
        for (int i = position; i < values.length; i++)
        {
            int old = values[position];
            values[position] = values[i];
            values[i] = old;
            permutations(values, position + 1);
            old = values[position];
            values[position] = values[i];
            values[i] = old;
        }
    }

    public static void main(String[] args)
    {
        edgeCases();
        for (boolean descending : new boolean[] {false, true})
        {
            for (boolean minimum : new boolean[] {false, true})
            {
                orderedUpdates(descending, minimum);
            }
        }
        randomUpdates(Comparator.naturalOrder(), 202627);
        randomUpdates(Comparator.reverseOrder(), 1703);
        permutations(new int[] {1, 2, 3, 4, 5, 6}, 0);
        System.out.println("All " + checks + " checks passed.");
    }
}
