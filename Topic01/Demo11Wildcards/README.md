# 11 Wildcards

This example uses the local `MyArray<E>` interface and `FixedMyArray<E>` implementation from the generic-arrays example, included here so the project remains self-contained. It demonstrates `MyArray<?>`, `MyArray<? extends Shape>` and `MyArray<? super Rectangle>` without requiring Java Collections. The same wildcard rules apply later to collection interfaces.

`?`, `? extends Shape`, `? super Rectangle`, comparator reuse.

**Slides:** A31–A36 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Open and run

Open **this `Demo11Wildcards` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Circle.java](src/ds/Circle.java)
- [FlexibleMaximum.java](src/ds/FlexibleMaximum.java)
- [Rectangle.java](src/ds/Rectangle.java)
- [Shape.java](src/ds/Shape.java)
- [ShapeAlgorithms.java](src/ds/ShapeAlgorithms.java)
- [Square.java](src/ds/Square.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Count: 2
Count: 2
Read through ? extends Shape: 6.0
Total rectangle area: 16.0
Total square area: 13.0
Rectangle comparison: -1
Largest rectangle area: 10.0
Largest shape area: 10.0
Direct wildcard lambda: -1
Destination sizes: 2, 3
First Object destination element: Existing text
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
