# Priority queue backed by a sorted list

## Problem statement and prerequisites

Keep a linked list sorted so the smallest element can be extracted immediately.
Prerequisites: Queue, ListIterator, Comparable and Comparator. A null comparator
means natural order. Null elements are prohibited. Equal priorities are allowed
and retain arrival order. The comparator must define a consistent ordering.

## Guided walkthrough

1. Follow offer until the first strictly larger value. Step back and insert there.
2. Equal priorities are passed, giving stable insertion among ties.
3. Validate comparison before changing the list; do not catch and ignore comparison failures.
4. The conversion constructor initializes the comparator before adding elements.
5. Inherited queue operations reuse offer, poll and peek. Compare with FIFO/LIFO.

## Costs and contracts

Insertion is O(n); peek and poll are O(1). Building n entries by repeated offer
may cost O(n²). This is the list-based alternative to a heap, introduced later.
Do not mutate fields used for ordering while an object is in the queue. Iteration
is sorted, unlike the unspecified iterator ordering of Java's PriorityQueue.

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
Sorted: [2, 2, 5, 7]
Extract: 2
Extract: 2
Extract: 5
Extract: 7
```
