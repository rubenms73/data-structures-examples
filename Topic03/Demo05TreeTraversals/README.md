# Four tree traversals implemented as iterators

## Problem statement

Build the same binary search tree as Demo01 and visit its nodes in four orders:
preorder, inorder, postorder and level order. Implement each traversal as a private
inner class implementing `Iterator<E>`. Display the actual tree with ASCII branches,
then compare the four sequences without changing its links between traversals.

This folder is self-contained. Its `ds.BinarySearchTree` extends the Demo01 code
with three new traversal algorithms and an explicitly named inorder factory.
Insertion, deletion, duplicate handling and the ASCII view retain their behaviour.
The tree is deliberately unbalanced so its shape stays easy to relate to insertion
order. These traversal algorithms apply equally to balanced binary trees: balance
changes height and shape, not the meaning of a traversal.

## Prerequisites

Topic 1's iterator contract, inner classes and generics; stacks and queues from
Topic 2; and Topic 3's binary trees, left/right children and traversal orders.
`ArrayDeque` supplies stack or queue storage. It does not perform the traversal.
Read `app.Main` first, then each iterator's constructor, `hasNext()` and `next()`.

## Four orders on one tree

| Traversal | Rule | Factory | Pending work |
| --- | --- | --- | --- |
| Preorder | Root, left subtree, right subtree | `preOrderIterator()` | Stack of subtree roots |
| Inorder | Left subtree, root, right subtree | `inOrderIterator()` | Stack of ancestors awaiting a visit |
| Postorder | Left subtree, right subtree, root | `postOrderIterator()` | One stack holding a path of pending ancestors |
| Level order | Depth 0, depth 1, depth 2, and so on; left to right | `levelOrderIterator()` | FIFO queue |

`iterator()` remains inorder, so enhanced `for`, collection text output and the
inherited collection operations retain their existing behaviour. The named factory
methods return `Iterator<E>`, not `Iterable<E>`; consume them with a `while` loop.
Every factory creates a new independent cursor. The last part of `Main` advances
a preorder iterator and a level-order iterator alternately to demonstrate this.

All four visit every stored occurrence, including duplicate labels. Only inorder
is guaranteed to be sorted according to the tree's comparator. Preorder is useful
when a parent must be handled before its children; postorder when child results
are needed before their parent; level order when processing by depth. Inorder's
usual left-root-right definition is specific to binary trees, unlike the general
ordered tree in Demo02.

## How the iterators work

### Preorder

The constructor puts the root on a stack if one exists. `next()` pops one node,
pushes its right child and then its left child, and returns the node's value.
A stack is LIFO, so pushing right first makes the left subtree get visited first.
No null reference is pushed into `ArrayDeque`.

### Inorder

The constructor pushes the root's leftmost path. The stack top is the next node.
`next()` pops it and pushes the leftmost path of its right subtree. The stack holds
only nodes whose own visit is still pending. This is the iterator from Demo01.

### Postorder

The constructor follows a path towards the first leaf: prefer the left child;
if none exists, follow the right. Each node on that path goes onto one stack.
`next()` pops a completed node. If it was its parent's left child and the parent
also has a right subtree, the iterator pushes the first path through that right
subtree. Otherwise the parent is ready to be returned on a later call.

The test `parent.left == node` compares **node identity**, not labels. Equal labels
can belong to distinct nodes. No second stack of all results and no recursive
collection of the complete traversal are needed. The iterator keeps the explicit
state that recursive postorder would otherwise leave on the call stack.

### Level order

The constructor queues the root if one exists. `next()` removes the front node
and appends its left and right children at the back. Nodes already waiting at the
current depth are processed before the newly appended next-depth nodes. This is
the difference between a FIFO queue and the stack used in depth-first traversals.

## Iterator contract and changes to the tree

- Empty trees yield four empty iterators.
- Repeated calls to `hasNext()` never advance a cursor.
- `next()` throws `NoSuchElementException` after exhaustion, including repeated calls.
- `remove()` is unsupported; the default method from `Iterator` throws
  `UnsupportedOperationException` in all four implementations.
- Iterators retain references to actual nodes and return stored element references;
  they are not snapshots or deep copies.
- As in Demo01, **do not structurally modify the tree while any iterator is in use**.
  There is no fail-fast modification counter in this example. Mutating the tree
  during traversal has no supported result. After a change, obtain a new iterator.

The ordinary tree contract remains unchanged: null elements are rejected, comparison
can be natural or supplied through `Comparator<? super E>`, and duplicate occurrences
are placed on the left. Do not mutate fields that participate in the comparison.

## Costs

Let n be the node count, h the node-count height and w the maximum width.
Each complete traversal takes O(n) time. Every node is added to and removed from
its pending-work container once; none of the iterators materialises the full result.

| Iterator | Construction | `hasNext()` | `next()` | Auxiliary storage |
| --- | --- | --- | --- | --- |
| Preorder | O(1) | O(1) | Amortized O(1) | O(h) |
| Inorder | O(h) | O(1) | O(h) worst case; amortized O(1) over a full traversal | O(h) |
| Postorder | O(h) | O(1) | O(h) worst case; amortized O(1) over a full traversal | O(h) |
| Level order | O(1) | O(1) | Amortized O(1) | O(w) |

`ArrayDeque` can occasionally resize; its stack/queue operations have amortized
constant cost. An inorder or postorder call may push a whole path, although the
average work per returned element over a complete traversal is O(1), including
initialisation for a nonempty tree. This unbalanced tree can have h = n. A wide
tree can have w proportional to n, so a level-order queue can be much larger than
a depth-first stack on a balanced tree. The recursive test oracles are not the
iterator implementation and are not used by the demo.

## Run

Use Java 17 or newer. Open this individual folder in VS Code and run/debug
`src/app/Main.java`, or use a terminal in this folder:

```sh
bash run.sh
bash run.sh test
```

Windows PowerShell or Command Prompt, without Bash or WSL:

```powershell
.\run.cmd
.\run.cmd test
```

From Topic03, select `Demo05TreeTraversals` with the topic launcher. The console
shows a spaced sideways tree: right subtrees above their parent, left below;
`[ROOT]` identifies the root. The printed order of that diagram is a drawing aid,
not one of the four traversal sequences shown afterwards.

## Experiments and validation

Predict the four sequences before running the program. Then try a single node,
a chain of left children, a chain of right children and a tree with repeated
labels. Explain why preorder and level order agree initially but later differ.
Call `hasNext()` twice before every `next()` and confirm that no elements disappear.

Tests retain Demo01's insertion/deletion checks and compare the new iterators with
independent recursive reference traversals. The breadth-first oracle collects one
depth at a time rather than using the implementation's queue algorithm. Tests cover
empty and singleton trees, asymmetric shapes, chains, duplicates, seeded random
trees, iterator independence, repeated `hasNext()`, exhaustion and unsupported removal.

## Expected output

```text
Same tree, four traversal orders (right subtree above):

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

Preorder (root, left, right): 8 -> 3 -> 1 -> 6 -> 4 -> 7 -> 10 -> 14 -> 13
Inorder (left, root, right): 1 -> 3 -> 4 -> 6 -> 7 -> 8 -> 10 -> 13 -> 14
Postorder (left, right, root): 1 -> 4 -> 7 -> 6 -> 3 -> 13 -> 14 -> 10 -> 8
Level order (breadth first): 8 -> 3 -> 10 -> 1 -> 6 -> 14 -> 4 -> 7 -> 13
The default iterator is inorder: [1, 3, 4, 6, 7, 8, 10, 13, 14]

Independent iterators, advanced alternately:
preorder=8, level order=8
preorder=3, level order=3
preorder=1, level order=10
Iterator creation does not materialise the complete traversal.
```
