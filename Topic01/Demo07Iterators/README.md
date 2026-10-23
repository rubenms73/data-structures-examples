# 07 Iterators

Iterating over the generic vector from Demo03, with independent positions and exhaustion.

**Slides:** 43–48 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Start from Demo03's generic vector and make it iterable. Extend `MyArray<E>` with `Iterable<E>`, and implement `iterator()` in `FixedMyArray<E>`. The iterator must be a **private inner class**, like the inner iterator in the Fibonacci example.

You should already know the generic-array example, interfaces, inner classes and the `Iterable` and `Iterator` contracts.

## What changes from Demo03

The storage, the capacity and the `add`, `get` and `set` operations are designed as before. `MyArray<E>` now extends `Iterable<E>`. Each call to `FixedMyArray.iterator()` creates a new `ArrayIterator` with its own `current` field, which starts at zero. Because it is a non-static inner class, it can access the private `data` and `size` fields of the vector that contains it.

The example has its own copies of these files rather than importing another project.

## Constructors

`FixedMyArray(int capacity)` creates an empty vector with the requested number of slots, as in Demo03. It allocates `data = (E[]) new Object[capacity]`, because Java does not allow `new E[capacity]`. The unchecked cast is confined to this constructor, and the backing field is still a private `E[]`. A capacity of zero is valid, whereas a negative capacity is rejected with `IllegalArgumentException` before the array is allocated.

`FixedMyArray(MyArray<? extends E> source)` is a conversion constructor. It delegates to `this(source.size())` and then uses an enhanced `for` loop to add the source elements in order, which is a practical use of the source's iterator. The new vector starts with all the copied elements and a capacity equal to their number, so it is already full. Thanks to the wildcard you can, for example, copy a `MyArray<Integer>` into a `FixedMyArray<Number>`.

The two vectors have separate backing arrays, so replacing an element in one does not change the other. The element references themselves are copied, including any `null` references, but mutable element objects are not duplicated. The earlier `FixedMyArray(E[] storage)` constructor is still there: it copies the storage array it receives but starts with a logical size of zero.

## Guided walkthrough

1. Follow the construction of `names` with `Ana` and `Ruben`, as in Demo03.
2. Create two iterators and look at their separate cursors.
3. Call `hasNext` twice. It reports whether an element is available without advancing.
4. Follow `next`. It checks that an element is available, returns the current element and advances.
5. Exhaust the first iterator. One more call to `next` throws `NoSuchElementException`, while the second iterator keeps its own position.
6. Run the enhanced `for` loop, which obtains a fresh iterator automatically. Create the integer vector with a capacity, copy it with the conversion constructor, and compute the sum over the copy.

## What to observe and try

Draw the vector once, with two iterator objects pointing to it. The positions belong to the iterators, not to the vector. The traversal stops at the logical size, not at the capacity.

Try an empty vector and a vector that contains a `null` element. `null` is a valid stored value, not an end marker. The iterator does not support `remove`. With this introductory implementation, do not modify the vector while a traversal is in progress; detecting concurrent modification is introduced only in the closing bags example.

## Open and run

Open **this `Demo07Iterators` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [MyArray.java](src/ds/MyArray.java)
- [FixedMyArray.java](src/ds/FixedMyArray.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Source vector: [Ana, Ruben]; size = 2
Two iterators over the same source keep independent positions:
hasNext(): true
hasNext() again: true
first.next(): Ana
first.next(): Ruben
second.next(): Ana
first.hasNext(): false
Next after the end: NoSuchElementException
A fresh enhanced for loop: Ana Ruben
Copy of scores: [8, 10]
Integer vector sum: 18
After copy.set(0, 99): copy[0] = 99; source[0] = 8
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

