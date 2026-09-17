# 04 String sorting

Natural order, external order and `Arrays.sort`.

**Slides:** 32–37 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Sort the same array of strings by natural order and by length. Prerequisites are arrays, interfaces, method calls and the role of a comparison result.

## Guided walkthrough

1. Run the direct call to `String.compareTo`. Its sign indicates order; comparison methods need not return exactly -1 or 1.
2. Follow `Arrays.sort(words)`, which uses the strings' natural order.
3. Read `LengthComparator.compare`, which compares integer lengths.
4. Follow `Arrays.sort(words, byLength)`, which receives an external comparison rule.

## What to observe and try

The original sequence is `pear, banana, fig`. Natural order gives `banana, fig, pear`; length order gives `fig, pear, banana`. Both calls rearrange the supplied array.

Add `plum` to create a length tie. Explain why two different strings can compare as zero under a length comparator without being equal strings. Reverse the comparison arguments to sort longest first. Uncomment the comparison with `<` to see why Java does not use that operator to order strings.

The focus is the comparison contract and the choice of ordering, not the internal sorting algorithm. Natural string order is not a locale-sensitive dictionary ordering.

## Open and run

Open **this `Demo04StringSorting` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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
pear.compareTo(banana): 14
Original: [pear, banana, fig]
Natural order: [banana, fig, pear]
Length order: [fig, pear, banana]
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
