# 09 Costs

Counting the additions in an array traversal, compared with constant-time direct access.

**Slides:** 53–55 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Count the work done when summing an array, and compare that traversal with accessing the middle element directly. You should already know arrays, integer arithmetic, loops and what the size of the input means.

## Guided walkthrough

1. Follow the creation of arrays of lengths 4, 8 and 16.
2. In `CostAlgorithms.sumAndCount`, tell the accumulated sum apart from the number of additions.
3. Notice that each element visited adds one to the count of additions.
4. Look at `middle`. It computes one index and makes one array access.

## What to observe and try

When every entry is one, the sum and the number of additions both equal the length, but they measure different things. Change the entries to two: the sum doubles, while the number of additions stays the same.

Try lengths 0 and 1, and an even length. The sum loop accepts an empty array, but `middle` requires a non-empty one. For an even length, integer division selects index `length / 2`.

The printed costs refer to these operations under the usual model of array access. They are not timings, and the counted additions do not include initialisation or console output. A full traversal takes linear time, whereas a single direct access takes constant time.

## Open and run

Open **this `Demo09Costs` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

On macOS or Linux, from this folder:

```sh
bash run.sh
bash run.sh test
```

To compile and run the example by hand (this also works in Windows PowerShell):

```sh
javac -encoding UTF-8 -d bin src/ds/*.java src/app/*.java
java -cp bin app.Main
```

## Files

- `src/ds/`: the example's classes and interfaces, all in the `ds` package.
- `src/app/Main.java`: the test program, with `public static void main`, in the `app` package.
- `tests/tests/ExampleChecks.java`: automated checks, kept apart from the teaching program.

The classes in `ds` are:

- [CostAlgorithms.java](src/ds/CostAlgorithms.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Sum arrays filled with ones; count additions rather than elapsed time:
n = 4: sum = 4, additions = 4
n = 8: sum = 8, additions = 8
n = 16: sum = 16, additions = 16
Doubling n doubles the additions: linear work, O(n).
Input for direct access: [A, B, C, D, E]
Middle index: 2
Array middle: C
Array middle: direct access, O(1).
```

The commented-out compilation errors are deliberate exercises. Restore each comment before you continue.

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

