# Sparse polynomial as an ordered map

## Problem statement and prerequisites

Represent a polynomial as a map from non-negative exponents to non-zero `double`
coefficients. You should already know maps, `TreeMap`, wrapper classes and
iteration over entries. By explicit convention, the zero polynomial has degree
-1. `addTerm` adds to the existing coefficient instead of replacing it, and it
returns the previous coefficient even when the terms cancel out.

## Guided walkthrough

1. Follow `addTerm`: it validates its arguments, combines the new coefficient
   with the old one, and removes the entry if the result is exactly zero.
2. `coefficient` returns zero when there is no entry, so no dense array is ever
   allocated.
3. The conversion constructor copies the entries; `Integer` and `Double` values
   are immutable.
4. `plus` produces a new result. When terms cancel, neither input may be
   modified.
5. `evaluate` iterates over the stored entries. `toString` deliberately shows
   `exponent=value` pairs, so that students can inspect the representation
   directly.

## Costs and contracts

With k non-zero terms, lookups and updates cost O(log k) and the storage is
O(k). Addition uses map insertion; O((k+m) log(k+m)) is a simple upper bound for
its cost, and no claim of linear merging is made. Evaluation visits the k terms,
plus the cost of `Math.pow`. Floating-point rounding applies, and cancellation
is detected only when the result is exactly zero. The inputs and the stored
coefficients must be finite, although evaluation may still overflow with finite
inputs. Merging with a peeking iterator is left as an extension for the
instructor rather than hidden in the introductory code.

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
Sparse notation: {exponent=coefficient}; absent exponents have coefficient zero.
Initial polynomial: {}; degree = -1
addTerm(2, 3), previous coefficient: 0.0; first = {2=3.0}
addTerm(0, 1), previous coefficient: 0.0; first = {0=1.0, 2=3.0}
First: {0=1.0, 2=3.0}; degree = 2
Second: {1=2.0, 2=-3.0}; degree = 2

Sum: {0=1.0, 1=2.0}; degree = 1
Coefficient of x^2 after cancellation: 0.0
Operands after plus(): first = {0=1.0, 2=3.0}; second = {1=2.0, 2=-3.0}
At x = 2: first = 13.0; second = -8.0; sum = 5.0
sum.addTerm(1, -2), previous coefficient: 2.0; sum = {0=1.0}
sum.addTerm(0, -1), previous coefficient: 1.0; sum = {}; degree = -1
Zero polynomial at x = 2: 0.0
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
