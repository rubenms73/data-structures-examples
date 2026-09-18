# Red-black trees and the Java Collections Framework

A red-black tree is a balanced binary search tree that uses colour rules to
bound its height. It supports ordered data with logarithmic worst-case lookup,
insertion and deletion. Colours guide internal repairs; client code supplies
keys and an ordering, not colours or rotations.

## Problem statement

Build a balanced binary search tree with insertion, lookup, deletion and inorder
iteration. Insert the keys 1 through 15 in ascending order, inspect the height,
and count the comparisons needed to find 15. Reject a duplicate, remove a key
with two children and the smallest/largest keys, then check the resulting order
and balance. Finally empty the tree.

The balancing algorithms are fully implemented, not simulated and not delegated
to a JCF container. The program counts actual comparator calls during lookup;
it does not use wall-clock timings. The plain-BST height of 15 is a theoretical
comparison for this insertion sequence, not a timed second implementation.

## Teaching scope

**Required focus:** why a plain BST can become a chain, why keeping the height
logarithmic is useful, and how that affects search and update costs. Read `app.Main`
and observe the output before opening the repair methods.

**Optional implementation study, not required for the exam:** rotation cases,
recolouring where applicable, recursive repair and the invariant-checking code.
These methods are included so the example really works and interested students
can trace it. The detailed properties below explain the implementation; they do
not add a requirement to reproduce its algorithms in the exam.

Prerequisites are Topic 1 generics, comparators and iterators, and Topic 3 BST
ordering and inorder traversal. A `Comparator<? super E>` is passed explicitly;
`Comparator.naturalOrder()` selects natural order and `Comparator.reverseOrder()`
selects reverse order. Private inner classes represent nodes and the iterator.
The iterator uses `ArrayDeque` as a stack, as in the earlier BST example.

## Run

Open this individual folder in VS Code with the Java Extension Pack and JDK 17.
Run or debug `src/app/Main.java`. From the same folder:

```sh
bash run.sh
bash run.sh test
```

Windows, from Command Prompt or PowerShell, without Bash or WSL:

```powershell
.\run.cmd
.\run.cmd test
```

Each example includes its own `src`, `tests`, VS Code settings and launchers.
No dependency on another demo, external JAR, internet connection or preview Java
feature is needed. The topic launcher also lists and runs this example.

## Public operations and contracts

The class implements `Iterable<E>`, not `Collection<E>` or `Set<E>`. This keeps
the public contract small; collection interfaces are developed in Topic 4.

- `add(value)` returns true only when a new comparison key is inserted.
- `remove(value)` returns true only when an existing comparison key is removed.
- `contains(value)` performs an ordinary BST search, following one child at a time.
- `first()` and `last()` use comparator order, which need not be numeric ascending order.
  Both throw `NoSuchElementException` on an empty tree.
- `size()`, `isEmpty()`, `clear()` and `height()` describe or reset the container.
- `iterator()` visits the elements in comparator order. `next()` throws
  `NoSuchElementException` after exhaustion; iterator `remove()` is unsupported.
- `invariantsHold()` is an O(n) diagnostic used by the demo and tests, not an
  additional full-tree traversal performed during every normal update.

A null comparator or a null element in `add`, `contains` or `remove` produces
`NullPointerException`, using an explicit conditional. Comparison result zero
identifies an existing key. Adding such a key keeps the original stored element;
`equals` and `hashCode` are not used. The comparator must provide a consistent,
stable total order. Stored objects are references, not deep copies: do not modify
fields used for ordering while an element is in the tree. The tree is not thread-safe.

Unlike Demo01, which retains duplicate occurrences on the left, these examples
store **one element per comparison key**. Rotations can move an equal key across
its parent, so the earlier one-sided duplicate rule cannot simply be reused.
A frequency field per node would be one way to support repeated occurrences;
that extension is not part of these examples.

Successful insertion, removal and clearing a nonempty tree invalidate existing
iterators. `hasNext()` and `next()` then throw `ConcurrentModificationException`.
Adding a duplicate, removing an absent key, or clearing an already empty tree
makes no structural change and leaves existing iterators valid. Fail-fast checking
is a debugging aid, not a concurrency guarantee.

## Costs and height convention

Height counts **nodes**, following the Topic 3 slides: empty = 0, leaf = 1.
With constant-time comparisons, lookup, insertion, deletion, first and last have
O(log n) worst-case cost for a nonempty tree. Small/empty cases take constant time.
The preliminary membership search in `add`/`remove` keeps duplicate and absent
operations simple and unchanged; a second descent only changes a constant factor.

Size and emptiness are O(1). Clearing drops the root reference in O(1), excluding
later garbage collection. Traversing all elements and checking invariants take
O(n); storage for nodes is O(n), and traversal/recursive-update auxiliary storage
is O(log n). Iterator creation and a single worst-case `next()` take O(log n);
a complete traversal takes O(n). Text output also depends on element string lengths.

`height()` is an O(n) diagnostic here: it measures the current links recursively.
Lookup and updates do not call it and retain their O(log n) bounds.

## Guided experiments

1. Change insertion order to descending, then to a fixed shuffled order. Check
   that the inorder sequence is the same while the exact shape may differ.
2. Search for a missing key. It still follows one root-to-null path.
3. Remove every key, checking `invariantsHold()` after each removal, then insert again.
4. Use a reverse comparator and observe how `first`, `last` and traversal change.
5. Try strings with `String.CASE_INSENSITIVE_ORDER`: `"Oak"` and `"OAK"` count as
   the same comparison key, although their `equals` result differs.
6. Explain why neither a balanced height nor sorted iteration requires elements
   to be inserted in sorted order.

## Validation

Tests compare contents, return values, endpoints and ordering against `TreeSet`.
After every update they independently audit the tree's structural invariants,
node count and a logarithmic height bound. Cases include all insertion permutations
of six keys with forward and reverse deletion, monotone sequences, minimum/maximum
deletion, seeded mixed operations, reverse order, duplicate equivalence, integer
extremes, invalid input, iterator exhaustion and iterator invalidation.
The JCF reference is only a test oracle; it does not store this implementation's data.

Code uses Java 17, Allman braces, four spaces and braces around every loop.


## Colour rules and this variant (optional)

The standard red-black properties are: every node is red or black; the root is
black; missing children are black NIL leaves; a red node has no red child; and
all paths from a node down to NIL leaves contain the same number of black nodes.
Together these conditions bound the height by O(log n). Ordinary height still
counts nodes, just as in the AVL example.

`ds.RedBlackTree<E>` implements the **left-leaning red-black (LLRB)** variant:
red links lean left in the finished tree. Null references stand for black NIL
leaves, and a node's boolean colour describes its incoming link. This is an extra
representation convention, not a requirement imposed on every red-black tree.

Insertion adds a red node, then repairs right-leaning red links, consecutive red
links and temporary nodes with two red children. Deletion prepares the child
subtree before descending, moving a red link towards the deletion position so
that removing a node does not lose a black level. The method compares again after
rotations because the subtree root may have changed. On return, `repair` restores
the left-leaning representation; the public operation makes the final root black.

`audit` checks BST ordering, a black root, no red right link, no consecutive red
nodes and equal black counts, as well as the total node count. Internally it counts
the current black node and the NIL leaf; that helper convention is documented and
does not change the slides' black-height definition, which excludes the starting node.

## What Java actually uses

Java 17 documents **`TreeMap` as a red-black-tree implementation** and **`TreeSet`
as based on `TreeMap`**. Thus the claim applies to those ordered JCF implementations,
not to the whole Java Collections Framework. For example, `ArrayList` is not a tree.
The JCF does not require clients to implement rotations or recolouring.

The final few lines of `app.Main` are a brief preview of Topic 4: a set stores
unique elements, while a map associates keys with values. Inserting 30, 10 and 20
shows that both library examples iterate by key order. The topic's full discussion
of collections, sets and maps is not a prerequisite for studying our tree.

Our class is independently implemented and deliberately small. Its left-leaning
variant is **not presented as TreeMap's exact internal algorithm**, and it does
not claim the full `Set`, `Map` or navigable collection API.

References for the library claims:

- [Java 17 TreeMap documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeMap.html).
- [Java 17 TreeSet documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/TreeSet.html).
- [Balanced search trees, Princeton Algorithms](https://algs4.cs.princeton.edu/33balanced/): optional background on the LLRB approach.

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
Adding duplicate 8: false

============================================================
Removing 8: true

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

The binary tree is printed sideways: the right subtree is above its parent and the left subtree below it. `[ROOT]` marks the root. `/--` connects a right child above its parent; `\--` connects a left child below it. Vertical `|` lines continue ancestor branches. Read the output with a monospaced font. Empty child links are omitted and an empty tree is shown as `(empty)`. Red-black labels include `[R]` for red and `[B]` for black. Missing children are implicit black NIL leaves.

`toTreeString()` returns text; `Main` prints it with `System.out.print`. It does not
change the tree. The existing ordered traversal remains available. Labels with
line breaks or tabs are escaped so each node occupies one line. These are views
of the actual links, not reconstructions from sorted values. They show completed
operations, not intermediate rotation states. Intended for small classroom trees;
indentation can make output quadratic in the height of a long chain.

Each diagram is separated from its heading and following output by blank lines.
Removal scenarios have a horizontal separator. Binary diagrams use wider level
spacing and connector-only lines between parent and child levels.
