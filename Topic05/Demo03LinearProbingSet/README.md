# A set with linear probing

## Problem statement and prerequisites

Implement a fixed-capacity set with open addressing and linear probing. You
should already know `hashCode`, `equals` and the remainder operator. The
capacity must be positive and `null` keys are rejected.

## Guided walkthrough

1. Each slot is EMPTY, OCCUPIED or DELETED. The `byte` array starts filled with
   zeros, so every slot starts EMPTY.
2. `start` reduces the hash code to a valid index; a negative remainder is
   corrected by adding the capacity.
3. `find` tries consecutive slots. An EMPTY slot proves that the key is absent;
   a DELETED slot does not, so the search continues past it.
4. `remove` leaves DELETED instead of EMPTY, so that later searches still reach
   the keys stored after it.
5. `add` remembers the first DELETED slot it passes and reuses it once it knows
   that the key is absent. A full table throws `IllegalStateException`.

## Costs and contracts

With a low load factor and well-spread hash codes, the expected cost of each
operation is O(1); the worst case visits all b slots. DELETED slots lengthen the
searches until the table is rebuilt; this fixed-capacity version does not grow or
rebuild itself. Hash codes and equality must not change while a key is stored.
`toString` shows every slot: a key, `-` for EMPTY or `DEL` for DELETED.

This example completes the open-addressing slides of Topic 5, which show only the
search loop.

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

Trace `Main` by hand before you run it. Check that the table matches the slide
that follows collisions through consecutive slots. Try removing 1 and then
searching for 8 and 15, and try a capacity whose keys all start at the same slot.

## Expected output

```text
1. Collisions move on to the next slot
Initial: [-, -, -, -, -, -, -]
add(7), start 0: true; table = [7, -, -, -, -, -, -]
add(1), start 1: true; table = [7, 1, -, -, -, -, -]
add(8), start 1: true; table = [7, 1, 8, -, -, -, -]
add(15), start 1: true; table = [7, 1, 8, 15, -, -, -]

2. A removed key leaves a DELETED marker
remove(8): true; table = [7, 1, DEL, 15, -, -, -]
contains(15): true (the search goes past DEL)
contains(8): false

3. Insertion reuses the first DELETED slot
add(15) again: false; table = [7, 1, DEL, 15, -, -, -]
add(22), start 1: true; table = [7, 1, 22, 15, -, -, -]
size = 4

4. A full table rejects a new key
add(3) to [2, 1]: Full
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
