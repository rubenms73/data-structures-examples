# 01 — A collection backed by singly linked nodes

## Problem statement and prerequisites

Build a collection from singly linked nodes, with a head, a tail and a size, by
extending `AbstractCollection<E>`. Implement only `size()`, `iterator()` and
`boolean add(E)`, which appends an element and returns `true`. The inner forward
iterator provides `hasNext()`, `next()` and `remove()`, which support traversal
and the inherited removal operations.

You should already know references, generic classes, inheritance, abstract
classes, loops, `Collection` and `Iterator`. Read this example before
Demo02LinkedList, which introduces the `List` contract and its bidirectional
cursor.

`null` elements and duplicates are both valid.

## Guided walkthrough

1. Draw `head`, `tail` and the `next` link in each node. In an empty list both
   ends are `null`.
2. Follow an append to an empty list and to a non-empty one. The tail reference
   means the list does not have to be scanned from the start.
3. Trace the four node references held by the iterator. `beforeLast` keeps the
   predecessor, so that removal does not have to search from the head.
4. Remove the last node and then append again: `tail` must refer to the node
   that is now last.
5. Calling `remove` twice without a `next` in between throws
   `IllegalStateException`, and calling `next` on an exhausted iterator throws
   `NoSuchElementException`. Removing an element whose value is `null` is valid.
6. Use the object through `Collection<E>`. `addAll`, `contains`, `toArray`,
   `remove(Object)`, `removeAll`, `retainAll` and `clear` are all inherited. The
   modifying operations reuse `add` or removal through the iterator, and
   `isEmpty` uses the size.

## Costs and contracts

Appending, `size`, and the iterator's `next` and `remove` all cost O(1). A full
traversal and the inherited `contains`, `remove(Object)` and `clear` take O(n)
in the worst case, and the storage is O(n).

During a traversal, modify the collection only through the iterator in use;
changes made by other means are not detected. Equality is not overridden, so
this class keeps identity equality. `AbstractCollection` reuses the forward
iterator and does not need a `ListIterator`.

The name `SinglyLinkedList` describes the internal representation. Its public
contract is `Collection`, with no methods for indexed access or insertion.
Demo02 introduces the fuller `List` contract through `AbstractSequentialList`.

## Open and run

Open this individual folder in VS Code with JDK 17 and Extension Pack for Java.
Run `src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

The project is self-contained. `src/ds` contains the implementation, `src/app`
the demonstration and `tests/tests` the automated checks. No external libraries
are required. Code and comments use English and Allman style.

## What to observe and try

Trace `Main` by hand before you run it. Try the empty case, the boundary cases
and the rejected operations described above, and check that an operation that is
rejected leaves the structure unchanged. Compare the representation with the
public contract it implements.

## Expected output

```text
Initial collection: []; size = 0
add(A): [A]; size = 1
add(B): [A, B]; size = 2
add(C): [A, B, C]; size = 3

Traversal with for-each:
Visit: A
Visit: B
Visit: C
After traversal: [A, B, C]

Remove C through the iterator:
next(): A
next(): B
next(): C
iterator.remove(): [A, B]
add(D), reusing the tail after removal: [A, B, D]

Inherited Collection operations:
contains(B): true
remove(B): true; contents = [A, D]
remove(X): false; contents = [A, D]
clear(): []; size = 0; empty = true
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
