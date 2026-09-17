# 03 Generic arrays

Type parameters, generic methods and compile-time checks.

**Slides:** 27–31 (printed slide numbers).

Storage is supplied by `FixedMyArray`, an implementation of the local `MyArray<E>` interface using an ordinary array. The constructor takes an array of the intended element type to determine capacity, copies it, and starts with size zero. For example, `new FixedMyArray<>(new String[4])` can hold four strings. Adding to a full array throws `IllegalStateException`. This example requires no knowledge of `List` or `ArrayList`.

This folder is a complete Java project. You can copy it anywhere and run it without another example or a shared library. Only the Java standard library is required.

## Open and run

Open **this `Demo03GenericArrays` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [FixedMyArray.java](src/ds/FixedMyArray.java)
- [GenericAlgorithms.java](src/ds/GenericAlgorithms.java)
- [MyArray.java](src/ds/MyArray.java)

Each example owns its sources and compiled output. Open examples as separate projects: combining their source folders would mix repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
First name: Ana
First score: 8
Object[] accepts mixed values; the wrong cast fails at run time.
```

Commented compilation errors are intentional exercises. Restore each comment before continuing.
