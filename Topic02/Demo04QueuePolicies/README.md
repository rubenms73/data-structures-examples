# FIFO and LIFO through AbstractQueue

## Problem statement and prerequisites

Implement FIFO and LIFO extraction policies using the same Queue interface.
Prerequisites: collections, abstract classes, linked lists, array lists and iterators.
Nulls are rejected explicitly because poll and peek use null to report emptiness.
Collection constructors copy element references in source iteration order.

FIFO owns singly linked nodes with head, tail and size. LIFO stores its elements
through a `List<E>` reference initialized with an `ArrayList<E>`.
Both classes implement only `offer`, `poll`, `peek`, `size` and `iterator`;
their iterators support removal. Clients use `Queue<E>` references.

## Guided walkthrough

1. Compare offer, peek and poll: FIFO works at opposite ends, LIFO at the same end.
2. Follow AbstractQueue's inherited add/remove/element operations. The latter two
   throw NoSuchElementException on an empty queue, whereas poll/peek return null.
3. Compare extraction order with iteration: this example's LIFO iterator walks
   the backing array list backwards, using a private inner adapter.
4. FIFO iterator removal reconnects nodes and updates head or tail when necessary;
   LIFO iterator removal delegates to the backing list iterator.
5. Inherited clear repeatedly calls poll. Inherited remove(Object), removeAll
   and retainAll use iterator removal. Trace these two paths to modifying a queue.

## Costs and contracts

FIFO insertion/extraction is O(1). LIFO insertion is amortized O(1), with O(1)
peek/poll. Traversal is O(n). Removing arbitrary elements may cost O(n).
FIFO retains the predecessor during iteration, making iterator removal O(1).
Removing through the LIFO iterator may shift array elements and cost O(n).
Only modify a queue through the active iterator during traversal. The teaching
FIFO iterator does not detect external changes. Removing the last FIFO node,
whether through poll or the iterator, must leave both head and tail null.

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
FIFO iteration: [1, 2, 3]
LIFO iteration: [3, 2, 1]
FIFO/LIFO: 1/3
FIFO/LIFO: 2/2
FIFO/LIFO: 3/1
Empty poll: null
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
