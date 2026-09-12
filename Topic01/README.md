# Topic 1 — Classroom examples

Data Structures · University of Oviedo · 2026–2027

Thirteen runnable Java demonstrations accompanying the current **Topic 1** presentation. Code and console output are in English. Each `Demo` class has its own `main` method.

## Open and run in VS Code

1. Open **this `Topic01` folder** using **File → Open Folder**.
2. Use an installed JDK 21 or newer and the **Extension Pack for Java**. Accept the extension recommendation if needed.
3. Open a class in `src/ds/topic01/demos` and click **Run** above `main`.
4. To step through a demonstration, set a breakpoint and click **Debug**. The named configurations are also available under **Run and Debug**.

If VS Code asks which runtime to use, select **Java: Configure Java Runtime** from the command palette. The project uses the Java standard library and contains no machine-specific runtime path.

## Run from Terminal on macOS or Linux

From this folder:

```sh
./run.sh list
./run.sh Demo01Rational
./run.sh Demo05Movies
./run.sh Demo10FunctionalComparators
./run.sh Demo12Wildcards
```

The script compiles the project before running the selected example. Use `./run.sh all` to run every demonstration, or `./run.sh test` to run the behaviour checks. Tests can also be run directly from `tests/ds/topic01/tests/ExampleChecks.java` in VS Code.

## Demonstrations

Slide numbers refer to the **number printed on the slide**, not the PDF page. The presentation has 59 main slides and an appendix numbered A1–A52.

| Demo | Subject | Slides |
| --- | --- | --- |
| [01 Rational](src/ds/topic01/demos/Demo01Rational.java) | Contract, interface, implementations, objects and references | 12, 16–18 |
| [02 Integer arrays](src/ds/topic01/demos/Demo02IntArrays.java) | Abstract class, fixed and dynamic storage, one client algorithm | 19–24; A2–A3 |
| [03 Generic arrays](src/ds/topic01/demos/Demo03GenericArrays.java) | Type parameters, generic methods and compile-time checks | 27–31 |
| [04 String sorting](src/ds/topic01/demos/Demo04StringSorting.java) | Natural order, external order and `Arrays.sort` | 32–37 |
| [05 Movies](src/ds/topic01/demos/Demo05Movies.java) | A class implementing `Comparable`, plus rating and title comparators | 33–40; additional example |
| [06 Maximum](src/ds/topic01/demos/Demo06Maximum.java) | One algorithm with two comparison rules | 38–39 |
| [07 Iterators](src/ds/topic01/demos/Demo07Iterators.java) | `IntRange`, independent positions and exhaustion | 43–48 |
| [08 Collections](src/ds/topic01/demos/Demo08Collections.java) | List/set behaviour and methods inherited from `AbstractCollection` | 49–52; A45–A46 |
| [09 Costs](src/ds/topic01/demos/Demo09Costs.java) | Counted additions and different costs behind `List.get` | 53–55 |
| [10 Functional comparators](src/ds/topic01/demos/Demo10FunctionalComparators.java) | Named class, anonymous class and lambda | A25–A29 |
| [11 Functional operations](src/ds/topic01/demos/Demo11FunctionalOperations.java) | `Predicate`, `Function`, `Consumer` and `Supplier` | A30; A42–A44 |
| [12 Wildcards](src/ds/topic01/demos/Demo12Wildcards.java) | `?`, `? extends Shape`, `? super Rectangle`, comparator reuse | A31–A36 |
| [13 Fibonacci](src/ds/topic01/demos/Demo13Fibonacci.java) | An iterator that computes a sequence | A39–A41 |

[ExpectedOutput.md](ExpectedOutput.md) contains the complete output of all demonstrations.

## Relationship to the slides

- `Rational` includes the observer operations used in the main sequence. These examples do not implement the appendix's extended factory/reduction interface. Both provided representations expose no mutators.
- `MyIntArray` and its three classes retain the hierarchy in the presentation. The abstract class supplies only `isEmpty` and `contains`; each concrete class owns its storage and implements `add`.
- `ArrayListMyArray` supplies a complete implementation for the generic client examples through composition. Its storage code is supporting material when introducing type parameters.
- `Maximum` and `FlexibleMaximum` are separate alternatives. Their `max` methods cannot both be added to the same class as overloads because their parameter types have the same erasure.
- `Movie` extends the examples without changing the presentation. Its titles and ratings are fictional. Its natural order is chronological, with title and rating as tie-breakers; equality uses the same three fields.
- The array/list cost explanation is an analysis of the implementations. The program counts additions in the sum loop; it does not claim to measure the execution time of `ArrayList` or `LinkedList`.

Intentional compilation errors are commented out. Uncomment one at a time, inspect the error, and undo the edit before continuing.

## References

- [VS Code: managing Java projects](https://code.visualstudio.com/docs/java/java-project).
- [Java API: Comparable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html), [Comparator](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html), [functional interfaces](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html).
- [Java API: Iterator](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Iterator.html), [AbstractCollection](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/AbstractCollection.html), [ArrayList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html), [LinkedList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/LinkedList.html).
- [GeeksforGeeks: Comparable vs Comparator](https://www.geeksforgeeks.org/java/comparable-vs-comparator-in-java/) supplied the suggested movie scenario. The code here was written for this project, with explicit tie-breaking and consistent equality.
