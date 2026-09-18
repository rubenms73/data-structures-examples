package app;

import ds.BinarySearchTree;
import java.util.Comparator;

public final class Main
{
    private static BinarySearchTree<Integer> example()
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[] {8, 3, 10, 1, 6, 14, 4, 7, 13})
        {
            tree.add(value);
        }
        return tree;
    }

    private static void showRemoval(String description, int value)
    {
        BinarySearchTree<Integer> tree = example();
        System.out.println(description + ": " + tree.remove(value) + " -> " + tree);
    }

    public static void main(String[] args)
    {
        BinarySearchTree<Integer> tree = example();
        System.out.println("In order: " + tree);
        System.out.println("Size: " + tree.size());
        System.out.println("Contains 6: " + tree.contains(6));
        System.out.println("Add duplicate 6: " + tree.add(6));
        System.out.println("With the duplicate: " + tree + ", size: " + tree.size());
        System.out.println("Remove one 6: " + tree.remove(6));
        System.out.println("One 6 remains: " + tree.contains(6));
        showRemoval("Remove leaf 1", 1);
        showRemoval("Remove node 14 with one child", 14);
        showRemoval("Remove node 3 with two children", 3);
        showRemoval("Remove root 8", 8);
        showRemoval("Remove absent 99", 99);

        Comparator<Integer> descending = (first, second) -> second.compareTo(first);
        BinarySearchTree<Integer> reversed = new BinarySearchTree<>(tree, descending);
        System.out.println("Reverse order: " + reversed);
        reversed.remove(8);
        System.out.println("Original still contains 8: " + tree.contains(8));

        // Ordered insertion creates a chain of right children, not a balanced tree.
        BinarySearchTree<Integer> chain = new BinarySearchTree<>();
        for (int value = 1; value <= 5; value++)
        {
            chain.add(value);
        }
        System.out.println("Ordered insertion: " + chain);
        tree.clear();
        System.out.println("After clear: " + tree + ", size: " + tree.size());
    }
}
