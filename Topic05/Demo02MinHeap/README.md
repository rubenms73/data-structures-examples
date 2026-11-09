# A binary min-heap

## Problem statement and prerequisites

Implement a minimum-priority queue with a binary heap stored in an array. You
should already know complete binary trees, `Comparator` and amortised growth of
an array. The comparator must not be `null`, and `null` elements are rejected.

## Guided walkthrough

1. The children of position `i` are at `2i + 1` and `2i + 2`, and its parent is
   at `(i - 1) / 2`. Every parent is less than or equal to its children.
2. `add` opens a gap at the next leaf and moves parents down into it while they
   are larger than the new value; the value is stored once, at the end.
3. `remove` returns the root and sifts the last element down from the root:
   at each step the smaller child moves up into the gap.
4. The second constructor copies a collection and builds the heap bottom-up,
   sifting down the internal nodes from the last one to the root.
5. With `Comparator.reverseOrder()` the same class removes the largest element
   first.

## Costs and contracts

`element`, `size` and `isEmpty` take O(1) time. `add` and `remove` take
O(log n), with amortised O(1) extra cost when the array doubles. Building a heap
bottom-up takes O(n), while adding the n elements one by one would take
O(n log n). `clear` takes O(n) because it releases the stored references.
`toString` shows the array, to follow the examples of the slides.

This example completes the priority-queue slides of Topic 5, which show only the
main loops.

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

Trace `Main` by hand before you run it, drawing the tree for each array. Check
that the five insertions and the bottom-up construction give the same arrays as
the tables in the slides. Try the heap with a comparator of your own, for
example strings by length.

## Expected output

```text
1. Inserting sifts the new value up
add(20): array = [20]
add(30): array = [20, 30]
add(25): array = [20, 30, 25]
add(25): array = [20, 25, 25, 30]
add(10): array = [10, 20, 25, 30, 25]
element(): 10; size = 5

2. Removing the minimum sifts the last element down
remove(): 10; array = [20, 25, 25, 30]
remove(): 20; array = [25, 30, 25]
remove(): 25; array = [25, 30]
remove(): 25; array = [30]
remove(): 30; array = []

3. Building a heap bottom-up from an array
From [9, 4, 7, 1, 3, 6, 2]: array = [1, 3, 2, 4, 9, 6, 7]

4. A reversed comparator gives a max-heap
Removed in order: plum pear fig apple
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
