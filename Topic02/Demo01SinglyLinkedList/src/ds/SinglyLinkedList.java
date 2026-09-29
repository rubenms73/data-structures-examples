package ds;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A collection backed by singly linked nodes, built on AbstractCollection.
 * The name describes its storage; this class does not implement List.
 * Null elements are allowed. During traversal, modify only through the iterator.
 */
public class SinglyLinkedList<E> extends AbstractCollection<E>
{
    private class Node
    {
        // Element stored in this node; it may be null.
        E value;
        // Successor in the list; null marks the end.
        Node next;

        Node(E value)
        {
            this.value = value;
            this.next = null;
        }

        Node(E value, Node next)
        {
            this.value = value;
            this.next = next;
        }
    }

    // First node, or null when the list is empty.
    private Node head;
    // Last node, or null when empty. Keeping it makes append constant-time.
    private Node tail;
    // Number of linked nodes, including nodes whose element is null.
    private int size;

    @Override
    public int size()
    {
        return size;
    }

    /** Append in constant time by retaining the last node. */
    @Override
    public boolean add(E value)
    {
        Node node = new Node(value);
        if (tail == null)
            head = node;
        else
            tail.next = node;
        tail = node;
        size++;
        return true;
    }

    @Override
    public Iterator<E> iterator()
    {
        return new ForwardIterator();
    }

    // Each iterator has its own references into the same list.
    // After next() returns B in A, B, C: beforeLast = A,
    // lastReturned = previous = B, and next = C.
    // remove() then links A to C and makes previous refer to A.
    private class ForwardIterator implements Iterator<E>
    {
        // Node whose element the next call to next() will return.
        private Node next = head;
        // Predecessor of next; null while the cursor is before the first node.
        private Node previous;
        // Node returned by the last next(); null when remove() is not permitted.
        private Node lastReturned;
        // Predecessor of lastReturned at the time it was returned.
        // Needed to unlink that node without searching again from head.
        private Node beforeLast;

        @Override
        public boolean hasNext()
        {
            return next != null;
        }

        @Override
        public E next()
        {
            if (!hasNext())
                throw new NoSuchElementException();
            // Save both the returned node and its predecessor before advancing.
            beforeLast = previous;
            lastReturned = next;
            previous = next;
            next = next.next;
            return lastReturned.value;
        }

        @Override
        public void remove()
        {
            // Each successful next() permits at most one remove().
            if (lastReturned == null)
                throw new IllegalStateException();
            // If there is no predecessor, the removed node is the head.
            // Otherwise bypass it: beforeLast.next must point to next.
            if (beforeLast == null)
                head = next;
            else
                beforeLast.next = next;
            // Removing the last node also moves tail to its predecessor.
            // For a one-node list, both head and tail become null.
            if (lastReturned == tail)
                tail = beforeLast;
            // next already refers to the first unvisited node: leave it there.
            // Its predecessor is now beforeLast, not the detached node.
            previous = beforeLast;
            // Disallow a second remove() until next() returns another element.
            lastReturned = null;
            size--;
        }
    }
}
