# FIFO and LIFO through AbstractQueue

## Problem statement and prerequisites

Implement FIFO and LIFO extraction policies behind the same `Queue` interface.
You should already know collections, abstract classes, linked lists and
iterators.

`null` elements are rejected explicitly, because `poll` and `peek` use `null` to
report that the queue is empty. The collection constructors copy the element
references in the source's iteration order.

Both queues reuse `DoublyLinkedList<E>` from
[Demo03](../Demo03DoublyLinkedList/README.md) and hold it through a `List<E>`
reference. This folder includes an unchanged copy of
`src/ds/DoublyLinkedList.java` from that demo so that it stays self-contained;
keep the two copies identical.

Both classes implement only `offer`, `poll`, `peek`, `size` and `iterator`, and
their iterators support removal. Clients use `Queue<E>` references.

## Guided walkthrough

1. Compare the two versions of `offer`. FIFO appends with `data.add(value)`,
   whereas LIFO inserts at the front with `data.add(0, value)`. Both extract and
   inspect the element at index zero.
2. Follow the `add`, `remove` and `element` operations inherited from
   `AbstractQueue`. On an empty queue the last two throw
   `NoSuchElementException`, whereas `poll` and `peek` return `null`.
3. Both queues return `data.iterator()`, because the list already stores their
   elements in extraction order. Managing the nodes and removing through the
   iterator are handled by the list implementation from Demo03, so no new
   iterator is needed for the queues.
4. A queue adds two things to the `List` contract: its policy on `null` and its
   FIFO or LIFO insertion policy. Every stored collection reference has an
   interface type: `List` inside the queue and `Queue` in the client.
5. The inherited `clear` calls `poll` repeatedly, while the inherited
   `remove(Object)`, `removeAll` and `retainAll` use removal through the
   iterator. Trace these two ways of modifying a queue.

## Costs and contracts

With this `DoublyLinkedList`, `offer`, `poll`, `peek` and `size` take O(1) under
both policies, because its list iterator reaches either end in O(1). Removal
through the iterator also takes O(1). A traversal and the inherited `clear` take
O(n), and removing by value may need an O(n) search. These costs depend on the
list implementation chosen, not just on the `List` interface.

During a traversal, modify a queue only through the iterator in use. The reused
teaching list does not detect changes made by other means.

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
1. Same arrivals, different insertion policies
  FIFO: []; size = 0
  LIFO: []; size = 0
offer(1) to both queues:
  FIFO: [1]; size = 1
  LIFO: [1]; size = 1
offer(2) to both queues:
  FIFO: [1, 2]; size = 2
  LIFO: [2, 1]; size = 2
offer(3) to both queues:
  FIFO: [1, 2, 3]; size = 3
  LIFO: [3, 2, 1]; size = 3

2. Inspect and traverse without removing
FIFO peek(), oldest arrival: 1
LIFO peek(), newest arrival: 3
FIFO for-each: 1 2 3
LIFO for-each: 3 2 1
After inspection and traversal:
  FIFO: [1, 2, 3]; size = 3
  LIFO: [3, 2, 1]; size = 3

3. Extract, then accept a new arrival
FIFO remove(): 1; remaining = [2, 3]
LIFO remove(): 3; remaining = [2, 1]
offer(4) to both queues:
  FIFO: [2, 3, 4]; size = 3
  LIFO: [4, 2, 1]; size = 3
FIFO remove(): 2; remaining = [3, 4]
LIFO remove(): 4; remaining = [2, 1]
FIFO remove(): 3; remaining = [4]
LIFO remove(): 2; remaining = [1]
FIFO remove(): 4; remaining = []
LIFO remove(): 1; remaining = []

4. Empty queues: special values and exceptions
FIFO peek(): null; poll(): null
FIFO element(): NoSuchElementException
FIFO remove(): NoSuchElementException
FIFO offer(null): NullPointerException; contents = []
LIFO peek(): null; poll(): null
LIFO element(): NoSuchElementException
LIFO remove(): NoSuchElementException
LIFO offer(null): NullPointerException; contents = []
add(9): both queues can be reused
  FIFO: [9]; size = 1
  LIFO: [9]; size = 1
clear():
  FIFO: []; size = 0
  LIFO: []; size = 0
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
