# Sparse polynomial as an ordered map

## Problem statement and prerequisites

Represent a polynomial by a map from nonnegative exponents to nonzero double
coefficients. Prerequisites: maps, TreeMap, wrappers and iteration over entries.
The zero polynomial has degree -1 by explicit convention. addTerm accumulates
rather than replaces, and returns the previous coefficient even on cancellation.

## Guided walkthrough

1. Follow addTerm: validate, combine with the old coefficient, remove exact zero.
2. coefficient returns zero when no entry exists. No dense array is allocated.
3. The conversion constructor copies the entries; Integer/Double values are immutable.
4. plus produces a fresh result. A term cancellation must not mutate either input.
5. evaluate iterates stored entries. toString intentionally shows exponent=value
   pairs so students can inspect the representation directly.

## Costs and contracts

With k nonzero terms, lookup/update cost O(log k) and storage O(k). Addition
uses map insertion and costs O((k+m) log(k+m)) as a simple upper bound; it does not
claim linear merging. Evaluation visits k terms plus Math.pow costs. Floating-point
rounding applies; cancellation uses exact zero. Inputs and stored coefficients
must be finite; evaluation may overflow despite finite inputs. A merge with a
peeking iterator is an instructor extension, not hidden in the introductory code.

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
First: {0=1.0, 2=3.0}
Sum: {0=1.0, 1=2.0}
Sum at 2: 5.0
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
