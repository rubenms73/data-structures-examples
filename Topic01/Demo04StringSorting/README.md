# 04 String sorting

Natural order, external order and `Arrays.sort`.

**Slides:** 32–37 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Sort the same array of strings by natural order and then by length. You should already know arrays, interfaces, method calls and what the result of a comparison means.

## Guided walkthrough

1. Run the direct call to `String.compareTo`. The sign of the result gives the order; comparison methods do not have to return exactly -1 or 1.
2. Follow `Arrays.sort(words)`, which uses the strings' natural order.
3. Read `LengthComparator.compare`, which compares integer lengths.
4. Follow `Arrays.sort(words, byLength)`, which is given an external comparison rule.

## What to observe and try

The original sequence is `pear, banana, fig`. Natural order gives `banana, fig, pear`; length order gives `fig, pear, banana`. Both calls rearrange the array they are given.

Add `plum` to create a length tie. Explain why a length comparator can return zero for two strings that are not equal. Swap the comparison arguments to sort the longest strings first. Uncomment the comparison with `<` to see why Java does not use that operator to order strings.

The example is about the comparison contract and the choice of ordering, not about the sorting algorithm used internally. The natural order of strings is not a locale-sensitive dictionary order.

## Open and run

Open **this `Demo04StringSorting` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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
pear.compareTo(banana): 14
A positive comparison places pear after banana alphabetically.
Original: [pear, banana, fig]
Natural order: [banana, fig, pear]
LengthComparator.compare(pear, banana): -1
A negative comparison places the shorter word first.
Length order: [fig, pear, banana]
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

