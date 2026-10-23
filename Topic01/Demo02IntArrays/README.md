# 02 Integer arrays

Abstract class, fixed and dynamic storage, one client algorithm.

**Slides:** 19–24; A2–A3 (printed slide numbers).

This folder is a complete Java project. You can copy it anywhere and run it without any other example or shared library; it needs only the Java standard library.

This teaching example does not deal with extremely large capacities; the array
grows by the simple geometric rule.

## Problem statement and prerequisites

Implement one integer-array contract twice, once with fixed storage and once with storage that grows dynamically, and reuse the same client algorithms with both. Study this example after interfaces, abstract classes, inheritance, arrays and indexed loops.

## Guided walkthrough

1. Read `MyIntArray` to find the operations that clients can use.
2. Look at `AbstractMyIntArray`. Shared behaviour, such as `contains`, is written in terms of the interface operations.
3. Compare the storage and `add` implementations of `FixedMyIntArray` and `DynamicMyIntArray`.
4. Follow `Main.fill` and `Main.show`. Neither of them needs to know how its argument stores the integers.
5. Step through the addition of a fourth element to arrays that start with room for three. The fixed implementation rejects it, whereas the dynamic one grows.

## What to observe and try

Keep the logical size and the storage capacity apart. Replacing an existing element with `set` does not change the size. When an insertion into the fixed array fails, the previous values are left intact.

Try a dynamic array with zero capacity, an invalid index and a negative capacity, and predict each result before you run the program. Uncomment the instantiation of the abstract class and the access to the private field one at a time, and explain each compiler error. Dynamic growth is an implementation choice; it does not change the contract that clients see.

## Open and run

Open **this `Demo02IntArrays` folder** in VS Code with JDK 17 or newer and the Extension Pack for Java. Open [src/app/Main.java](src/app/Main.java) and select **Run** or **Debug** above `main`.

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

- [AbstractMyIntArray.java](src/ds/AbstractMyIntArray.java)
- [ArrayAlgorithms.java](src/ds/ArrayAlgorithms.java)
- [DynamicMyIntArray.java](src/ds/DynamicMyIntArray.java)
- [FixedMyIntArray.java](src/ds/FixedMyIntArray.java)
- [MyIntArray.java](src/ds/MyIntArray.java)

Each example has its own sources and compiled output. Open each one as a separate project: combining their source folders would mix up repeated names such as `app.Main` and `ds.Movie`.

## Expected output

```text
Both arrays start with capacity 3 and size 0.
Fill the fixed array:
  add(4): [4]; size = 1
  add(8): [4, 8]; size = 2
  add(12): [4, 8, 12]; size = 3
Fill the dynamic array:
  add(4): [4]; size = 1
  add(8): [4, 8]; size = 2
  add(12): [4, 8, 12]; size = 3
Fixed: [4, 8, 12]; size = 3; sum = 24
Dynamic: [4, 8, 12]; size = 3; sum = 24
After set(1, 10): [4, 10, 12]
size = 3, contains(10) = true
Fixed add(16): IllegalStateException
Fixed still contains: [4, 10, 12]
Dynamic after add(16): [4, 8, 12, 16]; size = 4; sum = 40
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

