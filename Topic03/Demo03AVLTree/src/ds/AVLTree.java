package ds;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A generic balanced search tree containing one element per comparison key.
 * Null elements and a null comparator are rejected. Elements are not copied;
 * fields used by the comparator must not change while an element is stored.
 * This is a teaching container, not an implementation of java.util.Set.
 * Iteration follows comparator order and does not support iterator removal.
 */
public final class AVLTree<E> implements Iterable<E>
{
    /** Nodes and links are private: clients cannot break the tree invariants. */
    private final class Node
    {
        E value;
        Node left;
        Node right;
        int height = 1;

        Node(E value)
        {
            this.value = value;
        }
    }

    private final Comparator<? super E> order;
    private Node root;
    private int size;
    private int modifications;

    /** Creates an empty tree using the supplied total ordering. */
    public AVLTree(Comparator<? super E> order)
    {
        if (order == null)
            throw new NullPointerException("Comparator must not be null");
        this.order = order;
    }

    /** Number of distinct comparison keys, in O(1) time. */
    public int size()
    {
        return size;
    }

    /** Whether the tree contains no elements. */
    public boolean isEmpty()
    {
        return root == null;
    }

    /**
     * Searches in O(log n) time, assuming constant-time comparisons.
     * Comparison result zero defines a matching key; equals is not consulted.
     */
    public boolean contains(E value)
    {
        if (value == null)
            throw new NullPointerException("Element must not be null");
        Node current = root;
        while (current != null)
        {
            int comparison = order.compare(value, current.value);
            if (comparison == 0)
                return true;
            if (comparison < 0)
                current = current.left;
            else
                current = current.right;
        }
        return false;
    }

    /**
     * Inserts a new key and restores balance, in O(log n) time.
     * Returns false for an existing key and retains the stored element.
     * A preliminary search makes the duplicate case a genuine no-op.
     */
    public boolean add(E value)
    {
        if (value == null)
            throw new NullPointerException("Element must not be null");
        // Even the first element must be acceptable to the comparator.
        order.compare(value, value);
        if (contains(value))
            return false;
        root = insert(root, value);

        size++;
        modifications++;
        return true;
    }

    /**
     * Removes one comparison key and restores balance, in O(log n) time.
     * Returns false without changing the tree if the key is absent.
     */
    public boolean remove(E value)
    {
        if (!contains(value))
            return false;

        root = delete(root, value);

        size--;
        modifications++;
        return true;
    }

    /** First element in comparator order; throws if this tree is empty. */
    public E first()
    {
        if (root == null)
            throw new NoSuchElementException("Empty tree");
        return minimum(root).value;
    }

    /** Last element in comparator order; throws if this tree is empty. */
    public E last()
    {
        if (root == null)
            throw new NoSuchElementException("Empty tree");
        Node current = root;
        while (current.right != null)
        {
            current = current.right;
        }
        return current.value;
    }

    private Node minimum(Node node)
    {
        while (node.left != null)
        {
            node = node.left;
        }
        return node;
    }

    /** Releases the root reference; invalidates iterators if the tree was nonempty. */
    public void clear()
    {
        if (root != null)
        {
            root = null;
            size = 0;
            modifications++;
        }
    }

    /** Height in nodes: an empty tree has height 0 and a leaf has height 1. */
    public int height()
    {
        return height(root);
    }

    private int height(Node node)
    {
        if (node == null)
            return 0;
        return node.height;
    }

    /**
     * Diagnostic only: checks ordering, balance, metadata and element count.
     * Takes O(n) time. It is not part of the logarithmic update/search operations.
     */
    public boolean invariantsHold()
    {
        return audit(root, null, null) >= 0 && count(root) == size;
    }

    private int count(Node node)
    {
        if (node == null)
            return 0;
        return 1 + count(node.left) + count(node.right);
    }

    /**
     * Inorder iterator with O(height) auxiliary storage. A complete traversal
     * takes O(n) time. Structural changes invalidate existing iterators;
     * adding a duplicate or removing an absent key does not invalidate them.
     * Fail-fast detection is a debugging aid, not a thread-safety guarantee.
     */
    @Override
    public Iterator<E> iterator()
    {
        return new TreeIterator();
    }

    private final class TreeIterator implements Iterator<E>
    {
        private final Deque<Node> pending = new ArrayDeque<>();
        private final int expectedModifications = modifications;

        TreeIterator()
        {
            pushLeft(root);
        }

        private void pushLeft(Node node)
        {
            while (node != null)
            {
                pending.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext()
        {
            if (expectedModifications != modifications)
                throw new ConcurrentModificationException();
            return !pending.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Node node = pending.pop();
            pushLeft(node.right);
            return node.value;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("Use the tree's remove method");
        }
    }

    /** Comparator-ordered contents; constructing the text takes O(n) visits. */
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        for (E value : this)
        {
            if (result.length() > 1)
                result.append(", ");
            result.append(value);
        }
        return result.append(']').toString();
    }

    // Optional implementation study: maintain heights while recursion unwinds.
    private Node insert(Node node, E value)
    {
        if (node == null)
            return new Node(value);
        if (order.compare(value, node.value) < 0)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        return rebalance(node);
    }

    // The public method has already checked that the key exists.
    private Node delete(Node node, E value)
    {
        int comparison = order.compare(value, node.value);
        if (comparison < 0)
            node.left = delete(node.left, value);
        else if (comparison > 0)
            node.right = delete(node.right, value);
        else
        {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            // Replace by the inorder successor, then remove its old node.
            node.value = minimum(node.right).value;
            node.right = delete(node.right, node.value);
        }
        return rebalance(node);
    }

    private void updateHeight(Node node)
    {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node node)
    {
        return height(node.left) - height(node.right);
    }

    /** Restores the AVL condition after updating either child subtree. */
    private Node rebalance(Node node)
    {
        updateHeight(node);
        int balance = balanceFactor(node);
        if (balance > 1)
        {
            // Left-right case: first turn it into a left-left case.
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1)
        {
            // Right-left case: first turn it into a right-right case.
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    /** Promotes the right child; preserves the inorder sequence. */
    private Node rotateLeft(Node oldRoot)
    {
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;
        // The former root is now lower: update it before its new parent.
        updateHeight(oldRoot);
        updateHeight(newRoot);
        return newRoot;
    }

    /** Mirror of rotateLeft; the middle subtree is kept, never discarded. */
    private Node rotateRight(Node oldRoot)
    {
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        updateHeight(oldRoot);
        updateHeight(newRoot);
        return newRoot;
    }

    // Returns the independently calculated height, or -1 if anything is wrong.
    private int audit(Node node, E lower, E upper)
    {
        if (node == null)
            return 0;
        if (lower != null && order.compare(node.value, lower) <= 0)
            return -1;
        if (upper != null && order.compare(node.value, upper) >= 0)
            return -1;
        int left = audit(node.left, lower, node.value);
        int right = audit(node.right, node.value, upper);
        if (left < 0 || right < 0 || Math.abs(left - right) > 1)
            return -1;
        int actual = 1 + Math.max(left, right);
        if (node.height != actual)
            return -1;
        return actual;
    }

    /**
     * Returns a sideways text view without changing the tree or its iterator.
     * The right subtree is above its parent; the left subtree is below it.
     * ROOT marks the root, and R/L identify child links, including single children.
     * Each depth adds four spaces. Empty children are omitted; an empty tree is
     * shown explicitly. Newlines in element text are escaped to keep one node
     * per line. Uses O(height) recursion and time proportional to output length.
     */
    public String toTreeString()
    {
        if (root == null)
            return "(empty)\n";
        StringBuilder result = new StringBuilder();
        appendTree(root, 0, "ROOT", result);
        return result.toString();
    }

    private void appendTree(Node node, int depth, String link, StringBuilder result)
    {
        if (node == null)
            return;
        appendTree(node.right, depth + 1, "R", result);
        for (int i = 0; i < depth; i++)
        {
            result.append("    ");
        }
        String label = String.valueOf(node.value).replace("\r", "\\r").replace("\n", "\\n")
                .replace("\t", "\\t");
        result.append(link).append(": ").append(label);
        result.append(" [h=").append(node.height)
                .append(", bf=").append(balanceFactor(node)).append(']');
        result.append('\n');
        appendTree(node.left, depth + 1, "L", result);
    }
}
