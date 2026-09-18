# 09 Functional comparators

Named class, anonymous class and lambda.

**Slides:** A25–A29 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Express the same string-length comparator in three ways: a named class, an anonymous class and a lambda. Use the same client method with all three. Prerequisites are the string-sorting example, interfaces and the introduction to anonymous classes and lambdas.

## Guided walkthrough

1. Read `LengthComparator`, then instantiate it as `named`.
2. Compare that class with the anonymous implementation assigned to `anonymous`.
3. Identify the parameters and returned expression in `lambda`.
4. Follow `show`: its parameter is a `Comparator<? super String>`, independently of how the object was created. It accepts comparators for strings or their supertypes.

## What to observe and try

All three print `[fig, pear, banana]`. Each call creates a fresh array, so every implementation receives the same starting input.

Change all three rules to longest-first and compare the results. Then change just one rule to see why syntactic alternatives are equivalent only when they implement the same operation. Add an equal-length string and explain a zero comparison.

A lambda needs a target functional-interface type. Its concise syntax does not remove the comparison contract or turn it into an arbitrary untyped function.

## Open and run

Open **this `Demo09FunctionalComparators` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [LengthComparator.java](src/ds/LengthComparator.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Named class: [fig, pear, banana]
Anonymous class: [fig, pear, banana]
Lambda: [fig, pear, banana]
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
