package ds;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list built on AbstractSequentialList.
 * Null elements are allowed. Element references are copied, not their objects.
 * Do not modify the list through another iterator or another operation while
 * traversing it: this teaching implementation does not detect such changes.
 */
public class DoublyLinkedList<E> extends AbstractSequentialList<E>
{
    private class Node
    {
        // Element stored in this node; it may be null.
        E info;
        // Successor in the list; null marks the end.
        Node next;
        // Predecessor in the list; null marks the beginning.
        Node previous;

        Node(E info)
        {
            this.info = info;
            this.previous = null;
            this.next = null;
        }

        Node(E info, Node previous, Node next)
        {
            this.info = info;
            this.previous = previous;
            this.next = next;
        }
    }

    // First node, or null when the list is empty.
    private Node head;
    // Last node, or null when empty. Keeping it makes append constant-time.
    private Node tail;
    // Number of linked nodes; maintained during insertion and removal.
    private int count;

    public DoublyLinkedList()
    {
        head = null;
        tail = null;
        count = 0;
    }

    /** Creates a list containing the supplied elements, in order. */
    @SafeVarargs
    public DoublyLinkedList(E... items)
    {
        if (items == null)
            throw new NullPointerException("Items must not be null");
        ListIterator<E> it = listIterator();
        for (E item : items)
        {
            it.add(item);
        }
    }

    /** Conversion constructor: creates independent nodes in source order. */
    public DoublyLinkedList(Collection<? extends E> source)
    {
        if (source == null)
            throw new NullPointerException("Source must not be null");
        ListIterator<E> it = listIterator();
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
            // Start from the nearer end. At size(), nextNode is null.
            if (index < count / 2)
            {
                nextNode = head;
                for (int i = 0; i < index; i++)
                {
                    nextNode = nextNode.next;
                }
            }
            else
            {
                previousNode = tail;
                for (int i = count; i > index; i--)
                {
                    nextNode = previousNode;
                    previousNode = previousNode.previous;
                }
            }
            if (nextNode == null)
                previousNode = tail;
            else
                previousNode = nextNode.previous;
            nextIndex = index;
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
            previousNode = previousNode.previous;
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
                // The removed node was before the cursor; one fewer element
                // will remain before it, so the cursor index decreases.
                previousNode = lastReturned.previous;
                nextIndex--;
            }
            else
            {
                // The removed node was after the cursor. Change the next node
                // without changing how many elements precede the cursor.
                nextNode = lastReturned.next;
            }
            // Reconnect the predecessor to the successor, or move head
            // when the removed node has no predecessor.
            if (previousNode == null)
                head = nextNode;
            else
                previousNode.next = nextNode;
            // Restore both directions, including the tail boundary.
            if (nextNode == null)
                tail = previousNode;
            else
                nextNode.previous = previousNode;
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
            Node newNode = new Node(value, previousNode, nextNode);
            if (previousNode == null)
                head = newNode;
            else
                previousNode.next = newNode;
            if (nextNode == null)
                tail = newNode;
            else
                nextNode.previous = newNode;
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
