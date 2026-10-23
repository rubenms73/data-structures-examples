# Four tree traversals implemented as iterators

## Problem statement

Build the same binary search tree as in Demo01 and visit its nodes in four
orders: preorder, inorder, postorder and level order. Implement each traversal
as a private inner class that implements `Iterator<E>`. Display the actual tree
with ASCII branches, and then compare the four sequences without changing the
links of the tree between traversals.

This folder is self-contained. Its `ds.BinarySearchTree` extends the Demo01 code
with three new traversal algorithms and a factory method that names the inorder
traversal explicitly. Insertion, deletion, the handling of duplicates and the
ASCII view behave as before. The tree is deliberately unbalanced, so that its
shape is easy to relate to the insertion order. The same traversal algorithms
work just as well on balanced binary trees: balancing changes the height and the
shape, not what a traversal means.

## Prerequisites

You need the iterator contract, inner classes and generics from Topic 1, stacks
and queues from Topic 2, and binary trees, left and right children and the
traversal orders from Topic 3. `ArrayDeque` provides the storage for the stack
or the queue; it does not perform the traversal itself. Read `app.Main` first,
and then the constructor, `hasNext()` and `next()` of each iterator.

## Four orders on one tree

| Traversal | Rule | Factory | Pending work |
| --- | --- | --- | --- |
| Preorder | Root, left subtree, right subtree | `preOrderIterator()` | Stack of subtree roots |
| Inorder | Left subtree, root, right subtree | `inOrderIterator()` | Stack of ancestors awaiting a visit |
| Postorder | Left subtree, right subtree, root | `postOrderIterator()` | One stack holding a path of pending ancestors |
| Level order | Depth 0, depth 1, depth 2, and so on; left to right | `levelOrderIterator()` | FIFO queue |

`iterator()` is still the inorder traversal, so the enhanced `for` loop, the
text output of the collection and the inherited collection operations behave as
before. The named factory methods return an `Iterator<E>`, not an `Iterable<E>`,
so consume them with a `while` loop. Each call to a factory creates a new,
independent cursor; the last part of `Main` shows this by advancing a preorder
iterator and a level-order iterator alternately.

All four traversals visit every stored occurrence, duplicate labels included.
Only the inorder traversal is guaranteed to follow the order of the tree's
comparator. Preorder is useful when a parent must be handled before its
children, postorder when the results for the children are needed before their
parent, and level order when nodes are processed depth by depth. The usual
left–root–right definition of inorder only makes sense for binary trees, unlike
the general ordered tree in Demo02.

## How the iterators work

### Preorder

The constructor puts the root on a stack, if there is one. `next()` pops a node,
pushes its right child and then its left child, and returns the node's value.
Because a stack is LIFO, pushing the right child first means that the left
subtree is visited first. A `null` reference is never pushed onto the
`ArrayDeque`.

### Inorder

The constructor pushes the leftmost path from the root, so the top of the stack
is always the next node. `next()` pops it and pushes the leftmost path of its
right subtree. The stack holds only nodes that are still waiting for their own
visit. This is the iterator from Demo01.

### Postorder

The constructor follows a path down to the first leaf, going to the left child
when there is one and to the right child otherwise, and pushes each node on that
path onto a single stack. `next()` pops a node whose subtrees are finished. If
that node was its parent's left child and the parent also has a right subtree,
the iterator pushes the first path through that right subtree. Otherwise, the
parent is ready to be returned on a later call.

The test `parent.left == node` compares **node identity**, not labels, because
equal labels can belong to different nodes. The iterator needs neither a second
stack holding all the results nor a recursive collection of the whole traversal.
It keeps, explicitly, the state that a recursive postorder would leave on the
call stack.

### Level order

The constructor puts the root in the queue, if there is one. `next()` removes
the node at the front and appends its left and right children at the back. The
nodes already waiting at the current depth are therefore processed before the
nodes just added for the next depth. This is the difference between a FIFO queue
and the stack used in the depth-first traversals.

## Iterator contract and changes to the tree

- An empty tree gives four empty iterators.
- Calling `hasNext()` repeatedly never advances a cursor.
- `next()` throws `NoSuchElementException` once the iterator is exhausted,
  however many times it is called.
- `remove()` is not supported: in all four implementations, the default method
  from `Iterator` throws `UnsupportedOperationException`.
- The iterators hold references to the actual nodes and return the stored
  element references; they are neither snapshots nor deep copies.
- As in Demo01, **do not change the structure of the tree while any iterator is
  in use**. There is no fail-fast modification counter in this example, and
  modifying the tree during a traversal has no supported result. After a change,
  obtain a new iterator.

The ordinary tree contract is unchanged: `null` elements are rejected, the
comparison can be the natural order or a `Comparator<? super E>` supplied by the
client, and duplicate occurrences go to the left. Do not modify fields that take
part in the comparison.

## Costs

Let n be the number of nodes, h the height (counted in nodes) and w the maximum
width. Each complete traversal takes O(n) time. Every node is added to its
container of pending work once and removed from it once, and none of the
iterators builds the full result.

| Iterator | Construction | `hasNext()` | `next()` | Auxiliary storage |
| --- | --- | --- | --- | --- |
| Preorder | O(1) | O(1) | Amortized O(1) | O(h) |
| Inorder | O(h) | O(1) | O(h) worst case; amortized O(1) over a full traversal | O(h) |
| Postorder | O(h) | O(1) | O(h) worst case; amortized O(1) over a full traversal | O(h) |
| Level order | O(1) | O(1) | Amortized O(1) | O(w) |

`ArrayDeque` occasionally resizes, so its stack and queue operations take
amortised constant time. A single inorder or postorder call may push a whole
path, but over a complete traversal the average work per returned element is
O(1), including the initialisation for a non-empty tree. This unbalanced tree
can have h = n. A wide tree can have w proportional to n, so on a balanced tree
the level-order queue can be much larger than a depth-first stack. The recursive
test oracles are not part of the iterator implementation, and the demo does not
use them.

## Run

Use Java 17 or newer. Open this folder on its own in VS Code and run or debug
`src/app/Main.java`, or use a terminal in this folder:

```sh
bash run.sh
bash run.sh test
```

On Windows, from PowerShell or Command Prompt, without Bash or WSL:

```powershell
.\run.cmd
.\run.cmd test
```

From Topic03, choose `Demo05TreeTraversals` with the topic launcher. The console
shows the tree sideways and spaced out, with right subtrees above their parent
and left subtrees below it; `[ROOT]` marks the root. The order in which the
diagram is printed is only a drawing aid, not one of the four traversal sequences
shown after it.

## Experiments and validation

Predict the four sequences before you run the program. Then try a single node, a
chain of left children, a chain of right children and a tree with repeated
labels. Explain why preorder and level order agree at first but later differ.
Call `hasNext()` twice before every `next()` and check that no elements go
missing.

The tests keep the insertion and deletion checks from Demo01 and compare the new
iterators with separate recursive reference traversals. The breadth-first oracle
collects one depth at a time instead of using the queue algorithm of the
implementation. The tests cover empty and single-node trees, asymmetric shapes,
chains, duplicates, seeded random trees, independent iterators, repeated calls
to `hasNext()`, exhaustion and the unsupported removal.

## Expected output

```text
Insertion order: [8, 3, 10, 1, 6, 14, 4, 7, 13]
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
After all traversals: [1, 3, 4, 6, 7, 8, 10, 13, 14]; size = 9
```
