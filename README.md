# Data Structures — Classroom examples · 2026–2027

University of Oviedo · Rubén Muñiz Sánchez

Runnable Java examples accompanying the course. Start with [Topic 1](Topic01/README.md): thirteen demonstrations of data abstraction, generic programming, comparison, iteration and Java Collections.

## Run an example

1. Download this repository using **Code → Download ZIP**, or clone it with Git.
2. In VS Code, open the **Topic01 folder** inside the downloaded repository.
3. Use an installed JDK 21 or newer and the **Extension Pack for Java**.
4. Open a class in `src/ds/topic01/demos` and select **Run** above `main`.

Each demonstration runs independently. You can also set a breakpoint and select **Debug** to follow its execution.

On macOS or Linux, from the `Topic01` folder:

```sh
bash run.sh Demo05Movies
bash run.sh Demo10FunctionalComparators
bash run.sh Demo12Wildcards
bash run.sh test
```

## Explore Topic 1

- [All thirteen examples and their slide references](Topic01/README.md).
- [Movies: Comparable and Comparator](Topic01/src/ds/topic01/demos/Demo05Movies.java).
- [Named class, anonymous class and lambda](Topic01/src/ds/topic01/demos/Demo10FunctionalComparators.java).
- [Predicate, Function, Consumer and Supplier](Topic01/src/ds/topic01/demos/Demo11FunctionalOperations.java).
- [Wildcards with Shape and Rectangle](Topic01/src/ds/topic01/demos/Demo12Wildcards.java).
- [Expected console output](Topic01/ExpectedOutput.md).

Comments identify small experiments that intentionally produce compilation errors. Uncomment one at a time, inspect the error, and restore the comment before continuing.
