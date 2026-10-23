# 12 Wildcards

`?`, `? extends Shape`, `? super Rectangle`, comparator reuse.

**Slides:** A31–A36 (printed slide numbers).

This example uses the local `MyArray<E>` interface and its `FixedMyArray<E>` implementation from the generic-arrays example; a copy is included here so that the project stays self-contained. It demonstrates `MyArray<?>`, `MyArray<? extends Shape>` and `MyArray<? super Rectangle>` without relying on the Java Collections Framework. The same wildcard rules apply later to the collection interfaces.

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Use the generic vector from Demo03 to demonstrate unknown, upper-bounded and lower-bounded element types, and reuse a comparator for shapes to select a rectangle. You should already know generics, inheritance, the local `MyArray` contract and comparators.

## Guided walkthrough

1. Compare `printCount(MyArray<?>)` with a method restricted to `MyArray<Object>`. The first one accepts vectors of strings or of rectangles.
2. Read a rectangle through a `MyArray<? extends Shape>` view. The result can be used as a `Shape`, but because the actual type is unknown you cannot insert an arbitrary shape.
3. Trace `ShapeAlgorithms.totalArea` with rectangles and squares.
4. Follow `FlexibleMaximum.max`. A `Comparator<Shape>` can compare rectangles, and the static type of the result is still `Rectangle`.
5. Look at `addRectangle(MyArray<? super Rectangle>)`. It can add rectangles and squares to any suitable destination, but anything it reads has type `Object`.

## What to observe and try

Uncomment each invalid assignment or call on its own, predict the compiler error, and then restore the comment. Explain why a circle cannot be added through the lower-bounded rectangle view and why a vector of rectangles is not a vector of shapes.

A wildcard changes what you may do through a reference, not the object at run time or its contents. An upper-bounded view is not an immutable copy. Both destinations must have room for the two additions. The supporting vector classes are included locally, so you do not need to know the Java Collections Framework yet.

## Open and run

Open **this `Demo12Wildcards` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Circle.java](src/ds/Circle.java)
- [FlexibleMaximum.java](src/ds/FlexibleMaximum.java)
- [Rectangle.java](src/ds/Rectangle.java)
- [Shape.java](src/ds/Shape.java)
- [ShapeAlgorithms.java](src/ds/ShapeAlgorithms.java)
- [Square.java](src/ds/Square.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Rectangles: areas 6.0, 10.0
Unbounded wildcard: inspect size without knowing the element type.
Count: 2
Count: 2

? extends Shape: read shapes from a rectangle array.
Read through ? extends Shape: 6.0
Total rectangle area: 16.0
Total square area: 13.0

? super Rectangle: reuse a comparator that accepts any Shape.
Rectangle comparison: -1
Largest rectangle area: 10.0
Largest shape area: 10.0
Direct wildcard lambda: -1

? super Rectangle: add rectangles and squares to Shape/Object destinations.
Destination sizes: 2, 3
First Object destination element: Existing text
Added Shape destination areas: 6.0, 4.0
Object destination element types: String, Rectangle, Square
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

