package app;

import ds.AVLTree;
import java.util.Comparator;

/** Observe balance and search cost; the repair algorithms are optional study. */
public final class Main
{
    private Main()
    {
    }

    private static final class CountingOrder implements Comparator<Integer>
    {
        private int comparisons;

        @Override
        public int compare(Integer first, Integer second)
        {
            comparisons++;
            return Integer.compare(first, second);
        }
    }

    public static void main(String[] args)
    {
        CountingOrder order = new CountingOrder();
        AVLTree<Integer> tree = new AVLTree<>(order);
        for (int value = 1; value <= 15; value++)
        {
            tree.add(value);
        }
        System.out.println("Inserted 1 through 15 in ascending order.");
        System.out.println("Tree structure (right above, left below):");
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        System.out.println("Ordered contents: " + tree);
        System.out.println("Balanced tree height (nodes): " + tree.height());
        System.out.println("A plain BST with this insertion order would have height 15.");
        order.comparisons = 0;
        boolean found = tree.contains(15);
        System.out.println("Search for 15: " + found + "; comparisons: " + order.comparisons);
        System.out.println("Adding duplicate 8: " + tree.add(8));
        System.out.println();
        System.out.println("============================================================");
        System.out.println("Removing 8: " + tree.remove(8));
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        System.out.println();
        System.out.println("============================================================");
        System.out.println("Removing 1: " + tree.remove(1));
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        System.out.println();
        System.out.println("============================================================");
        System.out.println("Removing 15: " + tree.remove(15));
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        System.out.println("After removals: " + tree);
        System.out.println("Height after removals: " + tree.height());
        System.out.println("First / last: " + tree.first() + " / " + tree.last());
        System.out.println("All invariants hold: " + tree.invariantsHold());

        tree.clear();
        System.out.println("After clear: size=" + tree.size() + ", height=" + tree.height());
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        System.out.println("Focus: purpose and logarithmic costs. Repair code is not required for the exam.");
    }
}
