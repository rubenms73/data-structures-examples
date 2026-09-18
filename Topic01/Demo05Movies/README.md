# 05 Movies

A class implementing `Comparable`, plus rating and title comparators.

**Slides:** 33–40; additional example (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Problem statement and prerequisites

Give a domain class its natural order and provide two alternative orders without changing that class. Prerequisites are objects, interfaces, arrays, `Comparable` and `Comparator`.

## Guided walkthrough

1. Read the fields and constructor of `Movie`. Titles and ratings in this example are fictional.
2. Inspect `compareTo`: it compares year first, then uses title and rating to resolve ties.
3. Inspect `RatingComparator` and `TitleComparator` to follow their comparison rules and tie-breakers.
4. Follow the three sorts in `Main`. Every sort works on the same array.
5. Compare `equals` and `hashCode` with the natural-order definition.

## What to observe and try

Natural order begins with the oldest movies. Rating order puts the highest ratings first; title order follows titles. Trace the two movies with rating 8.6 to understand why an explicit tie-breaker matters.

Add two films from the same year, then two entries with the same title but different ratings. Predict the result under each comparator. Test a rating outside the accepted range and explain the constructor's rejection.

Natural ordering is part of the class's design; an external comparator lets a particular client choose another ordering. This program requires no list or set implementations.

## Open and run

Open **this `Demo05Movies` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Movie.java](src/ds/Movie.java)
- [RatingComparator.java](src/ds/RatingComparator.java)
- [TitleComparator.java](src/ds/TitleComparator.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Natural order: year, then title, then rating
  Blue Planet (2018, 8.6)
  Winter Lights (2018, 7.9)
  A Quiet Harbour (2020, 8.6)
  The Last Train (2022, 7.4)
Rating: highest first
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)
  The Last Train (2022, 7.4)
Title order
  A Quiet Harbour (2020, 8.6)
  Blue Planet (2018, 8.6)
  The Last Train (2022, 7.4)
  Winter Lights (2018, 7.9)
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
