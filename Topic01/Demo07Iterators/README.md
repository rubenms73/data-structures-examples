# 07 Iterators

Iteration over the generic vector from Demo03, independent positions and exhaustion.

**Slides:** 43–48 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Start from Demo03's generic vector and make it iterable. Extend `MyArray<E>` with `Iterable<E>`, and implement `iterator()` in `FixedMyArray<E>`. The iterator must be a **private inner class**, just like the inner iterator in the Fibonacci example.

Prerequisites are the generic-array example, interfaces, inner classes and the `Iterable`/`Iterator` contracts.

## What changes from Demo03

The storage, capacity, `add`, `get` and `set` operations keep the same design. `MyArray<E>` now extends `Iterable<E>`. Each call to `FixedMyArray.iterator()` creates a new `ArrayIterator`, whose own `current` field starts at zero. As a non-static inner class, it can access its enclosing vector's private `data` and `size`.

The example includes its own copies of these files; it does not import another project.

## Constructors

`FixedMyArray(int capacity)` creates an empty vector with the requested number of slots, as in Demo03. It allocates `data = (E[]) new Object[capacity]`, because Java does not allow `new E[capacity]`. The unchecked cast is confined to this constructor, and the backing field remains a private `E[]`. Zero capacity is valid; negative capacity produces `NegativeArraySizeException`.

`FixedMyArray(MyArray<? extends E> source)` is a conversion constructor. It delegates with `this(source.size())`, then uses an enhanced `for` loop to add the source elements in order. This loop demonstrates a practical use of the source's iterator. The new vector starts with all copied elements and capacity equal to their number, so it is already full. The wildcard allows, for example, copying a `MyArray<Integer>` into a `FixedMyArray<Number>`.

The vectors have independent backing arrays: replacing an element in one does not change the other. The element references themselves are copied, including nulls; mutable element objects are not duplicated. The earlier `FixedMyArray(E[] storage)` constructor is also retained: it copies the supplied storage array but starts with logical size zero.

## Guided walkthrough

1. Follow construction of `names` with `Ana` and `Ruben`, as in Demo03.
2. Create two iterators and inspect their separate cursors.
3. Call `hasNext` twice: it reports availability without advancing.
4. Follow `next`: check availability, return the current element and advance.
5. Exhaust the first iterator. A further `next` throws `NoSuchElementException`; the second iterator still has its own position.
6. Run the enhanced `for` loop. It obtains a fresh iterator automatically. Create the integer vector with a capacity, copy it through the conversion constructor, and compute the sum over the copy.

## What to observe and try

Draw the vector once, with two iterator objects pointing to it. Their positions belong to the iterators, not to the vector. The traversal stops at logical size, not capacity.

Try an empty vector and a vector containing a null element. Null is a valid stored value and is not an end marker. Iterator `remove` is unsupported. For this introductory implementation, do not modify the vector during an active traversal; concurrent-modification detection is introduced only in the closing bags example.

## Open and run

Open **this `Demo07Iterators` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [MyArray.java](src/ds/MyArray.java)
- [FixedMyArray.java](src/ds/FixedMyArray.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
hasNext(): true
hasNext() again: true
first.next(): Ana
first.next(): Ruben
second.next(): Ana
first.hasNext(): false
Next after the end: NoSuchElementException
A fresh enhanced for loop: Ana Ruben
Integer vector sum: 18
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
