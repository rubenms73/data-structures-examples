package ds;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An unbalanced binary search tree retaining every occurrence.
 * Left descendants compare <= their root; right descendants compare > it.
 * Null elements are rejected. Do not modify the tree during iteration.
 * Iterator removal and inherited operations that require it are unsupported.
 */
public class BinarySearchTree<E> extends AbstractCollection<E>
{
    private class Node
    {
        // Element used as the comparison key at this node.
        E label;
        // Root of the left subtree, or null if absent.
        Node left;
        // Root of the right subtree, or null if absent.
        Node right;

        Node(E label)
        {
            this.label = label;
        }
    }

    // Root of the whole tree, or null for an empty tree.
    private Node root;
    // Number of nodes, including repeated comparison keys.
    private int size;
    // Search ordering; null selects natural ordering.
    private final Comparator<? super E> cmp;

    public BinarySearchTree()
    {
        cmp = null;
    }

    public BinarySearchTree(Comparator<? super E> cmp)
    {
        this.cmp = cmp;
    }

    public BinarySearchTree(Collection<? extends E> source)
    {
        this(source, null);
    }

    public BinarySearchTree(Collection<? extends E> source,
                            Comparator<? super E> cmp)
    {
        this(cmp);
        if (source == null)
            throw new NullPointerException("Source must not be null");
        // The subclass is not initialised yet: use private insertion logic.
        for (E value : source)
        {
            insert(value);
        }
    }

    @SuppressWarnings("unchecked")
    private int compare(E first, E second)
    {
        if (cmp != null)
            return cmp.compare(first, second);
        if (!(first instanceof Comparable<?>))
            throw new ClassCastException("Elements must implement Comparable");
        return ((Comparable<? super E>) first).compareTo(second);
    }

    @Override
    public int size()
    {
        return size;
    }

    /** Insert one occurrence, including comparison-equivalent values. */
    @Override
    public boolean add(E value)
    {
        return insert(value);
    }

    // Construction and add() share the same validation and node-linking logic.
    private boolean insert(E value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        if (root == null)
        {
            root = new Node(value);
            size++;
            return true;
        }
        Node current = root;
        while (true)
        {
            int result = compare(value, current.label);
            // Equal values go left, as in the Topic 3 presentation.
            if (result <= 0)
            {
                if (current.left == null)
                {
                    current.left = new Node(value);
                    size++;
                    return true;
                }
                current = current.left;
            }
            else
            {
                if (current.right == null)
                {
                    current.right = new Node(value);
                    size++;
                    return true;
                }
                current = current.right;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        E item = (E) value;
        Node current = root;
        while (current != null)
        {
            int result = compare(item, current.label);
            if (result == 0)
                return true;
            if (result < 0)
                current = current.left;
            else
                current = current.right;
        }
        return false;
    }

    /** Remove one occurrence identified by the comparison relation. */
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object value)
    {
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        E item = (E) value;
        // Find the node, keeping its parent one step behind.
        Node parent = null;
        Node current = root;
        while (current != null)
        {
            int result = compare(item, current.label);
            if (result == 0)
                break;
            parent = current;
            if (result < 0)
                current = current.left;
            else
                current = current.right;
        }
        if (current == null)
            return false;
        // Store what removeTop returns in the link that led to current.
        if (parent == null)
            root = removeTop(current);
        else if (parent.left == current)
            parent.left = removeTop(current);
        else
            parent.right = removeTop(current);
        size--;
        return true;
    }

    // Removes the element at the root of subtree t and returns the root of what remains.
    private Node removeTop(Node t)
    {
        if (t.left == null)
            return t.right;
        if (t.right == null)
            return t.left;
        // With duplicates on the left, use the maximum of the left subtree.
        // The minimum of the right subtree could leave an equal value on the right.
        Node parent = t;
        Node p = t.left;
        while (p.right != null)
        {
            parent = p;
            p = p.right;
        }
        t.label = p.label;
        if (parent == t)
            parent.left = p.left;
        else
            parent.right = p.left;
        return t;
    }

    @Override
    public void clear()
    {
        // Override the inherited implementation, which uses iterator.remove().
        root = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new InOrderIterator();
    }

    /** Explicit name for the default left-root-right traversal. */
    public Iterator<E> inOrderIterator()
    {
        return new InOrderIterator();
    }

    /** Root-left-right traversal using a stack of pending subtree roots. */
    public Iterator<E> preOrderIterator()
    {
        return new PreOrderIterator();
    }

    /** Left-right-root traversal using one stack, without collecting all nodes. */
    public Iterator<E> postOrderIterator()
    {
        return new PostOrderIterator();
    }

    /** Breadth-first traversal, visiting each level from left to right. */
    public Iterator<E> levelOrderIterator()
    {
        return new LevelOrderIterator();
    }

    private class PreOrderIterator implements Iterator<E>
    {
        private final Deque<Node> stack = new ArrayDeque<>();

        PreOrderIterator()
        {
            if (root != null)
                stack.push(root);
        }

        @Override
        public boolean hasNext()
        {
            return !stack.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Node node = stack.pop();
            // LIFO: push right first so that left is visited first.
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
            return node.label;
        }
    }

    private class PostOrderIterator implements Iterator<E>
    {
        private final Deque<Node> stack = new ArrayDeque<>();

        PostOrderIterator()
        {
            pushPathToLeaf(root);
        }

        private void pushPathToLeaf(Node node)
        {
            // Each stack entry is an ancestor whose own visit is still pending.
            while (node != null)
            {
                stack.push(node);
                if (node.left != null)
                    node = node.left;
                else
                    node = node.right;
            }
        }

        @Override
        public boolean hasNext()
        {
            return !stack.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Node node = stack.pop();
            if (!stack.isEmpty())
            {
                Node parent = stack.peek();
                // Finishing the left subtree makes the right subtree pending.
                // Compare node identities, not labels: duplicates are allowed.
                if (parent.left == node && parent.right != null)
                    pushPathToLeaf(parent.right);
            }
            return node.label;
        }
    }

    private class LevelOrderIterator implements Iterator<E>
    {
        private final Deque<Node> queue = new ArrayDeque<>();

        LevelOrderIterator()
        {
            if (root != null)
                queue.addLast(root);
        }

        @Override
        public boolean hasNext()
        {
            return !queue.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Node node = queue.removeFirst();
            // FIFO: append children after the nodes already waiting at this level.
            if (node.left != null)
                queue.addLast(node.left);
            if (node.right != null)
                queue.addLast(node.right);
            return node.label;
        }
    }

    private class InOrderIterator implements Iterator<E>
    {
        private final Deque<Node> stack = new ArrayDeque<>();

        InOrderIterator()
        {
            pushLeft(root);
        }

        private void pushLeft(Node node)
        {
            // The stack holds nodes whose visit is still pending.
            while (node != null)
            {
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext()
        {
            return !stack.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            Node node = stack.pop();
            pushLeft(node.right);
            return node.label;
        }
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
        String label = String.valueOf(node.label).replace("\r", "\\r").replace("\n", "\\n")
                .replace("\t", "\\t");
        result.append(label);
        if (isRoot)
            result.append(" [ROOT]");
        result.append('\n');
        if (node.left != null)
            result.append(below).append("|\n");
        appendTree(node.left, below, "L", result);
    }
}
