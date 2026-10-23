# 01 — Binary search tree

## Problem statement and prerequisites

Implement an unbalanced generic binary search tree that supports insertion,
search, removal, clearing and in-order iteration. Use an optional
`Comparator<? super E>`, or the natural order of the elements. As in Topic 3,
keep elements that compare as equivalent as separate nodes.

You should already know generic types, `Comparable` and `Comparator`, abstract
classes, binary trees, recursion, inner iterators and stacks. The iterator's
stack is an `ArrayDeque`, whose `push` and `pop` work on the same end.

## Representation and contracts

Each private inner `Node` holds a value and references to its left and right
children. Every value in the left subtree compares as smaller than or equal to
the node's value, and every value in the right subtree compares as strictly
larger. “Smaller” and “larger” refer to the chosen ordering, which may be a
reverse order. `root == null` represents the empty tree, and `size` counts the
stored nodes.

The default constructor uses natural order, and the comparator constructor also
selects natural order when it receives `null`. The conversion constructors
accept a `Collection<? extends E>` and insert its elements into new nodes of
their own, although the element objects themselves are shared. A copy is not
guaranteed to keep the shape or the comparator of the source, so choose the
comparator you want explicitly.

`add`, `contains` and `remove` reject `null` elements with an explicit `if` that
throws `NullPointerException`, and a `null` source is rejected too. Without a
comparator, the elements must be mutually comparable; comparing incompatible
elements may throw `ClassCastException`. The first insertion checks that the
element can be compared before it creates a node.

Every successful `add` returns `true` and increases `size`, duplicates included.
`remove` deletes one occurrence and decreases `size` once. Iteration visits
every occurrence in non-decreasing order according to the comparator.

Two elements are comparison-equivalent when the comparison returns zero, even if
`equals` returns `false`. For example, a comparison of strings by length alone
treats `cat` and `dog` as equivalent. Choose an ordering consistent with
`equals` when you need the usual `Collection` contract based on equality; any
other ordering deliberately makes membership depend on comparison. Do not change
stored objects in ways that alter their order.

## Guided walkthrough

1. Read the constructors and `compare`. The comparator field uses `? super E`.
2. Follow `add` and `contains` from the root. Each comparison chooses one
   subtree.
3. Follow the public `remove`. It searches first, replaces `root` with the
   result of the recursive call, and then decreases `size` exactly once. The
   preliminary search keeps the recursion simple.
4. In the recursive `remove`, a node with no left child is replaced by its right
   child, and a node with no right child is replaced by its left child. Leaves
   are covered by these two cases.
5. When the node has two children, copy the maximum value of its left subtree
   and detach that predecessor node, reconnecting its left child if it has one.
   Using the successor instead could leave another equal value in the right
   subtree, which would break the strict bound on that side.
6. Follow `pushLeft` and `next`. The stack holds the nodes still waiting to be
   visited; after visiting a node, the iterator pushes the leftmost path of its
   right subtree.
7. Run `Main`. Each deletion starts from the same tree, so the cases can be
   compared.

`Main` covers removing a leaf, a node with one child, a node with two children,
the root and an absent value, as well as duplicates, reverse ordering,
independent copies and `clear`. The inherited `toString` shows the traversal
order, not the shape of the tree.

## Costs and limitations

Search, insertion and removal cost O(h), where h is the height of the tree.
Searching before removing repeats a path, but it does not change this
complexity. The tree is not balanced: following the presentation, inserting 1,
2, 3, 4, 5 produces a chain of right children of height five (five nodes and
four edges). Draw it and compare it with the insertion order 3, 1, 5, 2, 4. The
same sorted output does not mean the same shape or the same search cost.

A complete traversal costs O(n) and uses O(h) auxiliary space for the stack. A
single call to `next` may take O(h), although its amortised cost over a whole
traversal is O(1). Recursive removal also needs O(h) space on the call stack, so
it is not suitable for very deep chains. Building a tree from sorted input may
cost O(n²), and this includes copying a naturally ordered tree, through its
iterator, into another naturally ordered tree.

The iterator does not support `remove` and does not detect external changes, so
do not modify the tree while iterating over it. The inherited `removeAll`,
`retainAll` and `removeIf` may throw `UnsupportedOperationException` when they
need to remove something. `clear` is implemented explicitly by resetting `root`
and `size`. `AbstractCollection` provides useful reuse, but this is a teaching
implementation with optional operations, not a replacement for `TreeSet`.
Balancing comes later.

## What to observe and try

Remove the only element and then insert again. Remove a root that has only a
left child, and one that has only a right child. Insert 4, 3, 2, 5 and remove 4:
the predecessor is the left child itself, and it has a left child of its own.
Then try duplicate keys and check that each removal deletes exactly one
occurrence.

Try a comparator by string length: both `cat` and `dog` are kept, and either of
them can match a search for `fox` under this ordering, which is deliberately
inconsistent with equality.

## Open and run

Open this folder in VS Code with JDK 17 and Extension Pack for Java. Run
src/app/Main.java, or use:

```sh
bash run.sh
bash run.sh test
```

Sources use packages ds and app. Automated checks in tests cover boundary cases
and compare 20,000 mixed operations with a sorted list retaining duplicates in
natural and reverse order. Test-only representation checks verify the strict
right-subtree bound, which sorted output alone cannot establish.
The project has no dependencies beyond the Java standard library.

## Expected output

```text
Insertion order: [8, 3, 10, 1, 6, 14, 4, 7, 13]
Initial tree (right above, left below):

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 3
        |
        \-- 1

In order: [1, 3, 4, 6, 7, 8, 10, 13, 14]
Size: 9
Contains 6: true
Add duplicate 6: true
With the duplicate: [1, 3, 4, 6, 6, 7, 8, 10, 13, 14], size: 10

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       |       /-- 6
|       |       |       |
|       |       \-- 4
|       |
\-- 3
        |
        \-- 1

Remove one 6: true
One 6 remains: true
After removing one duplicate: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9

============================================================
Fresh copy of the same initial example: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
Remove leaf 1: true -> [3, 4, 6, 7, 8, 10, 13, 14]
Size after removal: 8; contains(1) = false

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 3


============================================================
Fresh copy of the same initial example: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
Remove node 14 with one child: true -> [1, 3, 4, 6, 7, 8, 10, 13]
Size after removal: 8; contains(14) = false

        /-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 3
        |
        \-- 1


============================================================
Fresh copy of the same initial example: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
Remove node 3 with two children: true -> [1, 4, 6, 7, 8, 10, 13, 14]
Size after removal: 8; contains(3) = false

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 1


============================================================
Fresh copy of the same initial example: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
Remove root 8: true -> [1, 3, 4, 6, 7, 10, 13, 14]
Size after removal: 8; contains(8) = false

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 7 [ROOT]
|
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 3
        |
        \-- 1


============================================================
Fresh copy of the same initial example: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
Remove absent 99: false -> [1, 3, 4, 6, 7, 8, 10, 13, 14]
Size after removal: 9; contains(99) = false

        /-- 14
        |       |
        |       \-- 13
        |
/-- 10
|
+-- 8 [ROOT]
|
|               /-- 7
|               |
|       /-- 6
|       |       |
|       |       \-- 4
|       |
\-- 3
        |
        \-- 1

Reverse order: [14, 13, 10, 8, 7, 6, 4, 3, 1]
reversed.remove(8): true; copy = [14, 13, 10, 7, 6, 4, 3, 1]
Original still contains 8: true
Ordered insertion: [1, 2, 3, 4, 5]

                        /-- 5
                        |
                /-- 4
                |
        /-- 3
        |
/-- 2
|
+-- 1 [ROOT]

After clear: [], size: 0

(empty)

```

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher sets its own working directory and copes with paths containing
spaces and with any bundled JAR libraries. It runs on Windows PowerShell 5.1,
which comes with Windows, and `run.ps1` also works with PowerShell 7. The VS Code
**Run** and **Debug** buttons are still available when the example folder is open.

## Reading the text tree

The binary tree is printed sideways: the right subtree is above its parent and
the left subtree below it. `[ROOT]` marks the root. `/--` connects a right child
above its parent, and `\--` connects a left child below it. Vertical `|` lines
continue the branches of ancestors. Read the output in a monospaced font. Empty
child links are left out, and an empty tree is shown as `(empty)`.

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
