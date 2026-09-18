# 12 Fibonacci

An iterator that computes a sequence.

**Slides:** A39–A41 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Expose a finite prefix of the Fibonacci sequence through `Iterable<Long>`, computing terms as requested instead of storing them in an array. Prerequisites are the vector-iterator example, inner classes and basic recurrence relations.

## Guided walkthrough

1. Inspect `Fibonacci`: the outer object stores how many terms are requested.
2. Follow `iterator()`, which creates a private inner `FibIterator`.
3. Inspect the iterator's remaining-count and two numeric state fields.
4. Trace one `next` call: preserve the returned term, update the state and decrease the remaining count.
5. Compare two iterators created from the same sequence; each starts independently at zero.

## What to observe and try

The default ten terms are `0 1 1 2 3 5 8 13 21 34`. In Demo07 the iterator reads stored values; here it computes them. Both clients use the same traversal protocol.

Try prefixes of lengths 0, 1 and 2, then request one more element after exhaustion. Explain why `hasNext` must not generate or consume a term. Trace the boundary guard that avoids computing an unnecessary overflowing value.

The constructor accepts 0 through 93 terms, corresponding up to F92 in a signed `long`. Length 94 is rejected. Each iterator stores only a constant amount of numeric state; the sequence object does not cache all terms.

## Open and run

Open **this `Demo12Fibonacci` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Fibonacci.java](src/ds/Fibonacci.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
First ten terms: 0 1 1 2 3 5 8 13 21 34
Iterator a: 0, 1
Iterator b starts at: 0
Rejected: Expected 0 to 93 terms
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
