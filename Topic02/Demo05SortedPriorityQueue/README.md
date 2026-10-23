# Priority queue backed by a sorted list

## Problem statement and prerequisites

Keep a linked list sorted, so that the smallest element can be extracted
straight away. You should already know `Queue`, `ListIterator`, `Comparable` and
`Comparator`. A `null` comparator means natural order. `null` elements are not
allowed. Equal priorities are allowed and keep their order of arrival. The
comparator must define a consistent ordering.

The backing `List<E>` is our `DoublyLinkedList<E>` from
[Demo03](../Demo03DoublyLinkedList/README.md), which Demo04 also reuses. This
folder includes an unchanged copy of `src/ds/DoublyLinkedList.java` so that it
stays self-contained; keep it identical to the one in Demo03. Clients use
`Queue<E>`.

The queue implements only `offer`, `poll`, `peek`, `size` and `iterator`; the
list takes care of managing the nodes and of removal through the iterator.

## Guided walkthrough

1. Follow `offer` until it finds the first value that is strictly larger. It
   steps back and inserts the new value there.
2. Equal priorities are skipped over, so insertion is stable among ties. If no
   larger value is found, the element is inserted with the same list iterator,
   which is already at the end.
3. The comparison is validated before the list changes; comparison failures are
   never caught and ignored.
4. The conversion constructor initialises the comparator before adding any
   element.
5. The inherited queue operations reuse `offer`, `poll` and `peek`. Compare them
   with the FIFO and LIFO queues.

## Costs and contracts

Insertion is O(n), while `peek` and `poll` are O(1). Building a queue of n
entries by calling `offer` repeatedly may cost O(n²). This is the list-based
alternative to a heap, which is introduced later.

Do not modify the fields that determine the order of an object while it is in
the queue. Iteration follows the sorted order, unlike Java's `PriorityQueue`,
whose iteration order is unspecified.

During a traversal, modify the queue only through the iterator in use. The
reused teaching list does not detect changes made by other means. Removal
through the iterator takes O(1), but the inherited `remove(Object)` may first
have to search through O(n) elements.

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
