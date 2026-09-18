# 02 — A singly linked list with ListIterator

## Problem statement and prerequisites

Implement a generic singly linked list by extending `AbstractSequentialList<E>`.
Provide `size()` and `listIterator(int index)` so that the superclass can supply
indexed access and modification. Implement all cursor operations, including
backward traversal, insertion, replacement and removal.

Prerequisites: references, linked nodes, generics, inheritance, abstract classes,
`List`, `Iterator` and `ListIterator`. Study a simple forward iterator first.

## Representation and contracts

`head` references the first node; `count` is the number of nodes. An empty list
has `head == null` and `count == 0`. Nodes are private inner objects.
Elements may be null. A null source collection or varargs array is rejected
with a simple conditional and `NullPointerException`.

The conversion constructor accepts `Collection<? extends E>`. It creates new
nodes, preserving order and sharing element references. Replacing a list entry
does not change the other list; mutating a shared element object affects both.

Iterator positions range from zero to size, inclusive. The cursor lies between
`previousNode` and `nextNode`. `nextIndex` is the index that `next()` would return.
`lastReturned` has a different purpose: it records the node affected by `set`
or `remove`, regardless of traversal direction. A null value in this field means
that those operations are not currently allowed; a node containing null is valid.

`add` inserts before the next node and leaves the cursor after the inserted node.
Both `add` and `remove` invalidate `lastReturned`. `set` may be repeated and does
not invalidate it. Exhausted movement throws `NoSuchElementException`.

## Guided walkthrough

1. Inspect Node, head and count, then trace construction using iterator insertion.
2. Follow the iterator constructor to see how a cursor reaches an index.
3. Trace next and previous: both record the returned node before moving the cursor.
4. Follow remove after each direction. After next, the cursor index decreases;
   after previous, the next node changes and the index stays the same.
5. Inspect add at the head, in an empty list and at the end.
6. Run Main. Its get/set/clear and printing operations illustrate inherited reuse.

## Costs and limitations

| Operation | Cost |
|---|---|
| size, hasNext, hasPrevious, cursor indices | O(1) |
| listIterator(index) | O(index) |
| next, iterator add, iterator set | O(1) |
| previous | O(n) worst case |
| iterator remove after next | O(n) worst case |
| iterator remove after previous | O(1) |
| construction from n elements | O(n) |

A backward step searches from the head for the predecessor. Complete backward
traversal can therefore cost O(n²). Inherited repeated append operations can
also cost O(n²); constructors avoid this by reusing one iterator.

Only modify the list through the active iterator during traversal. This teaching
implementation does not detect external modifications. It is not a replacement
for the standard library's LinkedList. Adding backward links would be a separate
exercise, with different storage costs and invariants.

## What to observe and try

Try remove/set before movement, remove twice, and set immediately after add.
Each must throw IllegalStateException. Test previous followed by set or remove,
insertion into an empty list, null elements and copying Integer elements into
a list of Number. Draw the cursor between nodes after each operation.

## Open and run

Open this folder in VS Code with JDK 17 and Extension Pack for Java. Run
`src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

The project is self-contained. Sources use packages ds and app; automated tests
use package tests. The tests compare mixed iterator operations against Java's
LinkedList, including return values, exceptions, contents and cursor positions.

## Expected output

```text
Initial: [1, 2, 3]
Insert at start: [0, 1, 2, 3]
Next: 1
Remove after next: [0, 2, 3]
Previous: 0
Set after previous: [10, 2, 3]
Remove after previous: [2, 3]
Independent copy: [2.5, 3]
Original: [2, 3]
Inherited clear: []
```
