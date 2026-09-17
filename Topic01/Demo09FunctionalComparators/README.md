# 09 Functional comparators

Named class, anonymous class and lambda.

**Slides:** A25–A29 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Open and run

Open **this `Demo09FunctionalComparators` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [LengthComparator.java](src/ds/LengthComparator.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Named class: [fig, pear, banana]
Anonymous class: [fig, pear, banana]
Lambda: [fig, pear, banana]
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
