# 11 Functional operations

`Predicate`, `Function`, `Consumer` and `Supplier`.

**Slides:** A30; A42–A44 (printed slide numbers).

## Problem statement

Write a program that tests the four functional interfaces introduced in the presentation, first individually and then as parameters of a reusable algorithm.

| Interface | Method to call | Task |
| --- | --- | --- |
| `Predicate<Integer>` | `test` | Determine whether an integer is even; try both 4 and 5. |
| `Supplier<Integer>` | `get` | Produce the example value 4 without receiving arguments. |
| `Function<Integer, String>` | `apply` | Transform a number into a label such as `Number 4`. |
| `Consumer<String>` | `accept` | Print the supplied label; return no value. |

Use lambdas to supply the implementations. With the ordinary array `{1, 2, 3, 4, 5, 6}`, pass a predicate, a transformation and an action to `FunctionalOperations.process`. The method must visit each value, test it, and invoke the transformation and action only for accepted values. First print the even numbers; then reuse the same traversal to print the squares of values greater than 4.

The supplier is tested through a direct `get()` call before the traversal. The program uses no lists, sets, streams or collection implementations. Prerequisites are interfaces, type parameters, arrays, loops and lambda expressions. Read `src/app/Main.java` first, then follow the calls into `src/ds/FunctionalOperations.java`.

`Comparable` and `Comparator`, also discussed in the presentation, are exercised in [Movies](../Demo05Movies/README.md) and [Functional comparators](../Demo10FunctionalComparators/README.md).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Open and run

Open **this `Demo11FunctionalOperations` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [FunctionalOperations.java](src/ds/FunctionalOperations.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.FunctionalOperations`.

## Expected output

```text
Predicate.test(4): true
Predicate.test(5): false
Supplier.get(): 4
Function.apply(4): Number 4
Consumer.accept: Number 4
Even values from an array:
Consumer.accept: Number 2
Consumer.accept: Number 4
Consumer.accept: Number 6
Values greater than 4, squared:
Consumer.accept: Square 25
Consumer.accept: Square 36
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
