# 01 — A collection backed by singly linked nodes

## Problem statement and prerequisites

Build a collection from singly linked nodes with head, tail and size by extending
`AbstractCollection<E>`. Implement only `size()`, `iterator()` and `boolean add(E)`,
which appends an element and returns true. The inner forward iterator supplies
`hasNext()`, `next()` and `remove()` to support traversal and inherited removal.
Prerequisites: references, generic classes, inheritance, abstract classes, loops,
Collection and Iterator. Read this example before Demo02LinkedList, which
introduces the List contract and its bidirectional cursor.
Null elements and duplicates are valid.

## Guided walkthrough

1. Draw head, tail and the next link in each node. Empty lists have both ends null.
2. Follow append into an empty and a nonempty list; tail avoids a complete scan.
3. Trace the four iterator node references. beforeLast saves the predecessor so
   removal does not need to search from the head.
4. Remove the last node, then append again: tail must refer to the surviving last node.
5. Repeated removal without next throws IllegalStateException. Exhaustion throws
   NoSuchElementException. Removing an element whose value is null is valid.
6. Use the object through `Collection<E>`: `addAll`, `contains`, `toArray`,
   `remove(Object)`, `removeAll`, `retainAll` and `clear` are inherited.
   The modifying operations reuse `add` or iterator removal; `isEmpty` uses size.

## Costs and contracts

Append, size, iterator next and iterator remove cost O(1).
Full traversal, inherited contains, remove(Object) and clear take O(n) in the
worst case; storage is O(n).
Modify only through the active iterator during traversal. External modifications
are not detected. Equality is not overridden: this class retains identity equality.

`AbstractCollection` reuses the forward iterator without requiring a ListIterator.
`SinglyLinkedList` names the internal representation. Its public contract is
`Collection`, with no indexed access or insertion methods. Demo02 introduces
the fuller `List` contract through `AbstractSequentialList`.

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

Trace Main by hand before running it. Exercise the empty case, boundaries and
rejected operations described above. Verify that a rejected single operation
leaves the structure unchanged. Compare the representation with its public contract.

## Expected output

```text
A
B
C
After iterator removal and append: [A, B, D]
Contains B: true
After inherited remove: [A, D]
Empty after inherited clear: true
```

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install a JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
