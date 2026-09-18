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
public final class RedBlackTree<E> implements Iterable<E>
{
    /** Nodes and links are private: clients cannot break the tree invariants. */
    private final class Node
    {
        E value;
        Node left;
        Node right;
        boolean red = true;

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
    public RedBlackTree(Comparator<? super E> order)
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
        root.red = false;
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
        // Temporarily supply a red root if both child roots are black.
        if (!isRed(root.left) && !isRed(root.right))
            root.red = true;
        root = delete(root, value);
        if (root != null)
            root.red = false;
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
        return 1 + Math.max(height(node.left), height(node.right));
    }

    /**
     * Diagnostic only: checks ordering, balance, metadata and element count.
     * Takes O(n) time. It is not part of the logarithmic update/search operations.
     */
    public boolean invariantsHold()
    {
        return !isRed(root) && audit(root, null, null) >= 0 && count(root) == size;
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

    /** Null links represent black NIL leaves; no sentinel objects are needed. */
    private boolean isRed(Node node)
    {
        return node != null && node.red;
    }

    // Optional implementation study: left-leaning red-black (LLRB) variant.
    private Node insert(Node node, E value)
    {
        if (node == null)
            return new Node(value);
        if (order.compare(value, node.value) < 0)
            node.left = insert(node.left, value);
        else
            node.right = insert(node.right, value);
        return repair(node);
    }

    /** Restores left-leaning links and splits a temporary node with two red children. */
    private Node repair(Node node)
    {
        if (isRed(node.right) && !isRed(node.left))
            node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left))
            node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right))
            flipColours(node);
        return node;
    }

    // Deletion prepares the next subtree BEFORE descending into it. This keeps
    // a red link available at the deletion position instead of losing a black
    // level. The public contains check guarantees that the search path exists.
    private Node delete(Node node, E value)
    {
        if (order.compare(value, node.value) < 0)
        {
            if (!isRed(node.left) && !isRed(node.left.left))
                node = prepareLeft(node);
            node.left = delete(node.left, value);
        }
        else
        {
            if (isRed(node.left))
                node = rotateRight(node);
            // Recompare after rotations: the subtree root may have changed.
            if (order.compare(value, node.value) == 0 && node.right == null)
                return null;
            if (!isRed(node.right) && !isRed(node.right.left))
                node = prepareRight(node);
            if (order.compare(value, node.value) == 0)
            {
                node.value = minimum(node.right).value;
                node.right = deleteMinimum(node.right);
            }
            else
                node.right = delete(node.right, value);
        }
        return repair(node);
    }

    private Node deleteMinimum(Node node)
    {
        if (node.left == null)
            return null;
        if (!isRed(node.left) && !isRed(node.left.left))
            node = prepareLeft(node);
        node.left = deleteMinimum(node.left);
        return repair(node);
    }

    /** Makes the left descent safe, borrowing a red link from the right if needed. */
    private Node prepareLeft(Node node)
    {
        flipColours(node);
        if (isRed(node.right.left))
        {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColours(node);
        }
        return node;
    }

    /** Makes the right descent safe, borrowing a red link from the left if needed. */
    private Node prepareRight(Node node)
    {
        flipColours(node);
        if (isRed(node.left.left))
        {
            node = rotateRight(node);
            flipColours(node);
        }
        return node;
    }

    // Called only when both children exist. Toggling can split or merge a
    // temporary group; it preserves the equal black count through this subtree.
    private void flipColours(Node node)
    {
        node.red = !node.red;
        node.left.red = !node.left.red;
        node.right.red = !node.right.red;
    }

    /** Promotes the right child, transferring the incoming colour to the new root. */
    private Node rotateLeft(Node oldRoot)
    {
        Node newRoot = oldRoot.right;
        oldRoot.right = newRoot.left;
        newRoot.left = oldRoot;
        newRoot.red = oldRoot.red;
        oldRoot.red = true;
        return newRoot;
    }

    /** Mirror rotation: the former root receives a red incoming link. */
    private Node rotateRight(Node oldRoot)
    {
        Node newRoot = oldRoot.left;
        oldRoot.left = newRoot.right;
        newRoot.right = oldRoot;
        newRoot.red = oldRoot.red;
        oldRoot.red = true;
        return newRoot;
    }

    // Returns a black-node count INCLUDING this node and the black NIL leaf,
    // or -1 for an invalid subtree. This helper's counting convention is local;
    // it does not redefine the lecture's black height (which excludes its root).
    private int audit(Node node, E lower, E upper)
    {
        if (node == null)
            return 1;
        if (lower != null && order.compare(node.value, lower) <= 0)
            return -1;
        if (upper != null && order.compare(node.value, upper) >= 0)
            return -1;
        if (isRed(node.right))
            return -1;
        if (isRed(node) && isRed(node.left))
            return -1;
        int left = audit(node.left, lower, node.value);
        int right = audit(node.right, node.value, upper);
        if (left < 0 || right < 0 || left != right)
            return -1;
        if (node.red)
            return left;
        return left + 1;
    }

    /**
     * Returns a sideways text view without changing the tree or its iterator.
     * The right subtree is above its parent; the left subtree is below it.
     * ASCII branches connect parents and children; [ROOT] identifies the root.
     * Vertical bars continue ancestor branches. Empty children are omitted; an empty tree is
     * shown explicitly. Newlines in element text are escaped to keep one node
     * per line. Uses O(height) recursion and time proportional to output length.
     */
    public String toTreeString()
    {
        if (root == null)
            return "(empty)\n";
        StringBuilder result = new StringBuilder();
        appendTree(root, "", "ROOT", result);
        return result.toString();
    }

    private void appendTree(Node node, String prefix, String link, StringBuilder result)
    {
        if (node == null)
            return;
        boolean isRoot = link.equals("ROOT");
        boolean isLeft = link.equals("L");
        String above = prefix;
        String below = prefix;
        if (!isRoot)
        {
            // Keep the vertical connector while crossing the parent's level.
            above += isLeft ? "|       " : "        ";
            below += isLeft ? "        " : "|       ";
        }
        appendTree(node.right, above, "R", result);
        if (node.right != null)
            result.append(above).append("|\n");
        result.append(prefix);
        if (isRoot)
            result.append("+-- ");
        else if (isLeft)
            result.append("\\-- ");
        else
            result.append("/-- ");
        String label = String.valueOf(node.value).replace("\r", "\\r").replace("\n", "\\n")
                .replace("\t", "\\t");
        result.append(label);
        if (isRoot)
            result.append(" [ROOT]");
        result.append(node.red ? " [R]" : " [B]");
        result.append('\n');
        if (node.left != null)
            result.append(below).append("|\n");
        appendTree(node.left, below, "L", result);
    }
}
