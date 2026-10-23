# 06 Maximum

One algorithm with two comparison rules.

**Slides:** 38–39 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Write one generic algorithm that selects the maximum according to a comparator it receives. Use it to find the alphabetically greatest string and the longest string. You should already know generic methods, arrays, loops and comparators; the client also uses simple lambdas.

## Guided walkthrough

The signature is `public static <T> T max(T[] values, Comparator<? super T> order)`. The comparator consumes values of type `T`, so it may be a comparator for `T` or for any supertype of `T`. For example, a `Comparator<Number>` can select the maximum of an `Integer[]`, while the returned value remains an `Integer`. This is the standard signature throughout these examples; Demo12 explains the wildcard in more detail.

1. Read the signature and preconditions of `Maximum.max`.
2. Trace the initial candidate and each comparison with the rest of the values.
3. Compare the two calls in `Main`. The array stays the same; only the comparison rule changes.
4. Follow the call with an empty array and the exception handler.

## What to observe and try

For `pear, banana, fig`, the alphabetical maximum is `pear` and the longest string is `banana`. The algorithm selects an element without sorting or modifying the array.

Try an array with one element, and then two strings of the same length. Check which of the tied values the strict comparison keeps. Reverse the comparator and explain why the same method then selects the minimum under the original order.

The array, the comparator and the elements being compared must all satisfy the method's contract. An empty array has no maximum, so it is rejected explicitly. If one call to the comparator takes constant time, the traversal is linear in the number of elements.

## Open and run

Open **this `Demo06Maximum` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Maximum.java](src/ds/Maximum.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Input: [pear, banana, fig]
The comparator determines what maximum means:
Alphabetical maximum: pear
Longest word: banana
Original array: [pear, banana, fig]
Request maximum of an empty array:
Rejected: Empty array
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

