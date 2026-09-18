# 01 Rational

Contract, interface, implementations, objects and references.

**Slides:** 12, 16–18 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Represent the same rational number with two different classes and write one client that works with either implementation through `Rational`. You should already know classes, constructors, interfaces, references and integer versus floating-point division.

## Guided walkthrough

1. Read `Rational.java`: its observer methods form the contract available to a client.
2. Compare `RationalImp1` with `RationalImp2`. The second stores two integers in an array; this representation does not appear in the interface.
3. Follow `Main.show`. Its parameter is a `Rational`, so the same code prints either representation.
4. Trace the assignments to `a`, `b` and `r`. Only `r` and `a` refer to the same object.
5. Observe how construction rejects a zero denominator before producing an invalid object.

## What to observe and try

Both representations print `3/4 = 0.75`. Equal numerical results do not imply identical references. Try `2/4` and `1/2`, then a negative numerator. Explain the difference between comparing references and comparing returned values.

These classes do not reduce fractions, implement arithmetic or define value-based `equals`. The comparison of `value()` in this small example is not a general exact equality algorithm for rational numbers. Restore the original inputs before comparing against the documented output.

## Open and run

Open **this `Demo01Rational` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Rational.java](src/ds/Rational.java)
- [RationalImp1.java](src/ds/RationalImp1.java)
- [RationalImp2.java](src/ds/RationalImp2.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
3/4 = 0.75
3/4 = 0.75
r and a refer to the same object: true
a and b refer to the same object: false
a and b return the same value: true
Rejected: Zero denominator
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
