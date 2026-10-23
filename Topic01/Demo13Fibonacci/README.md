# 13 Fibonacci

An iterator that computes a sequence.

**Slides:** A39–A41 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Expose a finite prefix of the Fibonacci sequence through `Iterable<Long>`, computing each term when it is requested instead of storing the terms in an array. You should already know the vector-iterator example, inner classes and basic recurrence relations.

## Guided walkthrough

1. Look at `Fibonacci`. The outer object stores how many terms have been requested.
2. Follow `iterator()`, which creates a private inner `FibIterator`.
3. Look at the iterator's fields: the count of remaining terms and the two numbers that hold its state.
4. Trace one call to `next`. It keeps the term it is about to return, updates the state and decreases the remaining count.
5. Compare two iterators created from the same sequence. Each one starts at zero, independently of the other.

## What to observe and try

The default ten terms are `0 1 1 2 3 5 8 13 21 34`. In Demo07 the iterator reads stored values, whereas here it computes them. Both clients follow the same traversal protocol.

Try prefixes of lengths 0, 1 and 2, and then ask for one more element once the iterator is exhausted. Explain why `hasNext` must not generate or consume a term. Trace the guard at the boundary that avoids computing an unnecessary value that would overflow.

The constructor accepts from 0 to 93 terms, which reaches F92 in a signed `long`; a length of 94 is rejected. Each iterator stores only a constant amount of numeric state, and the sequence object does not cache the terms.

## Open and run

Open **this `Demo13Fibonacci` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Fibonacci.java](src/ds/Fibonacci.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
First ten terms: 0 1 1 2 3 5 8 13 21 34
A fresh iterator starts again; traversal does not consume the sequence.
Iterator a: 0, 1
Iterator b starts at: 0
Independent positions: a.next() = 1; b.next() = 1
A zero-term sequence hasNext(): false
Request 94 terms, exceeding the safe long range:
Rejected: Expected 0 to 93 terms
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

