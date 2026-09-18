# 03 Generic arrays

Type parameters, generic methods and compile-time checks.

**Slides:** 27–31 (printed slide numbers).

Storage is supplied by `FixedMyArray`, an implementation of the local `MyArray<E>` interface using an ordinary array. The constructor `FixedMyArray(int capacity)` creates the internal array and starts with size zero. For example, `new FixedMyArray<String>(4)` can hold four strings. An additional constructor accepts a typed array, copies it, and also starts with size zero. Adding to a full array throws `IllegalStateException`. This example requires no knowledge of `List` or `ArrayList`.

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Generalize an array interface so one implementation works with strings and integers. Write a generic method that returns the first element with its correct static type. Prerequisites are the integer-array example, interfaces, arrays and basic type parameters.

## Guided walkthrough

1. Read `MyArray<E>` and identify where the element type is used.
2. Read `FixedMyArray<E>`. The capacity constructor allocates `data = (E[]) new Object[capacity]`: Java does not allow `new E[capacity]`, so the unchecked cast is confined to this constructor with `@SuppressWarnings("unchecked")`. The field remains `E[]`, and the class keeps the array private. Capacity is the number of available slots; logical size starts at zero. The other constructor copies a supplied typed array.
3. Follow the two vectors in `Main`: `names` stores strings and `scores` stores integers.
4. Inspect `GenericAlgorithms.first`. Its type parameter connects the input's element type with its return type.
5. Compare the commented compilation errors with the deliberately invalid cast from `Object[]`, which fails only at execution.

## What to observe and try

Try adding an integer to `names` and assigning it to `MyArray<Object>`; restore each comment before continuing. Then test an empty vector, the last valid index and a full vector. Null elements are permitted and consume a slot.

Try capacities zero and one: a zero-capacity vector is already full, and a one-capacity vector accepts one element. A negative capacity is explicitly rejected with `IllegalArgumentException` before allocating the array. When using the typed-array constructor, supply an array whose component type matches the chosen element type. The example uses fixed capacity and does not implement iteration yet. Demo07 extends the vector design with an inner iterator class.

## Open and run

Open **this `Demo03GenericArrays` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [FixedMyArray.java](src/ds/FixedMyArray.java)
- [GenericAlgorithms.java](src/ds/GenericAlgorithms.java)
- [MyArray.java](src/ds/MyArray.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
First name: Ana
First score: 8
Object[] accepts mixed values; the wrong cast fails at run time.
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
