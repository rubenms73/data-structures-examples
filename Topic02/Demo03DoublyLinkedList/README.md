# 03 — A doubly linked list with ListIterator

## Problem statement and prerequisites

Extend the singly linked implementation from Demo02 with a link to the previous
node in each node and a reference to the tail. Keep the same `ListIterator`
contract and reuse `AbstractSequentialList<E>`. The new links make moving
backwards and removing constant-time operations. Study Demo01 and Demo02 first.

## Representation and contracts

Each node stores an element, its predecessor and its successor. The `previous`
link of the first node and the `next` link of the last node are `null`. An empty
list has both `head` and `tail` equal to `null` and `count` equal to zero. For
any two adjacent nodes, following `next` and then `previous` must lead back to
the original node.

`null` elements are allowed. `null` constructor arguments and invalid cursor
indices are rejected with explicit conditionals. The conversion constructor
accepts a `Collection<? extends E>`, creates new nodes in the order of the
source and shares the element references: it is a shallow copy, not a copy of
the element objects.

## Guided walkthrough

1. Compare `Node` with Demo02: `previous` and `tail` are the additional
   references.
2. Follow the iterator constructor, which starts from the nearer end. The cursor
   may be at any position from zero to the size, inclusive; at the size,
   `nextNode` is `null`.
3. Trace `next` and `previous`. The cursor lies between `previousNode` and
   `nextNode`, and `lastReturned` identifies the node that `set` or `remove` may
   affect.
4. Follow `add`. It connects the new node to both neighbours and updates `head`
   or `tail` when inserting at either end. The cursor ends up after the inserted
   node.
5. Follow `remove` after `next` and after `previous`. Only removal after `next`
   reduces `nextIndex`. The links must be reconnected in both directions and
   both end references kept up to date.
6. Remove the only element and then insert again: both ends of the empty list
   must stay valid. Run `Main` to compare the same operations with Demo02.

## Iterator state and limitations

`set` and `remove` require a successful `next` or `previous` since the last
`add` or `remove`; otherwise they throw `IllegalStateException`. Calling `set`
repeatedly is allowed. Moving past either end throws `NoSuchElementException`.
`add` is allowed even before any movement, and it invalidates `lastReturned`. A
node that contains `null` is still a valid `lastReturned` node.

While traversing the list, modify it only through the iterator in use. This
teaching implementation does not detect changes made through other iterators or
list operations. As in Demo02, the nodes are private inner objects.

## Costs and comparison

| Operation | Demo02: singly linked | Demo03: doubly linked |
|---|---|---|
| `next`, `add`, `set` through the iterator | O(1) | O(1) |
| `previous` | O(n) worst case | O(1) |
| `remove` after `next` | O(n) worst case | O(1) |
| `remove` after `previous` | O(1) | O(1) |
| `listIterator(index)` | O(index + 1) | O(min(index, size - index) + 1) |
| Complete backward traversal | O(n²) worst case | O(n) |
| Construction from n elements | O(n) | O(n) |

`size` and the cursor queries cost O(1). Indexed access still costs O(n) in the
worst case, because backward links do not give the random access of an array.
The storage is O(n), with one extra link per node and one tail reference for the
list. An inherited append now places the iterator at the tail in O(1).

## What to observe and try

Draw the links in both directions after inserting or removing at the head, in
the middle and at the tail. Alternate `next` and `previous` over the same node.
Try `remove` twice, `set` after `add`, and moving beyond either end. Copy a
list, change one entry and check that the entry in the original is unchanged.
Compare a full backward traversal with the one in Demo02.

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
Initial list and cursor: [1, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
iterator.add(0): insert before the cursor: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
next(): 1
After moving forwards: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 1, nextIndex = 2, hasPrevious = true, hasNext = true
remove(): delete the element returned by next(): [0, 2, 3]; size = 3
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
previous(): 0
After moving backwards: [0, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
set(10): replace the element returned by previous(): [10, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
remove(): delete that element without skipping its successor: [2, 3]; size = 2
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true

Indexed operations inherited from AbstractSequentialList:
get(1): 3
Copy before modification: [2, 3]
copy.set(0, 2.5) replaced: 2
Copy after modification: [2.5, 3]
Original remains: [2, 3]
Original after clear(): []; size = 0
Copy after clearing original: [2.5, 3]
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
