# 05 Movies

A class implementing `Comparable`, plus rating and title comparators.

**Slides:** 33–40; additional example (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

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
