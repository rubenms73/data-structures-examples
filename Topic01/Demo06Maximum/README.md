# 06 Maximum

One algorithm with two comparison rules.

**Slides:** 38–39 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Write one generic algorithm that selects a maximum using a supplied comparator. Use it to find the alphabetically greatest string and the longest string. Prerequisites are generic methods, arrays, loops and comparators; the client also uses simple lambdas.

## Guided walkthrough

The signature is `public static <T> T max(T[] values, Comparator<? super T> order)`. A comparator consumes values of type `T`, so it may compare `T` or a supertype. For example, a `Comparator<Number>` can select the maximum of an `Integer[]`, while the returned value remains an `Integer`. This is the standard signature throughout these examples; Demo11 explains the wildcard in more detail.

1. Read the signature and preconditions of `Maximum.max`.
2. Trace the initial candidate and each comparison with the remaining values.
3. Compare the two calls in `Main`. The array stays the same while the comparison rule changes.
4. Follow the empty-array call and the exception handler.

## What to observe and try

For `pear, banana, fig`, alphabetical maximum is `pear` and longest is `banana`. The algorithm selects an element without sorting or modifying the array.

Try one element and two equally long strings. Check which tied value is retained by the strict comparison. Reverse the comparator and explain why the same method then selects a minimum under the original ordering.

The array, comparator and compared elements must satisfy the method's contract. An empty array has no maximum and is rejected explicitly. The traversal is linear in the number of elements when one comparator call has constant cost.

## Open and run

Open **this `Demo06Maximum` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Maximum.java](src/ds/Maximum.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Alphabetical maximum: pear
Longest word: banana
Original array: [pear, banana, fig]
Rejected: Empty array
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
