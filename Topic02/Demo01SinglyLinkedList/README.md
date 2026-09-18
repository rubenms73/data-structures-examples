# 01 — Singly linked list and forward iterator

## Problem statement and prerequisites

Build a list from singly linked nodes with head, tail and size. Support append,
indexed insertion and access, and an inner forward iterator with removal.
Prerequisites: references, generic classes, loops and Iterator. Read this example
before Demo02LinkedList, whose bidirectional cursor is a later extension.
Null elements are valid. Invalid indices are rejected before changing links.

## Guided walkthrough

1. Draw head, tail and the next link in each node. Empty lists have both ends null.
2. Follow append into an empty and a nonempty list; tail avoids a complete scan.
3. Follow insertion at the head, middle and end. Valid insertion indices include size.
4. Trace the four iterator node references. beforeLast saves the predecessor so
   removal does not need to search from the head.
5. Remove the last node, then append again: tail must refer to the surviving last node.
6. Repeated removal without next throws IllegalStateException. Exhaustion throws
   NoSuchElementException. Removing an element whose value is null is valid.

## Costs and contracts

Append, size, iterator next and iterator remove cost O(1). Indexed access and
insertion cost O(n) in the worst case. Full traversal is O(n); storage is O(n).
Modify only through the active iterator during traversal. External modifications
are not detected. Equality is not overridden: this class does not claim List
value equality. The old membership-based equals and faulty tail update are not retained.

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
Last after removal and append: D
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
