# 03 Generic arrays

Type parameters, generic methods and compile-time checks.

**Slides:** 27–31 (printed slide numbers).

The storage comes from `FixedMyArray`, which implements the local `MyArray<E>` interface with an ordinary array. The constructor `FixedMyArray(int capacity)` creates the internal array, and the vector starts with size zero. For example, `new FixedMyArray<String>(4)` can hold four strings. A second constructor takes a typed array, copies it and also starts with size zero. Adding to a full array throws `IllegalStateException`. You do not need to know `List` or `ArrayList` for this example.

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Generalise an array interface so that one implementation works with both strings and integers. Write a generic method that returns the first element with its correct static type. Before starting, you should know the integer-array example, interfaces, arrays and basic type parameters.

## Guided walkthrough

1. Read `MyArray<E>` and identify where the element type is used.
2. Read `FixedMyArray<E>`. The capacity constructor allocates `data = (E[]) new Object[capacity]`. Java does not allow `new E[capacity]`, so the unchecked cast is confined to this constructor and marked with `@SuppressWarnings("unchecked")`. The field is still declared as `E[]`, and the class keeps the array private. The capacity is the number of available slots, while the logical size starts at zero. The other constructor copies the typed array it receives.
3. Follow the two vectors in `Main`: `names` stores strings and `scores` stores integers.
4. Inspect `GenericAlgorithms.first`. Its type parameter connects the input's element type with its return type.
5. Compare the commented-out compilation errors with the deliberately invalid cast from `Object[]`, which fails only when the program runs.

## What to observe and try

Try adding an integer to `names`, and try assigning `names` to a `MyArray<Object>`; restore each comment before you continue. Then test an empty vector, the last valid index and a full vector. Elements may be `null`, and each `null` takes up a slot.

Try capacities of zero and one: a vector with capacity zero is already full, and one with capacity one accepts a single element. A negative capacity is rejected with `IllegalArgumentException` before the array is allocated. When you use the typed-array constructor, pass an array whose component type matches the chosen element type. This example has a fixed capacity and does not implement iteration yet; Demo07 extends the same vector design with an inner iterator class.

## Open and run

Open **this `Demo03GenericArrays` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [FixedMyArray.java](src/ds/FixedMyArray.java)
- [GenericAlgorithms.java](src/ds/GenericAlgorithms.java)
- [MyArray.java](src/ds/MyArray.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
MyArray<String>: Ana, Ruben; size = 2
MyArray<Integer>: 8, 10; size = 2
The same generic first() method preserves each element type:
First name: Ana
First score: 8
Object[] contents: [Ana, 8]
Attempt to cast the Integer at index 1 to String:
Object[] accepts mixed values; the wrong cast fails at run time.
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

