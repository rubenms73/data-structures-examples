# 01 Rational

Contract, interface, implementations, objects and references.

**Slides:** 12, 16–18 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Represent the same rational number with two different classes, and write one client that works with either implementation through `Rational`. You should already be familiar with classes, constructors, interfaces, references, and the difference between integer and floating-point division.

## Guided walkthrough

1. Read `Rational.java`. Its observer methods form the contract that a client can rely on.
2. Compare `RationalImp1` with `RationalImp2`. The second stores the two integers in an array, and nothing in the interface reveals this choice.
3. Follow `Main.show`. Its parameter has type `Rational`, so the same code prints either representation.
4. Trace the assignments to `a`, `b` and `r`. Only `r` and `a` refer to the same object.
5. Notice how the constructor rejects a zero denominator before an invalid object can be created.

## What to observe and try

Both representations print `3/4 = 0.75`. Equal numerical results do not mean that the references are identical. Try `2/4` and `1/2`, then a negative numerator. Explain the difference between comparing references and comparing the values the methods return.

These classes do not reduce fractions, implement arithmetic or define value-based `equals`. Comparing `value()` works in this small example, but it is not a general, exact equality test for rational numbers. Restore the original inputs before you compare your output with the one documented below.

## Open and run

Open **this `Demo01Rational` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Rational.java](src/ds/Rational.java)
- [RationalImp1.java](src/ds/RationalImp1.java)
- [RationalImp2.java](src/ds/RationalImp2.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Same Rational interface, two representations:
RationalImp1 (two fields): 3/4 = 0.75
RationalImp2 (array): 3/4 = 0.75

Reference identity versus numeric value:
r and a refer to the same object: true
a and b refer to the same object: false
a and b return the same value: true

Constructing 3/0 violates the denominator invariant:
Rejected: Zero denominator
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

