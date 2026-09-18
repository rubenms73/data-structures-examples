package ds;

import java.util.AbstractCollection;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An unbalanced binary search tree. Comparison defines duplicate elements.
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
            if (result == 0)
                return false;
            if (result < 0)
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
            // Two children: replace the value with its in-order successor.
            Node successor = node.right;
            while (successor.left != null)
            {
                successor = successor.left;
            }
            node.value = successor.value;
            node.right = remove(node.right, successor.value);
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
}
