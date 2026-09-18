# 11 Wildcards

This example uses the local `MyArray<E>` interface and `FixedMyArray<E>` implementation from the generic-arrays example, included here so the project remains self-contained. It demonstrates `MyArray<?>`, `MyArray<? extends Shape>` and `MyArray<? super Rectangle>` without requiring Java Collections. The same wildcard rules apply later to collection interfaces.

`?`, `? extends Shape`, `? super Rectangle`, comparator reuse.

**Slides:** A31–A36 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Use the generic vector from Demo03 to demonstrate unknown, upper-bounded and lower-bounded element types. Reuse a comparator for shapes when selecting a rectangle. Prerequisites are generics, inheritance, the local `MyArray` contract and comparators.

## Guided walkthrough

1. Compare `printCount(MyArray<?>)` with a method restricted to `MyArray<Object>`. The former accepts vectors of strings or rectangles.
2. Read a rectangle through a `MyArray<? extends Shape>` view. The result can be used as a `Shape`, but the unknown actual type prevents arbitrary shape insertions.
3. Trace `ShapeAlgorithms.totalArea` with rectangles and squares.
4. Follow `FlexibleMaximum.max`: a `Comparator<Shape>` can compare rectangles, while the result remains statically a `Rectangle`.
5. Inspect `addRectangle(MyArray<? super Rectangle>)`. It can add rectangles and squares to suitable destinations, but reads only as `Object`.

## What to observe and try

Uncomment each invalid assignment or call separately, predict the compiler error, then restore it. Explain why a circle cannot be added through the lower-bounded rectangle view and why a vector of rectangles is not a vector of shapes.

The wildcard changes what a reference permits, not the runtime object or its contents. An upper-bounded view is not an immutable copy. Both destinations must have room for the two additions. Supporting vector classes are included locally so Java Collections are not prerequisites.

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

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install a JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
