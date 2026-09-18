package app;

import ds.BinarySearchTree;
import java.util.Iterator;

/** Compare four incremental iterators over exactly the same tree. */
public final class Main
{
    private Main()
    {
    }

    private static void show(String title, Iterator<Integer> iterator)
    {
        System.out.print(title + ": ");
        boolean first = true;
        while (iterator.hasNext())
        {
            if (!first)
                System.out.print(" -> ");
            System.out.print(iterator.next());
            first = false;
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        for (int value : new int[] {8, 3, 10, 1, 6, 14, 4, 7, 13})
        {
            tree.add(value);
        }
        System.out.println("Same tree, four traversal orders (right subtree above):");
        System.out.println();
        System.out.print(tree.toTreeString());
        System.out.println();
        show("Preorder (root, left, right)", tree.preOrderIterator());
        show("Inorder (left, root, right)", tree.inOrderIterator());
        show("Postorder (left, right, root)", tree.postOrderIterator());
        show("Level order (breadth first)", tree.levelOrderIterator());
        System.out.println("The default iterator is inorder: " + tree);
        System.out.println();
        Iterator<Integer> preorder = tree.preOrderIterator();
        Iterator<Integer> levels = tree.levelOrderIterator();
        System.out.println("Independent iterators, advanced alternately:");
        for (int i = 0; i < 3; i++)
        {
            System.out.println("preorder=" + preorder.next() + ", level order=" + levels.next());
        }
        System.out.println("Iterator creation does not materialise the complete traversal.");
    }
}
