# 05 Movies

A class implementing `Comparable`, plus rating and title comparators.

**Slides:** 33–40; additional example (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

## Problem statement and prerequisites

Give a domain class a natural order, and provide two alternative orders without changing that class. You should already know objects, interfaces, arrays, `Comparable` and `Comparator`.

## Guided walkthrough

1. Read the fields and constructor of `Movie`. Titles and ratings in this example are fictional.
2. Look at `compareTo`. It compares the year first, and then uses the title and the rating to break ties.
3. Read `RatingComparator` and `TitleComparator` to see their comparison rules and tie-breakers.
4. Follow the three sorts in `Main`. All of them work on the same array.
5. Compare `equals` and `hashCode` with the definition of the natural order.

## What to observe and try

The natural order starts with the oldest movies. The rating order puts the highest ratings first, and the title order sorts by title. Trace the two movies rated 8.6 to see why an explicit tie-breaker matters.

Add two films from the same year, then two entries with the same title but different ratings. Predict the result with each comparator. Then try a rating outside the accepted range and explain why the constructor rejects it.

The natural order is part of the design of the class, whereas an external comparator lets a particular client choose a different order. The program does not need any list or set implementation.

## Open and run

Open **this `Demo05Movies` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [Movie.java](src/ds/Movie.java)
- [RatingComparator.java](src/ds/RatingComparator.java)
- [TitleComparator.java](src/ds/TitleComparator.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text

Original arrival order (4 movies):
  The Last Train (2022, 7.4)
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)

Natural order: year, then title, then rating (4 movies):
  Blue Planet (2018, 8.6)
  Winter Lights (2018, 7.9)
  A Quiet Harbour (2020, 8.6)
  The Last Train (2022, 7.4)

Rating: highest first (4 movies):
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)
  The Last Train (2022, 7.4)

Title order (4 movies):
  A Quiet Harbour (2020, 8.6)
  Blue Planet (2018, 8.6)
  The Last Train (2022, 7.4)
  Winter Lights (2018, 7.9)
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

