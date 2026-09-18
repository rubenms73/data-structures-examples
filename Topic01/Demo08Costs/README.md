# 08 Costs

Counted additions in an array traversal and constant-time direct array access.

**Slides:** 53–55 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Count the work performed by summing an array and compare that traversal with accessing a middle element directly. Prerequisites are arrays, integer arithmetic, loops and the meaning of input size.

## Guided walkthrough

1. Follow the creation of arrays of lengths 4, 8 and 16.
2. In `CostAlgorithms.sumAndCount`, distinguish the accumulated sum from the number of additions.
3. Observe that each visited element causes one counted addition.
4. Inspect `middle`: it computes one index and performs one array access.

## What to observe and try

With all entries equal to one, the sum and addition count both equal the length, but they represent different quantities. Change the entries to two: the sum doubles while the addition count stays unchanged.

Try lengths 0, 1 and an even length. The sum loop accepts an empty array; `middle` requires a nonempty array. For an even length, integer division selects index `length / 2`.

The printed costs concern these operations under the usual array-access model. They are not timing measurements, and initialization and console output are not included in the counted additions. A full traversal is linear; a single direct access is constant-time.

## Open and run

Open **this `Demo08Costs` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

On macOS or Linux, from this folder:

```sh
bash run.sh
bash run.sh test
```

To compile and run the example directly (also in Windows PowerShell):

```sh
javac -encoding UTF-8 -d bin src/ds/*.java src/app/*.java
java -cp bin app.Main
```

## Files

- `src/ds/`: the example's classes and interfaces, all in package `ds`.
- `src/app/Main.java`: the test program with `public static void main`, in package `app`.
- `tests/tests/ExampleChecks.java`: automated checks, separate from the teaching program.

The classes in `ds` are:

- [CostAlgorithms.java](src/ds/CostAlgorithms.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
n = 4: sum = 4, additions = 4
n = 8: sum = 8, additions = 8
n = 16: sum = 16, additions = 16
Array middle: C
Array middle: direct access, O(1).
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.

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
