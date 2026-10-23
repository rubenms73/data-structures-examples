# Hash set with separate chaining

This teaching example does not deal with extremely large capacities; the table
grows by the simple geometric rule.

## Problem statement and prerequisites

Implement a set based on equality, with linked lists as buckets. You should
already know `Set`, `hashCode` and `equals`, iterators and rehashing. The
elements do not have to be `Comparable`. The capacity must be positive (unlike a
vector, which may have capacity zero), and `null` elements are rejected. The
load threshold must be positive and finite, so `NaN` is rejected too.

## Guided walkthrough

1. The hash is reduced modulo the capacity, and a negative remainder is then
   corrected.
2. `contains` checks equality only within one bucket, which is why equal objects
   must have equal hash codes.
3. Before inserting a new element, the table grows once if the load after the
   insertion would exceed the threshold.
4. Rehashing recomputes the index of every entry from the new capacity; the old
   bucket indices are never copied.
5. The iterator scans the buckets. It keeps `lastUsed` in a separate field,
   because `hasNext` may already have moved on.
6. Removing through the iterator must also decrease the `size` of the enclosing
   set.

## Costs and contracts

With a suitable load and a good distribution of hash codes, lookups and updates
have an expected cost of O(1); the worst case is O(n). Rehashing costs O(n + new
capacity), and iteration costs O(n + capacity). A single insertion triggers at
most one growth step (doubling plus one), so a very small threshold may be
exceeded for a while. Structural changes during an iteration are not supported,
except through the iterator itself. Hash codes and equality must not change
while an element is stored.

This version fixes three problems in the old material: the negative-index bug,
buckets that only worked with `Comparable` elements, and a size that was not
updated on removal. The inherited `clear` works through the corrected iterator.

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
1. Separate chaining; initial capacity 3, load limit 0.75
Initial: []; size = 0
add(-1): true; contents = [-1]; size = 1
add(-2147483648): true; contents = [-2147483648, -1]; size = 2
add(7): true; contents = [7, -2147483648, -1]; size = 3
The third distinct insertion exceeds 3 * 0.75 and triggers rehashing.
Iteration follows buckets; it is not sorted or arrival order.
contains(-1): true
contains(Integer.MIN_VALUE): true
contains(99): false
add(7) again: false; size = 3
remove(99): false; size = 3

2. Equal hash codes do not imply equal elements
Aa.hashCode(): 2112; BB.hashCode(): 2112
Aa.equals(BB): false
Both strings remain: [Aa, BB]; size = 2
remove(Aa): true; contains(BB): true

3. Iterator removal updates the total size
next(): 7; hasNext(): true
iterator.remove(): [-2147483648, -1]; size = 2
next(): -2147483648; hasNext(): true
iterator.remove(): [-1]; size = 1
next(): -1; hasNext(): false
iterator.remove(): []; size = 0
Empty: true
Reuse with add(42): [42]; size = 1
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
