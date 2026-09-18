# 01 — Binary search tree

## Problem statement and prerequisites

Implement an unbalanced generic binary search tree supporting insertion, search,
removal, clearing and in-order iteration. Use an optional `Comparator<? super E>`
or the elements' natural order. Reject comparison-equivalent duplicates.

Prerequisites: generic types, Comparable/Comparator, abstract classes, binary
trees, recursion, inner iterators and stacks. ArrayDeque implements the stack
used by the iterator; push and pop operate on the same end.

## Representation and contracts

Each private inner Node holds a value and references to its left and right
children. All values on the left compare smaller, and all values on the right
compare larger. These words refer to the selected ordering, including reverse
order. `root == null` represents the empty tree; size counts stored nodes.

The default constructor uses natural order. The comparator constructor accepts
null to select natural order too. The conversion constructors accept a
`Collection<? extends E>` and insert its elements into independent nodes.
Element objects themselves are shared. Copying does not promise to preserve the
source's shape or comparator: select the desired comparator explicitly.

Null elements are rejected by add, contains and remove with an explicit if and
NullPointerException. A null source is also rejected. Without a comparator,
elements must be mutually comparable; incompatible comparisons may throw
ClassCastException. The first insertion checks comparison before creating a node.

Two elements are duplicates when comparison returns zero, even if equals returns
false. For example, comparing strings only by length treats cat and dog as
equivalent. Choose an ordering consistent with equals when the ordinary
Collection equality-based contract is required; a different ordering deliberately
uses comparison-based membership. Do not change stored objects in ways that
alter their ordering.

## Guided walkthrough

1. Read the constructors and compare. The comparator field uses `? super E`.
2. Follow add and contains from the root. Each comparison selects one subtree.
3. Follow public remove: search first, replace root with the recursive result,
   then decrease size exactly once. The preliminary search simplifies recursion.
4. In recursive remove, a node with no left child is replaced by its right child;
   a node with no right child is replaced by its left child. This includes leaves.
5. For two children, copy the minimum value of the right subtree and remove that
   successor there. Always reconnect the returned subtree root.
6. Follow pushLeft and next: the stack stores pending visits. After visiting a
   node, push the leftmost path of its right subtree.
7. Run Main: each deletion starts from the same tree so cases can be compared.

Main covers leaf removal, one child, two children, the root, an absent value,
duplicates, reverse ordering, independent copies and clear. The inherited
toString displays traversal order, not the tree's shape.

## Costs and limitations

Search, insertion and removal cost O(h), where h is the tree height. Searching
before removal repeats a path but does not change this complexity. The tree is
not balanced: inserting 1, 2, 3, 4, 5 produces a chain of right children with
four edges. Draw it and compare with insertion order 3, 1, 5, 2, 4. Identical
sorted output does not imply identical shape or search cost.

A complete traversal costs O(n), with O(h) auxiliary stack space. One next call
may take O(h), although its amortized cost across traversal is O(1). Recursive
removal also needs O(h) call-stack space and is unsuitable for very deep chains.
Constructing from sorted input may cost O(n²), including copying a naturally
ordered tree through its iterator into another naturally ordered tree.

The iterator does not support remove and does not detect external modifications.
Do not modify the tree during iteration. Inherited removeAll, retainAll and
removeIf may throw UnsupportedOperationException when removal is needed.
clear is explicitly implemented by resetting root and size. AbstractCollection
provides useful reuse, but this is a teaching implementation with optional
operations, not a replacement for TreeSet. Balancing is a later subject.

## What to observe and try

Remove the only element, then insert again. Remove a root with one left child
and one with one right child. Try 2, 1, 3, 4 and remove 2: the successor is the
immediate right child and itself has a right child. Check size after each step.
Try a comparator by string length and explain which object is retained.

## Open and run

Open this folder in VS Code with JDK 17 and Extension Pack for Java. Run
src/app/Main.java, or use:

```sh
bash run.sh
bash run.sh test
```

Sources use packages ds and app. Automated checks in tests cover boundary cases
and compare 20,000 mixed operations with TreeSet in natural and reverse order.
The project has no dependencies beyond the Java standard library.

## Expected output

```text
In order: [1, 3, 4, 6, 7, 8, 10, 13, 14]
Size: 9
Contains 6: true
Add duplicate 6: false
Remove leaf 1: true -> [3, 4, 6, 7, 8, 10, 13, 14]
Remove node 14 with one child: true -> [1, 3, 4, 6, 7, 8, 10, 13]
Remove node 3 with two children: true -> [1, 4, 6, 7, 8, 10, 13, 14]
Remove root 8: true -> [1, 3, 4, 6, 7, 10, 13, 14]
Remove absent 99: false -> [1, 3, 4, 6, 7, 8, 10, 13, 14]
Reverse order: [14, 13, 10, 8, 7, 6, 4, 3, 1]
Original still contains 8: true
Ordered insertion: [1, 2, 3, 4, 5]
After clear: [], size: 0
```
