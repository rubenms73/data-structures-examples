# Sparse vectors and matrices

## Problem statement and prerequisites

Store only the non-zero integer values, while keeping fixed logical dimensions.
You should already know maps, composition and index validation. Zero dimensions
are valid and negative dimensions are rejected. Every access checks the logical
bounds.

## Guided walkthrough

1. `SparseVector` maps indices to non-zero values; a missing key stands for
   zero.
2. Setting a value to zero removes its key, which keeps the sparse invariant.
3. `SparseMatrix` maps row indices to sparse vectors, and a row is created only
   when it is needed.
4. Removing the last non-zero value of a row also removes the row object from
   the map.
5. Compare the logical dimensions with `storedEntries` and `storedRows`.

## Costs and contracts

Under the usual assumptions about hashing, `get` and `set` have an expected cost
of O(1). The storage depends on the number of non-zero entries and non-empty
rows rather than on rows × columns. Because of the overhead of `HashMap`, dense
arrays are a better choice for dense data. Integer values avoid the need for
floating-point tolerances when testing for zero. Arithmetic on vectors and
matrices is a separate exercise.

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
1. Logical length and stored entries are different
Initial vector: length = 1000000; stored entries = 0
Unset get(123): 0
set(999999, 7): stored entries = 1; logical length = 1000000
set(3, -2): stored entries = 2; logical length = 1000000
set(999999, 9): replacing a value: stored entries = 2; logical length = 1000000
set(999999, 0): remove its stored entry: stored entries = 1; logical length = 1000000
get(999999) after removal: 0

2. A 1000 x 1000 matrix stores only nonempty rows
Initial stored rows: 0
Unset get(5, 9): 0
set(5, 9, 12): value = 12; stored rows = 1
set(5, 10, 4): stored rows = 1
set(8, 2, -3): stored rows = 2
Clear (5, 9): stored rows = 2; row 5 still contains (5, 10) = 4
Clear last entry of row 5: stored rows = 1
Clear last entry of row 8: stored rows = 0
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
