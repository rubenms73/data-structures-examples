# Data Structures — Classroom examples

University of Oviedo · Rubén Muñiz Sánchez

Runnable Java examples accompanying the course. Start with [Topic 1](Topic01/README.md): thirteen demonstrations of data abstraction, generic programming, comparison, iteration and functional interfaces.

## Run an example

1. Download this repository using **Code → Download ZIP**, or clone it with Git.
2. In VS Code, open an **individual example folder**, such as **Topic01/Demo05Movies**.
3. Use an installed JDK 17 or newer and the **Extension Pack for Java**.
4. Open `src/app/Main.java` and select **Run** above `main`.

Each example contains its own `ds` classes and its `app.Main` test program. You can copy any one example folder and run it independently. You can also set a breakpoint and select **Debug** to follow its execution.

On macOS or Linux, from the `Topic01` folder:

```sh
bash run.sh Demo05Movies
bash run.sh Demo09FunctionalComparators
bash run.sh Demo11Wildcards
bash run.sh test
```

## Explore Topic 1

- [All thirteen Topic 1 examples and their slide references](Topic01/README.md).
- [Movies: Comparable and Comparator](Topic01/Demo05Movies/README.md).
- [Named class, anonymous class and lambda](Topic01/Demo09FunctionalComparators/README.md).
- [Predicate, Function, Consumer and Supplier](Topic01/Demo10FunctionalOperations/README.md).
- [Wildcards with Shape and Rectangle](Topic01/Demo11Wildcards/README.md).
- [Closing example: immutable and mutable bags](Topic01/Demo13Bags/README.md).
- [Expected console output](Topic01/ExpectedOutput.md).

Comments identify small experiments that intentionally produce compilation errors. Uncomment one at a time, inspect the error, and restore the comment before continuing.

## Source style

When a method accepts a comparator to compare elements of type `T`, use `Comparator<? super T>` from the outset. Apply the same rule to concrete element types and stored comparator fields. Comparator implementations still declare their actual supported type, such as `implements Comparator<Movie>`.

Each example's README includes its problem statement, prerequisites, a guided code walkthrough, observations and experiments, execution instructions and expected output. Keep these explanations aligned with the implementation when adding or changing examples. Demo07 extends Demo03's generic vector with a private inner iterator; Demo12 uses the same inner-class pattern for a computed sequence. Demo13 is the closing example after the collection and iterator contracts have been introduced.

Apply these rules to all Java sources, including tests and future examples:

- Use **Allman style**: opening and closing block braces go on separate lines at the same indentation level, with four spaces per indentation level and no tabs.
- For `if` and `else`, omit braces when the branch contains a single statement. Put that statement on the next line, indented by four spaces.
- Keep braces around branches containing multiple statements, and whenever removing them would change which `if` an `else` belongs to.
- Keep braces around all loop bodies (`for`, enhanced `for`, `while` and `do`), including single-statement bodies.
- Preserve each example's packages and behaviour, and compile with Java 17.

Array initializer braces may remain inline because they delimit data rather than a code block.
