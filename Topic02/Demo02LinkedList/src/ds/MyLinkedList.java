package ds;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A singly linked list built on AbstractSequentialList.
 * Null elements are allowed. Element references are copied, not their objects.
 * Do not modify the list through another iterator or another operation while
 * traversing it: this teaching implementation does not detect such changes.
 */
public class MyLinkedList<E> extends AbstractSequentialList<E>
{
    private class Node
    {
        // Element stored in this node; it may be null.
        E info;
        // Successor in the list; null marks the end.
        Node next;

        Node(E info)
        {
            this.info = info;
            this.next = null;
        }

        Node(E info, Node next)
        {
            this.info = info;
            this.next = next;
        }
    }

    // First node, or null when the list is empty.
    private Node head;
    // Number of linked nodes; maintained during insertion and removal.
    private int count;

    public MyLinkedList()
    {
        head = null;
        count = 0;
    }

    /** Creates a list containing the supplied elements, in order. */
    @SafeVarargs
    public MyLinkedList(E... items)
    {
        if (items == null)
            throw new NullPointerException("Items must not be null");
        // Use the private iterator directly: subclass overrides must not run
        // before their own fields have been initialised.
        MyListIterator it = new MyListIterator(0);
        for (E item : items)
        {
            it.add(item);
        }
    }

    /** Conversion constructor: creates independent nodes in source order. */
    public MyLinkedList(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        // Use the private iterator directly: subclass overrides must not run
        // before their own fields have been initialised.
        MyListIterator it = new MyListIterator(0);
        for (E item : source)
        {
            it.add(item);
        }
    }

    @Override
    public int size()
    {
        return count;
    }

    /** The cursor may be placed at size(), immediately after the last node. */
    @Override
    public ListIterator<E> listIterator(int index)
    {
        if (index < 0 || index > count)
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        return new MyListIterator(index);
    }

    private class MyListIterator implements ListIterator<E>
    {
        // The cursor lies between previousNode and nextNode.
        // Node to the right of the cursor; null at position size().
        private Node nextNode;
        // Node to the left of the cursor; null at position 0.
        private Node previousNode;
        // Node returned by the last successful next() or previous().
        // Null means that neither set nor remove is currently permitted.
        private Node lastReturned;
        // Index of nextNode, or count at the end; also the cursor position.
        private int nextIndex;
        // Records which side of the cursor contains lastReturned.
        // Only meaningful after next() or previous() has succeeded.
        private boolean lastMoveWasNext;

        MyListIterator(int index)
        {
            nextNode = head;
            previousNode = null;
            // Insertion or removal consumes the right to set/remove.
            // A later next() or previous() establishes a new lastReturned.
            lastReturned = null;
            nextIndex = 0;
            while (nextIndex < index)
            {
                previousNode = nextNode;
                nextNode = nextNode.next;
                nextIndex++;
            }
        }

        // There are no backward links: finding a predecessor costs O(n).
        private Node predecessor(Node node)
        {
            if (node == head)
                return null;
            // A singly linked node cannot reach its predecessor directly.
            // Walk from head until the next link reaches the target node.
            Node current = head;
            while (current != null && current.next != node)
            {
                current = current.next;
            }
            return current;
        }

        @Override
        public boolean hasNext()
        {
            return nextNode != null;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            lastReturned = nextNode;
            previousNode = nextNode;
            nextNode = nextNode.next;
            nextIndex++;
            lastMoveWasNext = true;
            return lastReturned.info;
        }

        @Override
        public boolean hasPrevious()
        {
            return previousNode != null;
        }

        @Override
        public E previous()
        {
            if (!hasPrevious())
                throw new NoSuchElementException();
            lastReturned = previousNode;
            nextNode = previousNode;
            previousNode = predecessor(previousNode);
            nextIndex--;
            lastMoveWasNext = false;
            return lastReturned.info;
        }

        @Override
        public int nextIndex()
        {
            return nextIndex;
        }

        @Override
        public int previousIndex()
        {
            return nextIndex - 1;
        }

        @Override
        public void remove()
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            // After next(), the removed node lies before the cursor.
            // After previous(), it lies after the cursor.
            if (lastMoveWasNext)
            {
                // After next(), lastReturned is on the left of the cursor.
                // Find its predecessor before reconnecting the list.
                previousNode = predecessor(lastReturned);
                nextIndex--;
            }
            else
            {
                // After previous(), lastReturned is on the right of the cursor.
                // Move the next reference past it; the cursor index does not change.
                nextNode = lastReturned.next;
            }
            // Reconnect the predecessor to the successor, or move head
            // when the removed node has no predecessor.
            if (previousNode == null)
                head = nextNode;
            else
                previousNode.next = nextNode;
            // One node has been detached. The cursor still refers to the same gap.
            count--;
            // Insertion or removal consumes the right to set/remove.
            // A later next() or previous() establishes a new lastReturned.
            lastReturned = null;
        }

        @Override
        public void set(E value)
        {
            if (lastReturned == null)
                throw new IllegalStateException();
            lastReturned.info = value;
        }

        @Override
        public void add(E value)
        {
            // Insert before nextNode; the cursor ends after the new node.
            Node newNode = new Node(value, nextNode);
            if (previousNode == null)
                head = newNode;
            else
                previousNode.next = newNode;
            // The cursor ends after the inserted element: previous() returns it,
            // while next() still returns the original successor.
            previousNode = newNode;
            nextIndex++;
            count++;
            // Insertion or removal consumes the right to set/remove.
            // A later next() or previous() establishes a new lastReturned.
            lastReturned = null;
        }
    }
}
