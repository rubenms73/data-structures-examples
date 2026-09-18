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
        E value;
        Node left;
        Node right;

        Node(E value)
        {
            this.value = value;
        }
    }

    private Node root;
    private int size;
    private final Comparator<? super E> order;

    public BinarySearchTree()
    {
        order = null;
    }

    public BinarySearchTree(Comparator<? super E> order)
    {
        this.order = order;
    }

    public BinarySearchTree(Collection<? extends E> source)
    {
        this(source, null);
    }

    public BinarySearchTree(Collection<? extends E> source,
                            Comparator<? super E> order)
    {
        this(order);
        if (source == null)
            throw new NullPointerException("Source must not be null");
        addAll(source);
    }

    @SuppressWarnings("unchecked")
    private int compare(E first, E second)
    {
        if (order != null)
            return order.compare(first, second);
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
        if (value == null)
            throw new NullPointerException("Null elements are not supported");
        if (root == null)
        {
            // Check comparison even when inserting the first element.
            compare(value, value);
            root = new Node(value);
            size++;
            return true;
        }
        Node current = root;
        while (true)
        {
            int result = compare(value, current.value);
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
            int result = compare(item, current.value);
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
        // A preliminary search keeps recursive removal simple: the value exists.
        if (!contains(value))
            return false;
        root = remove(root, (E) value);
        size--;
        return true;
    }

    // Returns the new root of this subtree, including when deleting the root.
    private Node remove(Node node, E value)
    {
        int result = compare(value, node.value);
        if (result < 0)
            node.left = remove(node.left, value);
        else if (result > 0)
            node.right = remove(node.right, value);
        else
        {
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;
            // With duplicates on the left, use the greatest value on the left.
            // A successor could leave an equal value in the strictly greater
            // right subtree. Detach the actual predecessor node, not any match.
            Node parent = node;
            Node predecessor = node.left;
            while (predecessor.right != null)
            {
                parent = predecessor;
                predecessor = predecessor.right;
            }
            node.value = predecessor.value;
            if (parent == node)
                parent.left = predecessor.left;
            else
                parent.right = predecessor.left;
        }
        return node;
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
            return node.value;
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
        String label = String.valueOf(node.value).replace("\r", "\\r").replace("\n", "\\n")
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
