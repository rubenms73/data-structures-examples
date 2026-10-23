# 11 Functional operations

`Predicate`, `Function`, `Consumer` and `Supplier`.

**Slides:** A30; A42–A44 (printed slide numbers).

## Problem statement

Write a program that tests the four functional interfaces introduced in the presentation, first individually and then as parameters of a reusable algorithm.

| Interface | Method to call | Task |
| --- | --- | --- |
| `Predicate<Integer>` | `test` | Decide whether an integer is even; try both 4 and 5. |
| `Supplier<Integer>` | `get` | Produce the example value 4 without taking any arguments. |
| `Function<Integer, String>` | `apply` | Turn a number into a label such as `Number 4`. |
| `Consumer<String>` | `accept` | Print the label it receives, without returning a value. |

Use lambdas to provide the implementations. With the ordinary array `{1, 2, 3, 4, 5, 6}`, pass a predicate, a transformation and an action to `FunctionalOperations.process`. The method must visit each value and test it, and call the transformation and the action only for the values that pass the test. First print the even numbers, then reuse the same traversal to print the squares of the values greater than 4.

The supplier is tested with a direct call to `get()` before the traversal. The program uses no lists, sets, streams or other collection implementations. You should already know interfaces, type parameters, arrays, loops and lambda expressions. Read `src/app/Main.java` first, then follow the calls into `src/ds/FunctionalOperations.java`.

`Comparable` and `Comparator`, which the presentation also discusses, are put into practice in [Movies](../Demo05Movies/README.md) and [Functional comparators](../Demo10FunctionalComparators/README.md).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Guided walkthrough

1. Read the four interface variables in `Main` and identify their input and return types before you evaluate the lambdas.
2. Run each direct method call. The predicate is tested with an even input and an odd one, and the supplier produces the value that the function and the consumer then use.
3. Step into `FunctionalOperations.process`. For each array element, follow the predicate, and then the transformation and the action if the element is accepted.
4. Look at the second call. The same traversal now selects the values greater than four and prints their squares.

## What to observe and try

The first combined traversal processes 2, 4 and 6. The second processes 5 and 6 and produces the squares 25 and 36. The function returns a value, whereas the consumer performs a visible action and returns nothing. The supplier takes no argument, so it is demonstrated separately.

Change only the predicate so that it accepts odd numbers, and then only the transformation so that it labels cubes. Try an empty array and a predicate that is always `false`. Explain why rejected elements must never reach the transformation.

These examples use ordinary arrays, with no streams and no list implementations. The type parameters are instantiated with `Integer`, so the arithmetic expressions show boxing and unboxing at work. Do not put `null` entries in the array, because the arithmetic lambdas cannot handle them.

## Open and run

Open **this `Demo11FunctionalOperations` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [FunctionalOperations.java](src/ds/FunctionalOperations.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.FunctionalOperations`.

## Expected output

```text
Predicate.test(4): true
Predicate.test(5): false
Supplier.get(): 4
Function.apply(4): Number 4
Consumer.accept: Number 4

Input array: [1, 2, 3, 4, 5, 6]
process(): test each value, transform accepted values, then consume them.
Even values from an array:
Consumer.accept: Number 2
Consumer.accept: Number 4
Consumer.accept: Number 6
Values greater than 4, squared:
Consumer.accept: Square 25
Consumer.accept: Square 36
Input after both traversals: [1, 2, 3, 4, 5, 6]
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

