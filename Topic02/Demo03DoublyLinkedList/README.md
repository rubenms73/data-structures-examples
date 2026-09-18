# 03 — A doubly linked list with ListIterator

## Problem statement and prerequisites

Extend the singly linked implementation from Demo02 with a previous link in each
node and a tail reference. Keep the same ListIterator contract and reuse
AbstractSequentialList<E>. The new links make backward movement and removal
constant-time operations. Study Demo01 and Demo02 first.

## Representation and contracts

Each node stores an element, its predecessor and its successor. The first node's
previous link and the last node's next link are null. An empty list has both
head and tail equal to null and count equal to zero. For adjacent nodes, following
next and then previous must return to the original node.

Null elements are allowed. Null constructor arguments and invalid cursor indices
are rejected using explicit conditionals. The conversion constructor accepts
Collection<? extends E>, creates new nodes in source order and shares element
references: it is a shallow copy, not a copy of the element objects.

## Guided walkthrough

1. Compare Node with Demo02: previous and tail are the additional references.
2. Follow the iterator constructor from the nearer end. Its cursor may be at any
   position from zero to size, inclusive; at size, nextNode is null.
3. Trace next and previous. The cursor lies between previousNode and nextNode;
   lastReturned identifies the node that set or remove may affect.
4. Follow add: connect the new node to both neighbors and update head or tail
   when inserting at a boundary. The cursor finishes after the inserted node.
5. Follow remove after next and after previous. Only removal after next reduces
   nextIndex. Reconnect both directions and maintain the two end references.
6. Remove the only element, then insert again. Both empty-list boundaries must
   remain valid. Run Main to compare the same operations with Demo02.

## Iterator state and limitations

set and remove require a successful next or previous since the last add or
remove; otherwise they throw IllegalStateException. Repeated set is allowed.
Exhausted movement throws NoSuchElementException. add is permitted even before
movement and invalidates lastReturned. A node containing null remains a valid
lastReturned node.

Modify the list only through the active iterator while traversing it. This
teaching implementation does not detect changes made through other iterators
or list operations. Nodes are private inner objects, as in Demo02.

## Costs and comparison

| Operation | Demo02: singly linked | Demo03: doubly linked |
|---|---|---|
| next, add, set through the iterator | O(1) | O(1) |
| previous | O(n) worst case | O(1) |
| remove after next | O(n) worst case | O(1) |
| remove after previous | O(1) | O(1) |
| listIterator(index) | O(index + 1) | O(min(index, size - index) + 1) |
| Complete backward traversal | O(n²) worst case | O(n) |
| Construction from n elements | O(n) | O(n) |

size and cursor queries cost O(1). Indexed access still costs O(n) in the worst
case: backward links do not provide array-style random access. Storage is O(n),
with one additional link per node and one tail reference for the list. Inherited
append now positions the iterator at the tail in O(1).

## What to observe and try

Draw both directions after inserting or removing at the head, middle and tail.
Alternate next and previous over the same node. Try remove twice, set after add,
and movement beyond either end. Copy a list, change one entry, and check that
the original entry is unchanged. Compare full backward traversal with Demo02.

## Open and run

Open this folder in VS Code with JDK 17 and Extension Pack for Java. Run
src/app/Main.java, or use:

```sh
bash run.sh
bash run.sh test
```

Sources use packages ds and app; automated checks use package tests. Checks compare
mixed cursor operations with java.util.LinkedList, including values, exceptions,
contents and cursor positions. The example has no external dependencies.

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
