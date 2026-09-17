# 02 Integer arrays

Abstract class, fixed and dynamic storage, one client algorithm.

**Slides:** 19–24; A2–A3 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Implement one integer-array contract using fixed storage and dynamically growing storage. Reuse the same client algorithms with both. Start after interfaces, abstract classes, inheritance, arrays and indexed loops.

## Guided walkthrough

1. Read `MyIntArray` to identify the operations clients can use.
2. Inspect `AbstractMyIntArray`: shared behaviour such as `contains` is expressed through the interface operations.
3. Compare the storage and `add` implementations of `FixedMyIntArray` and `DynamicMyIntArray`.
4. Follow `Main.fill` and `Main.show`. Neither needs to know how the argument stores its integers.
5. Step through adding a fourth element to arrays initially sized for three. The fixed implementation rejects it; the dynamic one grows.

## What to observe and try

Distinguish logical size from storage capacity. Replacing an existing element with `set` does not change size. A failed fixed-array insertion leaves the previous values intact.

Try a zero-capacity dynamic array, an invalid index and a negative capacity. Predict each result before running. Uncomment the abstract-class instantiation and private-field access separately to explain the compiler errors. Dynamic growth is an implementation choice; it does not change the client contract.

## Open and run

Open **this `Demo02IntArrays` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [AbstractMyIntArray.java](src/ds/AbstractMyIntArray.java)
- [ArrayAlgorithms.java](src/ds/ArrayAlgorithms.java)
- [DynamicMyIntArray.java](src/ds/DynamicMyIntArray.java)
- [FixedMyIntArray.java](src/ds/FixedMyIntArray.java)
- [MyIntArray.java](src/ds/MyIntArray.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Fixed: [4, 8, 12]; sum = 24
Dynamic: [4, 8, 12]; sum = 24
After set(1, 10): [4, 10, 12]
size = 3, contains(10) = true
Fixed add(16): IllegalStateException
Fixed still contains: [4, 10, 12]
Dynamic after add(16): [4, 8, 12, 16]; sum = 40
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
