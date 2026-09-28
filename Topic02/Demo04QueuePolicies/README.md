# FIFO and LIFO through AbstractQueue

## Problem statement and prerequisites

Implement FIFO and LIFO extraction policies using the same Queue interface.
Prerequisites: collections, abstract classes, linked lists and iterators.
Nulls are rejected explicitly because poll and peek use null to report emptiness.
Collection constructors copy element references in source iteration order.

Both queues reuse `DoublyLinkedList<E>` from
[Demo03](../Demo03DoublyLinkedList/README.md), storing it through a `List<E>`
reference. This folder includes the unchanged `src/ds/DoublyLinkedList.java`
from that demo so it remains self-contained; keep the two copies identical.
Both classes implement only `offer`, `poll`, `peek`, `size` and `iterator`;
their iterators support removal. Clients use `Queue<E>` references.

## Guided walkthrough

1. Compare offer: FIFO appends with `data.add(value)`; LIFO inserts at the front
   with `data.add(0, value)`. Both extract and observe index zero.
2. Follow AbstractQueue's inherited add/remove/element operations. The latter two
   throw NoSuchElementException on an empty queue, whereas poll/peek return null.
3. Both queues return `data.iterator()`: the list already stores their elements
   in extraction order. Node management and iterator removal belong to Demo03's
   list implementation and require no new queue-specific iterator.
4. The queue adds its null policy and FIFO/LIFO insertion policy to the List
   contract. All stored collection references use interfaces: List internally,
   Queue in the client.
5. Inherited clear repeatedly calls poll. Inherited remove(Object), removeAll
   and retainAll use iterator removal. Trace these two paths to modifying a queue.

## Costs and contracts

With this DoublyLinkedList, offer, poll, peek and size take O(1) for both policies:
its list iterator reaches either end in O(1). Iterator removal also takes O(1).
Traversal and inherited clear take O(n); removing by value may require an O(n)
search. These costs depend on the chosen list implementation, not just List.
Only modify a queue through the active iterator during traversal. The reused
teaching list does not detect external changes.

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
