# Sparse vectors and matrices

## Problem statement and prerequisites

Store only nonzero integer values while retaining fixed logical dimensions.
Prerequisites: maps, composition and index validation. Zero dimensions are valid;
negative dimensions are rejected. Every access checks the logical bounds.

## Guided walkthrough

1. SparseVector maps indices to nonzero values. Missing keys represent zero.
2. Setting zero removes the key, maintaining the sparse invariant.
3. SparseMatrix maps row indices to sparse vectors. A row is created only when needed.
4. Removing a row's last nonzero value removes the row object from the map.
5. Compare logical dimensions with storedEntries and storedRows.

## Costs and contracts

Expected get/set cost is O(1) under usual hash assumptions. Storage depends on
nonzero entries and nonempty rows rather than rows*columns. HashMap overhead
makes dense arrays preferable for dense data. Integer values avoid floating-point
zero tolerances. Vector/matrix arithmetic is a separate exercise.

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
Length: 1000000; stored: 1
Entry: 12
Stored rows after clearing entry: 0
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
