# Sets and maps backed by lists

## Problem statement and prerequisites

Implement a set by extending `AbstractSet` and a map by extending `AbstractMap`.
You should already know `List`, `Set`, `Map.Entry`, iterators, `equals` and
`hashCode`, and optional operations. `null` elements, keys and values are
allowed. Keys must have stable equality.

## Guided walkthrough

1. `ListSet` guarantees uniqueness by checking membership before inserting.
2. `ListMap` searches its entries and replaces the value when it finds an equal
   key.
3. `SimpleEntry` provides the standard `Map.Entry` contract for `equals`,
   `hashCode` and `setValue`.
4. `entrySet` is a live inner view, not a copy, and its iterator removes from
   the map.
5. `AbstractMap` derives `get`, `containsKey`, `remove`, `keySet` and `values`
   from `entrySet`.
6. `EntrySet` does not support `add`; new associations are added with `put`.

## Costs and contracts

Membership tests, `get` and `put` cost O(n), and a complete traversal is O(n).
This makes the example useful for understanding the contracts, not a fast
general-purpose map. `AbstractSet` and `AbstractMap` provide compatible `equals`
and `hashCode` methods. Mutable entry values are supported, but changing the
equality of a key while it is stored is not. The views are backed by the map.

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
1. A set stores each distinct element once
Initial: []; size = 0
add(A): true; set = [A]; size = 1
add(A): false; set = [A]; size = 1
add(B): true; set = [A, B]; size = 2
contains(A): true; contains(X): false
remove(A): true; set = [B]
remove(A) again: false; set = [B]

2. A map associates one value with each key
put(A, 1), previous value: null; map = {A=1}
put(A, 2), previous value: 1; map = {A=2}; size = 1
put(B, 3), previous value: null; map = {A=2, B=3}
get(A): 2; get(X): null

3. Collection views are connected to the map
Keys: [A, B]; values: [2, 3]; entries: [A=2, B=3]
keys.remove(B): true; map = {A=2}
put(C, 4): map = {A=2, C=4}; existing key view = [A, C]
values().remove(2): true; map = {C=4}
clear(): map = {}; existing key view = []
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
