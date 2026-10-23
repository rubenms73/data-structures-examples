# Red-black trees and the Java Collections Framework

A red-black tree is a balanced binary search tree that uses colour rules to
bound its height. It supports ordered data with logarithmic worst-case lookup,
insertion and deletion. The colours guide the internal repairs; client code
supplies the keys and an ordering, never colours or rotations.

## Problem statement

Build a balanced binary search tree with insertion, lookup, deletion and inorder
iteration. Insert the keys 1 to 15 in ascending order, look at the height, and
count the comparisons needed to find 15. Reject a duplicate, remove a key that
has two children as well as the smallest and the largest keys, and then check
the resulting order and balance. Finally, empty the tree.

The balancing algorithms are fully implemented: they are neither simulated nor
delegated to a JCF container. The program counts the comparator calls actually
made during lookup rather than measuring time. The height of 15 for a plain BST
is a theoretical comparison for this insertion sequence, not the result of
timing a second implementation.

## Teaching scope

**Required focus:** why a plain BST can turn into a chain, why keeping the height logarithmic is useful, and how that affects the cost of searches and updates. Read `app.Main` and look at the output before you open the repair methods.

**Optional study of the implementation, not required for the exam:** the rotation cases, recolouring where it applies, recursive repair and the code that checks the invariants. These methods are included so that the example really works and so that interested students can trace it. The detailed properties below explain the implementation; you are not expected to reproduce its algorithms in the exam.

You should already know generics, comparators and iterators from Topic 1, and
BST ordering and inorder traversal from Topic 3. A `Comparator<? super E>` is
passed explicitly: `Comparator.naturalOrder()` selects natural order and
`Comparator.reverseOrder()` selects reverse order. Private inner classes
represent the nodes and the iterator. As in the earlier BST example, the
iterator uses an `ArrayDeque` as a stack.

## Run

Open this folder on its own in VS Code, with the Java Extension Pack and JDK 17,
and run or debug `src/app/Main.java`. From the same folder:

```sh
bash run.sh
bash run.sh test
```

On Windows, from Command Prompt or PowerShell, without Bash or WSL:

```powershell
.\run.cmd
.\run.cmd test
```

Each example has its own `src`, `tests`, VS Code settings and launchers. It does
not depend on another demo, an external JAR, an internet connection or any
preview feature of Java. The topic launcher also lists and runs this example.

## Public operations and contracts

The class implements `Iterable<E>`, not `Collection<E>` or `Set<E>`. This keeps
the public contract small; the collection interfaces are developed in Topic 4.

- `add(value)` returns `true` only when a new comparison key is inserted.
- `remove(value)` returns `true` only when an existing comparison key is
  removed.
- `contains(value)` performs an ordinary BST search, following one child at a
  time.
- `first()` and `last()` follow the comparator order, which need not be
  ascending numeric order. Both throw `NoSuchElementException` on an empty tree.
- `size()`, `isEmpty()`, `clear()` and `height()` describe or reset the
  container.
- `iterator()` visits the elements in comparator order. `next()` throws
  `NoSuchElementException` once the iterator is exhausted, and the iterator's
  `remove()` is not supported.
- `invariantsHold()` is an O(n) diagnostic used by the demo and the tests;
  normal updates do not perform this extra traversal of the whole tree.

A `null` comparator, or a `null` element passed to `add`, `contains` or
`remove`, causes a `NullPointerException`, thrown from an explicit conditional.
A comparison result of zero identifies an existing key. Adding such a key keeps
the element already stored; `equals` and `hashCode` are not used. The comparator
must provide a consistent and stable total order. The tree stores references,
not deep copies, so do not modify the fields used for ordering while an element
is in the tree. The tree is not thread-safe.

Unlike Demo01, which keeps duplicate occurrences on the left, these examples
store **one element per comparison key**. A rotation can move an equal key to
the other side of its parent, so the earlier rule of keeping duplicates on one
side cannot simply be reused. A frequency field in each node would be one way to
support repeated occurrences, but that extension is not part of these examples.

Do not insert into, remove from or clear the tree while traversing it. Each
iterator has its own traversal stack, but this teaching implementation does not
detect structural changes made during a traversal, so create a new iterator
after updating the tree. Removal through the iterator is still not supported.

## Costs and height convention

Following the Topic 3 slides, height counts **nodes**: an empty tree has height
0 and a leaf has height 1. When comparisons take constant time, lookup,
insertion, deletion, `first` and `last` cost O(log n) in the worst case for a
non-empty tree, and the small or empty cases take constant time. The preliminary
membership search in `add` and `remove` keeps the duplicate and absent cases
simple and leaves the tree unchanged; the second descent only changes a constant
factor.

`size` and `isEmpty` are O(1). `clear` drops the root reference in O(1), not
counting the later garbage collection. Traversing all the elements and checking
the invariants take O(n). The nodes need O(n) storage, and the auxiliary storage
for traversal and recursive updates is O(log n). Creating an iterator and a
single worst-case call to `next()` take O(log n), and a complete traversal takes
O(n). The text output also depends on the length of the elements' strings.

Here `height()` is an O(n) diagnostic that measures the current links
recursively. Lookup and updates do not call it, so they keep their O(log n)
bounds.

## Guided experiments

1. Change the insertion order to descending, and then to a fixed shuffled order.
   Check that the inorder sequence stays the same, even though the exact shape
   may differ.
2. Search for a missing key. The search still follows a single path from the
   root to `null`.
3. Remove every key, checking `invariantsHold()` after each removal, and then
   insert again.
4. Use a reverse comparator and see how `first`, `last` and the traversal
   change.
5. Try strings with `String.CASE_INSENSITIVE_ORDER`: `"Oak"` and `"OAK"` count
   as the same comparison key, although `equals` tells them apart.
6. Explain why neither a balanced height nor sorted iteration requires the
   elements to be inserted in sorted order.

## Validation

The tests compare the contents, return values, endpoints and ordering with those
of a `TreeSet`. After every update they check the structural invariants of the
tree, the node count and a logarithmic bound on the height, independently of the
tree's own code. The cases include every insertion order of six keys followed by
deletion in forward and reverse order, monotone sequences, deletion of the
minimum and the maximum, seeded sequences of mixed operations, reverse order,
equivalent duplicates, extreme integer values, invalid input, iterator
exhaustion and iterators invalidated by updates. The JCF class is only a test
oracle; it does not store this implementation's data.

The code uses Java 17, Allman braces, four spaces per indentation level and
braces around every loop body.

## Colour rules and this variant (optional)

The standard red-black properties are these: every node is red or black; the
root is black; missing children are black NIL leaves; a red node has no red
child; and every path from a node down to a NIL leaf contains the same number of
black nodes. Together, these conditions bound the height by O(log n). As in the
AVL example, the ordinary height still counts nodes.

`ds.RedBlackTree<E>` implements the **left-leaning red-black (LLRB)** variant,
in which red links lean left in the finished tree. `null` references stand for
black NIL leaves, and the boolean colour of a node describes the link coming
into it. This is an extra convention of the representation, not a requirement
for every red-black tree.

Insertion adds a red node and then repairs right-leaning red links, consecutive
red links and temporary nodes with two red children. Deletion prepares the child
subtree before descending into it, moving a red link towards the position being
deleted so that removing a node does not lose a black level. The method compares
again after rotations, because the root of the subtree may have changed. On the
way back, `repair` restores the left-leaning representation, and the public
operation makes the final root black.

`audit` checks the BST ordering, that the root is black, that there are no red
right links and no consecutive red nodes, that the black counts are equal, and
the total number of nodes. Internally it counts both the current black node and
the NIL leaf; this convention of the helper is documented, and it does not
change the definition of black height in the slides, which excludes the starting
node.

## What Java actually uses

The Java 17 documentation describes **`TreeMap` as a red-black tree
implementation** and **`TreeSet` as based on `TreeMap`**. The claim therefore
applies to these ordered JCF implementations, not to the whole Java Collections
Framework; `ArrayList`, for example, is not a tree. The JCF never asks clients
to implement rotations or recolouring.

The last few lines of `app.Main` give a brief preview of Topic 4: a set stores
unique elements, while a map associates keys with values. Inserting 30, 10 and
20 shows that both library examples iterate in key order. You do not need the
topic's full discussion of collections, sets and maps to study our tree.

Our class is an independent implementation and is deliberately small. Its
left-leaning variant is **not presented as the exact internal algorithm of
`TreeMap`**, and it does not claim to provide the full `Set`, `Map` or navigable
collection API.

References for the claims about the library:

- [Java 17 TreeMap
  documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html).
- [Java 17 TreeSet
  documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html).
- [Balanced search trees, Princeton
  Algorithms](https://algs4.cs.princeton.edu/33balanced/): optional background
  on the LLRB approach.

## Expected output

```text
Inserted 1 through 15 in ascending order.
Tree structure (right above, left below):

                /-- 15 [B]
                |
        /-- 14 [B]
        |       |
        |       \-- 13 [B]
        |
/-- 12 [B]
|       |
|       |       /-- 11 [B]
|       |       |
|       \-- 10 [B]
|               |
|               \-- 9 [B]
|
+-- 8 [ROOT] [B]
|
|               /-- 7 [B]
|               |
|       /-- 6 [B]
|       |       |
|       |       \-- 5 [B]
|       |
\-- 4 [B]
        |
        |       /-- 3 [B]
        |       |
        \-- 2 [B]
                |
                \-- 1 [B]

Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Before duplicate: size = 15
Adding duplicate 8: false
After duplicate: size = 15; invariants hold = true

============================================================
Removing 8: true
State: size = 14; height = 5; contains(8) = false; invariants hold = true

        /-- 15 [B]
        |
/-- 14 [B]
|       |
|       |       /-- 13 [B]
|       |       |
|       \-- 12 [R]
|               |
|               \-- 11 [B]
|                       |
|                       \-- 10 [R]
|
+-- 9 [ROOT] [B]
|
|               /-- 7 [B]
|               |
|       /-- 6 [B]
|       |       |
|       |       \-- 5 [B]
|       |
\-- 4 [R]
        |
        |       /-- 3 [B]
        |       |
        \-- 2 [B]
                |
                \-- 1 [B]


============================================================
Removing 1: true
State: size = 13; height = 5; contains(1) = false; invariants hold = true

        /-- 15 [B]
        |
/-- 14 [B]
|       |
|       |       /-- 13 [B]
|       |       |
|       \-- 12 [R]
|               |
|               \-- 11 [B]
|                       |
|                       \-- 10 [R]
|
+-- 9 [ROOT] [B]
|
|       /-- 7 [B]
|       |
\-- 6 [B]
        |
        |       /-- 5 [B]
        |       |
        \-- 4 [R]
                |
                \-- 3 [B]
                        |
                        \-- 2 [R]


============================================================
Removing 15: true
State: size = 12; height = 5; contains(15) = false; invariants hold = true

        /-- 14 [B]
        |       |
        |       \-- 13 [R]
        |
/-- 12 [B]
|       |
|       \-- 11 [B]
|               |
|               \-- 10 [R]
|
+-- 9 [ROOT] [B]
|
|       /-- 7 [B]
|       |
\-- 6 [B]
        |
        |       /-- 5 [B]
        |       |
        \-- 4 [R]
                |
                \-- 3 [B]
                        |
                        \-- 2 [R]

After removals: [2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14]
Height after removals: 5
First / last: 2 / 14
All invariants hold: true
TreeSet ordered contents: [10, 20, 30]
TreeMap ordered keys and values: {10=item-10, 20=item-20, 30=item-30}
Java 17: TreeMap uses a red-black tree; TreeSet is based on TreeMap.
Our LLRB implementation is not a copy of TreeMap's internal algorithm.
After clear: size=0, height=0

(empty)

Focus: purpose and logarithmic costs. Repair code is not required for the exam.
```

## Reading the text tree

The binary tree is printed sideways: the right subtree is above its parent and
the left subtree below it. `[ROOT]` marks the root. `/--` connects a right child
above its parent, and `\--` connects a left child below it. Vertical `|` lines
continue the branches of ancestors. Read the output in a monospaced font. Empty
child links are left out, and an empty tree is shown as `(empty)`. Red-black
labels include `[R]` for red and `[B]` for black. Missing children are implicit
black NIL leaves.

`toTreeString()` returns text, which `Main` prints with `System.out.print`; it
does not change the tree. The ordered traversal is still available. Labels that
contain line breaks or tabs are escaped, so that each node takes up one line.
The diagrams show the actual links, not a reconstruction from the sorted values,
and they show completed operations, not intermediate rotation states. They are
meant for small classroom trees: for a long chain, the indentation can make the
output quadratic in the height.

Each diagram is separated by blank lines from its heading and from the output
that follows. Removal scenarios have a horizontal separator. Binary diagrams use
wider spacing between levels, with lines that contain only connectors between a
parent level and its children.
