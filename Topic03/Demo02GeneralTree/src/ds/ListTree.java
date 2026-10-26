package ds;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/** Child subtrees are copied on insertion, so sharing cannot introduce cycles. */
public class ListTree<E> extends AbstractTree<E> implements Iterable<E>
{
    // Element at the root of this subtree.
    private E label;
    // Ordered child subtrees, each with its own independently copied links.
    private final List<ListTree<E>> children = new ArrayList<>();

    public ListTree(E label)
    {
        assignLabel(label);
    }

    public ListTree(Tree<E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        assignLabel(source.label());
        ChildrenIterator<Tree<E>> itr = source.childrenIterator();
        while (itr.hasNext())
        {
            copyChild(itr.next());
        }
    }

    @Override
    public boolean isLeaf()
    {
        return children.isEmpty();
    }

    @Override
    public E label()
    {
        return label;
    }

    @Override
    public void setLabel(E label)
    {
        assignLabel(label);
    }

    // Constructors use private helpers so subclass setters cannot run too early.
    private void assignLabel(E label)
    {
        if (label == null)
            throw new NullPointerException("Label must not be null");
        this.label = label;
    }

    public void addChild(Tree<E> child)
    {
        copyChild(child);
    }

    private void copyChild(Tree<E> child)
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

    @Override
    public ChildrenIterator<Tree<E>> childrenIterator()
    {
        return new ReadOnlyChildrenIterator();
    }

    // Read-only: set, add and remove keep their default UnsupportedOperationException.
    private class ReadOnlyChildrenIterator implements ChildrenIterator<Tree<E>>
    {
        private int index;  // position of the child that next() returns

        @Override
        public boolean hasNext()
        {
            return index < children.size();
        }

        @Override
        public Tree<E> next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            index++;
            return children.get(index - 1);
        }
    }

    @Override
    public Iterator<E> iterator()
    {
        return new PreOrderIterator();
    }

    private class PreOrderIterator implements Iterator<E>
    {
        // Stack of subtree roots still to visit; the top is visited next.
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
