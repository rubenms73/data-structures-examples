# 10 Functional comparators

Named class, anonymous class and lambda.

**Slides:** A25–A29 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Express the same string-length comparator in three ways: a named class, an anonymous class and a lambda. Use the same client method with all three. You should already know the string-sorting example, interfaces and the introduction to anonymous classes and lambdas.

## Guided walkthrough

1. Read `LengthComparator`, then instantiate it as `named`.
2. Compare that class with the anonymous implementation assigned to `anonymous`.
3. Identify the parameters and the returned expression in `lambda`.
4. Follow `show`. Its parameter is a `Comparator<? super String>`, however the object was created, so it accepts comparators for strings or for any of their supertypes.

## What to observe and try

All three print `[fig, pear, banana]`. Each call creates a fresh array, so every implementation starts from the same input.

Change all three rules to longest-first and compare the results. Then change just one rule, to see that different syntax gives equivalent results only when it implements the same operation. Add a string of the same length as another and explain why the comparison returns zero.

A lambda needs a target type, which must be a functional interface. Its concise syntax does not remove the comparison contract, nor does it turn the comparator into an arbitrary untyped function.

## Open and run

Open **this `Demo10FunctionalComparators` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [LengthComparator.java](src/ds/LengthComparator.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Three implementations of Comparator<String>, all ordering by length:

Named class input: [pear, banana, fig]
compare(pear, banana): -1
Named class: [fig, pear, banana]

Anonymous class input: [pear, banana, fig]
compare(pear, banana): -1
Anonymous class: [fig, pear, banana]

Lambda input: [pear, banana, fig]
compare(pear, banana): -1
Lambda: [fig, pear, banana]
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

