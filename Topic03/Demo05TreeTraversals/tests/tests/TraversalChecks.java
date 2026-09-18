package tests;

import ds.BinarySearchTree;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/** Recursive reference traversals verify the independent iterative algorithms. */
final class TraversalChecks
{
    private static int checks;

    private TraversalChecks()
    {
    }

    private static final class ReferenceNode
    {
        int value;
        ReferenceNode left;
        ReferenceNode right;

        ReferenceNode(int value)
        {
            this.value = value;
        }
    }

    private static ReferenceNode insert(ReferenceNode node, int value)
    {
        if (node == null)
            return new ReferenceNode(value);
        if (value <= node.value)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        return node;
    }

    private static void reference(ReferenceNode node, int order, List<Integer> result)
    {
        if (node == null)
            return;
        if (order == 0)
            result.add(node.value);
        reference(node.left, order, result);
        if (order == 1)
            result.add(node.value);
        reference(node.right, order, result);
        if (order == 2)
            result.add(node.value);
    }

    private static void atDepth(ReferenceNode node, int depth, List<Integer> result)
    {
        if (node == null)
            return;
        if (depth == 0)
            result.add(node.value);
        else
        {
            atDepth(node.left, depth - 1, result);
            atDepth(node.right, depth - 1, result);
        }
    }

    private static void check(boolean condition)
    {
        checks++;
        if (!condition)
            throw new AssertionError("Traversal check " + checks);
    }

    private static Iterator<Integer> iterator(BinarySearchTree<Integer> tree, int order)
    {
        if (order == 0)
            return tree.preOrderIterator();
        if (order == 1)
            return tree.inOrderIterator();
        if (order == 2)
            return tree.postOrderIterator();
        return tree.levelOrderIterator();
    }

    private static void verify(int[] values)
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        ReferenceNode root = null;
        for (int value : values)
        {
            tree.add(value);
            root = insert(root, value);
        }
        for (int order = 0; order < 4; order++)
        {
            List<Integer> expected = new ArrayList<>();
            if (order < 3)
                reference(root, order, expected);
            else
            {
                // Independent breadth-first oracle: collect one depth at a time.
                for (int depth = 0; depth < values.length; depth++)
                {
                    atDepth(root, depth, expected);
                }
            }
            Iterator<Integer> first = iterator(tree, order);
            Iterator<Integer> second = iterator(tree, order);
            for (int value : expected)
            {
                check(first.hasNext());
                check(first.hasNext());
                check(first.next() == value);
                // A separate iterator retains its own cursor, even when interleaved.
                check(second.next() == value);
            }
            check(!first.hasNext() && !second.hasNext());
            for (int repetition = 0; repetition < 2; repetition++)
            {
                boolean failed = false;
                try
                {
                    first.next();
                }
                catch (NoSuchElementException expectedError)
                {
                    failed = true;
                }
                check(failed);
            }
            boolean unsupported = false;
            try
            {
                second.remove();
            }
            catch (UnsupportedOperationException expectedError)
            {
                unsupported = true;
            }
            check(unsupported);
        }
        check(tree.size() == values.length);
    }

    static int run()
    {
        verify(new int[] {});
        verify(new int[] {8});
        verify(new int[] {8, 3, 10, 1, 6, 14, 4, 7, 13});
        verify(new int[] {1, 2, 3, 4, 5});
        verify(new int[] {5, 4, 3, 2, 1});
        verify(new int[] {3, 3, 3, 2, 4, 4});
        Random random = new Random(202603);
        for (int trial = 0; trial < 250; trial++)
        {
            int[] values = new int[random.nextInt(70)];
            for (int i = 0; i < values.length; i++)
            {
                values[i] = random.nextInt(30);
            }
            verify(values);
        }
        return checks;
    }
}
