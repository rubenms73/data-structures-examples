# A set stored as an array of bits

## Problem statement and prerequisites

Implement a set over a finite universe of U elements with an array of U
bits: `bits[i]` is `true` when the element with index `i` is present. You
should already know `AbstractSet`, iterators, anonymous classes and abstract
methods. `null` elements are not allowed.

## Guided walkthrough

1. `BitArraySet` keeps a `boolean[] bits` and a count of the elements. It
   extends `AbstractSet`, so `equals`, `hashCode`, `toString` and the bulk
   operations come from it.
2. The subclass chooses the universe with `index` and `element`, which convert
   between elements and positions. `LetterSet` uses `index(c) = c - 'a'` for the
   26 lowercase letters.
3. `add`, `contains` and `remove` read or write one position. `add` rejects
   elements outside the universe; `contains` and `remove` simply answer `false`.
4. The iterator looks for the next `true` position with `nextIndex`, so it
   returns the elements in index order. Its `remove` sets the last returned
   position to `false`.
5. Every change of membership, including `iterator.remove()`, updates the count.

## Costs and contracts

`add`, `contains` and `remove` take O(1) time, and `size` is O(1) thanks to
the count. A complete traversal checks all U positions, so it takes O(U)
time even when the set has few elements, and the array always uses U bits.
The mapping must give equal elements the same index and different elements
different indices, so that `element(index(e))` equals `e`.

This example completes the bit-array slides of Topic 4, which show only its main
parts.

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

Trace `Main` by hand before you run it. Compare the printed bits with the set,
and check that the traversal order does not depend on the order of insertion.
Try another universe, for example the digits `'0'` to `'9'`, by writing a new
subclass with its own `index` and `element`.

## Expected output

```text
1. Each letter is one position of the bit array
Initial: []; size = 0
add(c): true; set = [c]; size = 1
add(f): true; set = [c, f]; size = 2
add(c): false; set = [c, f]; size = 2
            abcdefghijklmnopqrstuvwxyz
bits:       00100100000000000000000000

2. Membership and removal read or write one position
contains(c): true; contains(z): false
remove(c): true; set = [f]
remove(c) again: false; set = [f]

3. A traversal follows the indices, not the insertion order
After adding z, a and m: [a, f, m, z]
Iterator removes the first element: [f, m, z]; size = 3

4. Only the 26 lowercase letters belong to the universe
contains(A): false
add(A): Outside universe: A; set = [f, m, z]

5. Inherited bulk operations combine sets
set = [f, m, z]; other = [b, f, z]
Union: [b, f, m, z]; intersection: [f, z]
clear(): set = []; size = 0
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
