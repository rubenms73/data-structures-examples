# 02 — A singly linked list with ListIterator

## Problem statement and prerequisites

Implement a generic singly linked list by extending `AbstractSequentialList<E>`.
Provide `size()` and `listIterator(int index)`, so that the superclass can
supply indexed access and modification. Implement all the cursor operations,
including backward traversal, insertion, replacement and removal.

You should already know references, linked nodes, generics, inheritance,
abstract classes, `List`, `Iterator` and `ListIterator`. Study a simple forward
iterator first.

## Representation and contracts

`head` refers to the first node and `count` is the number of nodes. An empty
list has `head == null` and `count == 0`. The nodes are private inner objects.

Elements may be `null`. A `null` source collection or varargs array is rejected
with a simple conditional that throws `NullPointerException`.

The conversion constructor accepts a `Collection<? extends E>`. It creates new
nodes in the same order and shares the element references. Replacing an entry in
one list does not change the other, but modifying a shared element object
affects both.

Iterator positions range from zero to the size, inclusive. The cursor lies
between `previousNode` and `nextNode`, and `nextIndex` is the index of the
element that `next()` would return.

`lastReturned` has a different purpose: it records the node that `set` or
`remove` would affect, whichever direction the traversal went. When this field
is `null`, those operations are not currently allowed; a node that contains
`null` is still a valid node.

`add` inserts before the next node and leaves the cursor after the inserted
node. Both `add` and `remove` invalidate `lastReturned`, whereas `set` may be
repeated and does not invalidate it. Moving past either end throws
`NoSuchElementException`.

## Guided walkthrough

1. Look at `Node`, `head` and `count`, and then trace how the constructor builds
   the list by inserting through an iterator.
2. Follow the iterator constructor to see how a cursor reaches an index.
3. Trace `next` and `previous`. Both record the returned node before moving the
   cursor.
4. Follow `remove` after each direction. After `next`, the cursor index
   decreases; after `previous`, the next node changes and the index stays the
   same.
5. Look at `add` at the head, in an empty list and at the end.
6. Run `Main`. Its `get`, `set`, `clear` and printing operations show what is
   reused from the superclass.

## Costs and limitations

| Operation | Cost |
|---|---|
| `size`, `hasNext`, `hasPrevious`, cursor indices | O(1) |
| `listIterator(index)` | O(index) |
| `next`, iterator `add`, iterator `set` | O(1) |
| `previous` | O(n) worst case |
| iterator `remove` after `next` | O(n) worst case |
| iterator `remove` after `previous` | O(1) |
| construction from n elements | O(n) |

A step backwards has to search for the predecessor from the head, so a complete
backward traversal can cost O(n²). Repeated appends through the inherited
operations can also cost O(n²); the constructors avoid this by reusing a single
iterator.

During a traversal, modify the list only through the iterator in use. This
teaching implementation does not detect changes made by other means, and it is
not meant to replace the standard library's `LinkedList`. Adding backward links
would be a separate exercise, with different storage costs and invariants.

## What to observe and try

Try `remove` or `set` before any movement, `remove` twice in a row, and `set`
straight after `add`. Each of these must throw `IllegalStateException`. Test
`previous` followed by `set` or `remove`, insertion into an empty list, `null`
elements, and copying `Integer` elements into a list of `Number`. Draw the
cursor between the nodes after each operation.

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
