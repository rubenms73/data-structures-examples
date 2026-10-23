# AVL trees and logarithmic height

An AVL tree is a binary search tree in which, at every node, the heights of the
two subtrees differ by at most one. This limits the length of the longest search
path even when the keys arrive in an awkward order. Typical uses are ordered
in-memory indexes and dictionaries, where a predictable logarithmic cost for
lookups and updates matters.

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

`height()` is O(1), because it uses the height cached at the root.

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

## How this implementation keeps balance (optional)

`ds.AVLTree<E>` stores a height in every private node. As in the slides, its
balance factor is `height(left) - height(right)`. After every completed
operation, every factor is in {-1, 0, 1}. Empty children have height zero.

`insert` and `delete` first do the ordinary BST work. As the recursion returns
towards the root, `rebalance` recalculates the heights and repairs any
imbalance. To delete a node with two children, the implementation replaces its
value with its inorder successor and removes the node where the successor used
to be. After a deletion, repairs may be needed at several ancestors.

| Local imbalance | Child shape | Repair |
| --- | --- | --- |
| Left side too tall | Left child leans left or is balanced | Right rotation |
| Left side too tall | Left child leans right | Left rotation at child, then right at parent |
| Right side too tall | Right child leans right or is balanced | Left rotation |
| Right side too tall | Right child leans left | Right rotation at child, then left at parent |

A rotation changes local links while keeping the inorder sequence. It keeps the
middle subtree attached, and it updates the height of the former root before
that of its new parent. A heavy child with balance factor zero can arise during
deletion, and the implementation correctly uses a single rotation for it.

`audit` recomputes the heights from the actual links instead of trusting the
cached values. It checks the strict comparator order, every balance factor and
every stored height, so the tests would detect a forgotten height update.

For the same number of keys, AVL trees impose a stricter height-balance
condition than red-black trees. That does not make them faster in every case:
the workload, the cost of comparisons and the overhead of updates all matter.
The course example demonstrates the structure and the comparison counts; it is
not a general performance benchmark. Java 17 has no public `AVLTree` collection
class in `java.util`.

## Expected output

```text
Inserted 1 through 15 in ascending order.
Tree structure (right above, left below):

                /-- 15 [h=1, bf=0]
                |
        /-- 14 [h=2, bf=0]
        |       |
        |       \-- 13 [h=1, bf=0]
        |
/-- 12 [h=3, bf=0]
|       |
|       |       /-- 11 [h=1, bf=0]
|       |       |
|       \-- 10 [h=2, bf=0]
|               |
|               \-- 9 [h=1, bf=0]
|
+-- 8 [ROOT] [h=4, bf=0]
|
|               /-- 7 [h=1, bf=0]
|               |
|       /-- 6 [h=2, bf=0]
|       |       |
|       |       \-- 5 [h=1, bf=0]
|       |
\-- 4 [h=3, bf=0]
        |
        |       /-- 3 [h=1, bf=0]
        |       |
        \-- 2 [h=2, bf=0]
                |
                \-- 1 [h=1, bf=0]

Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Before duplicate: size = 15
Adding duplicate 8: false
After duplicate: size = 15; invariants hold = true

============================================================
Removing 8: true
State: size = 14; height = 4; contains(8) = false; invariants hold = true

                /-- 15 [h=1, bf=0]
                |
        /-- 14 [h=2, bf=0]
        |       |
        |       \-- 13 [h=1, bf=0]
        |
/-- 12 [h=3, bf=0]
|       |
|       |       /-- 11 [h=1, bf=0]
|       |       |
|       \-- 10 [h=2, bf=-1]
|
+-- 9 [ROOT] [h=4, bf=0]
|
|               /-- 7 [h=1, bf=0]
|               |
|       /-- 6 [h=2, bf=0]
|       |       |
|       |       \-- 5 [h=1, bf=0]
|       |
\-- 4 [h=3, bf=0]
        |
        |       /-- 3 [h=1, bf=0]
        |       |
        \-- 2 [h=2, bf=0]
                |
                \-- 1 [h=1, bf=0]


============================================================
Removing 1: true
State: size = 13; height = 4; contains(1) = false; invariants hold = true

                /-- 15 [h=1, bf=0]
                |
        /-- 14 [h=2, bf=0]
        |       |
        |       \-- 13 [h=1, bf=0]
        |
/-- 12 [h=3, bf=0]
|       |
|       |       /-- 11 [h=1, bf=0]
|       |       |
|       \-- 10 [h=2, bf=-1]
|
+-- 9 [ROOT] [h=4, bf=0]
|
|               /-- 7 [h=1, bf=0]
|               |
|       /-- 6 [h=2, bf=0]
|       |       |
|       |       \-- 5 [h=1, bf=0]
|       |
\-- 4 [h=3, bf=0]
        |
        |       /-- 3 [h=1, bf=0]
        |       |
        \-- 2 [h=2, bf=-1]


============================================================
Removing 15: true
State: size = 12; height = 4; contains(15) = false; invariants hold = true

        /-- 14 [h=2, bf=1]
        |       |
        |       \-- 13 [h=1, bf=0]
        |
/-- 12 [h=3, bf=0]
|       |
|       |       /-- 11 [h=1, bf=0]
|       |       |
|       \-- 10 [h=2, bf=-1]
|
+-- 9 [ROOT] [h=4, bf=0]
|
|               /-- 7 [h=1, bf=0]
|               |
|       /-- 6 [h=2, bf=0]
|       |       |
|       |       \-- 5 [h=1, bf=0]
|       |
\-- 4 [h=3, bf=0]
        |
        |       /-- 3 [h=1, bf=0]
        |       |
        \-- 2 [h=2, bf=-1]

After removals: [2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14]
Height after removals: 4
First / last: 2 / 14
All invariants hold: true
After clear: size=0, height=0

(empty)

Focus: purpose and logarithmic costs. Repair code is not required for the exam.
```

## Reading the text tree

The binary tree is printed sideways: the right subtree is above its parent and
the left subtree below it. `[ROOT]` marks the root. `/--` connects a right child
above its parent, and `\--` connects a left child below it. Vertical `|` lines
continue the branches of ancestors. Read the output in a monospaced font. Empty
child links are left out, and an empty tree is shown as `(empty)`. AVL labels
include `h` (the height stored in the node) and `bf` (the height of the left
subtree minus that of the right).

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
