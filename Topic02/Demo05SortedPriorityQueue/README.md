# Priority queue backed by a sorted list

## Problem statement and prerequisites

Keep a linked list sorted so the smallest element can be extracted immediately.
Prerequisites: Queue, ListIterator, Comparable and Comparator. A null comparator
means natural order. Null elements are prohibited. Equal priorities are allowed
and retain arrival order. The comparator must define a consistent ordering.

The backing `List<E>` is our `DoublyLinkedList<E>` from
[Demo03](../Demo03DoublyLinkedList/README.md), also reused in Demo04.
This folder includes an unchanged copy of `src/ds/DoublyLinkedList.java` to
remain self-contained; keep it identical to Demo03. Clients use `Queue<E>`.
The queue implements only `offer`, `poll`, `peek`, `size` and `iterator`;
node management and iterator removal are supplied by the list.

## Guided walkthrough

1. Follow offer until the first strictly larger value. Step back and insert there.
2. Equal priorities are passed, giving stable insertion among ties. If no larger
   value is found, insert with the same list iterator, already at the end.
3. Validate comparison before changing the list; do not catch and ignore comparison failures.
4. The conversion constructor initializes the comparator before adding elements.
5. Inherited queue operations reuse offer, poll and peek. Compare with FIFO/LIFO.

## Costs and contracts

Insertion is O(n); peek and poll are O(1). Building n entries by repeated offer
may cost O(n²). This is the list-based alternative to a heap, introduced later.
Do not mutate fields used for ordering while an object is in the queue. Iteration
is sorted, unlike the unspecified iterator ordering of Java's PriorityQueue.
Only modify the queue through the active iterator during traversal. The reused
teaching list does not detect external changes. Iterator removal takes O(1);
inherited remove(Object) may first search O(n) elements.

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
Natural order: smallest value leaves first
Initial: []; size = 0
offer(7): [7]; size = 1
offer(2): [2, 7]; size = 2
offer(5): [2, 5, 7]; size = 3
offer(2): [2, 2, 5, 7]; size = 4
peek(): 2; contents unchanged = [2, 2, 5, 7]
remove(): 2; remaining = [2, 5, 7]; size = 3
remove(): 2; remaining = [5, 7]; size = 2
remove(): 5; remaining = [7]; size = 1
remove(): 7; remaining = []; size = 0
Empty peek(): null; empty poll(): null

Reverse comparator: largest value leaves first
Initial: []; size = 0
offer(7): [7]; size = 1
offer(2): [7, 2]; size = 2
offer(5): [7, 5, 2]; size = 3
offer(2): [7, 5, 2, 2]; size = 4
peek(): 7; contents unchanged = [7, 5, 2, 2]
remove(): 7; remaining = [5, 2, 2]; size = 3
remove(): 5; remaining = [2, 2]; size = 2
remove(): 2; remaining = [2]; size = 1
remove(): 2; remaining = []; size = 0
Empty peek(): null; empty poll(): null
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
