package ds;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** Child subtrees are copied on insertion, so sharing cannot introduce cycles. */
public class ListTree<E> extends AbstractTree<E> implements Iterable<E>
{
    private E label;
    private final ArrayList<ListTree<E>> children = new ArrayList<>();

    public ListTree(E label)
    {
        setLabel(label);
    }

    public ListTree(Tree<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        setLabel(source.label());
        for (Tree<? extends E> child : source.children())
        {
            addChild(child);
        }
    }

    @Override
    public E label()
    {
        return label;
    }

    public void setLabel(E label)
    {
        if (label == null)
            throw new NullPointerException("Label must not be null");
        this.label = label;
    }

    public void addChild(Tree<? extends E> child)
    {
        if (child == null)
            throw new NullPointerException("Child must not be null");
        children.add(new ListTree<>(child));
    }

    public ListTree<E> removeChild(int index)
    {
        if (index < 0 || index >= children.size())
            throw new IndexOutOfBoundsException("Invalid child index: " + index);
        return children.remove(index);
    }

    /** A snapshot of the child references protects the internal list of links. */
    @Override
    public Iterable<? extends Tree<E>> children()
    {
        return new ArrayList<>(children);
    }

    @Override
    public Iterator<E> iterator()
    {
        return new PreOrderIterator();
    }

    private class PreOrderIterator implements Iterator<E>
    {
        private final Deque<ListTree<E>> pending = new ArrayDeque<>();

        PreOrderIterator()
        {
            pending.push(ListTree.this);
        }

        @Override
        public boolean hasNext()
        {
            return !pending.isEmpty();
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            ListTree<E> node = pending.pop();
            // Push in reverse order so that the first child is visited next.
            for (int i = node.children.size() - 1; i >= 0; i--)
            {
                pending.push(node.children.get(i));
            }
            return node.label;
        }
    }
}
