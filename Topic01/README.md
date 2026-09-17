# Topic 1 — Classroom examples

Data Structures · University of Oviedo

Thirteen self-contained Java projects accompany the Topic 1 presentation. Each folder contains its own `ds` package and an `app.Main` test program. Examples share no source folders, compiled classes or external libraries.

## Open one example

1. Choose a folder below, for example **Demo05Movies**.
2. Open **that example folder** in VS Code, with JDK 17 or newer and the Extension Pack for Java.
3. Open `src/app/Main.java` and select **Run** or **Debug** above `main`.

You can copy a single example folder to another location. Its README includes slide references, commands, class links and expected output. Classes needed in two examples, such as `FixedMyArray`, are included in both.

On macOS or Linux, from an individual example folder:

```sh
bash run.sh
bash run.sh test
```

## Run from the Topic01 folder

The optional launcher builds each project separately:

```sh
bash run.sh list
bash run.sh Demo05Movies
bash run.sh all
bash run.sh test
```

Do not combine all `src` folders into one Java project: names such as `app.Main` intentionally repeat. Each example has its own `bin` directory and VS Code configuration.

## Demonstrations

Examples 01–12 use ordinary arrays and the local `MyArray` interface rather than Java collection implementations. The earlier list/set `Collections` demo remains [reserved for later](../DeferredExamples/README.md). Example 13 is a closing exercise using `Collection` and `AbstractCollection`, after those interfaces and iterator operations have been explained. The examples are numbered consecutively from 01 to 13. Start with the basic interface and array examples, then comparison and iteration. The functional-interface and wildcard examples correspond to the appendix and should follow their theory explanations.

For a step-by-step test of `Predicate`, `Supplier`, `Consumer` and `Function`, see [Demo10FunctionalOperations](Demo10FunctionalOperations/README.md). Its README includes the problem statement, each interface's operation and the expected output.

Slide numbers refer to the **number printed on the slide**, not the PDF page. The presentation has 59 main slides and an appendix numbered A1–A52.

| Demo | Subject | Slides |
| --- | --- | --- |
| [01 Rational](Demo01Rational/README.md) | Contract, interface, implementations, objects and references | 12, 16–18 |
| [02 Integer arrays](Demo02IntArrays/README.md) | Abstract class, fixed and dynamic storage, one client algorithm | 19–24; A2–A3 |
| [03 Generic arrays](Demo03GenericArrays/README.md) | Type parameters, generic methods and compile-time checks | 27–31 |
| [04 String sorting](Demo04StringSorting/README.md) | Natural order, external order and `Arrays.sort` | 32–37 |
| [05 Movies](Demo05Movies/README.md) | A class implementing `Comparable`, plus rating and title comparators | 33–40; additional example |
| [06 Maximum](Demo06Maximum/README.md) | One algorithm with two comparison rules | 38–39 |
| [07 Iterators](Demo07Iterators/README.md) | Generic vector, private inner iterator, independent positions and exhaustion | 43–48 |
| [08 Costs](Demo08Costs/README.md) | Counted additions versus direct array access | 53–55 |
| [09 Functional comparators](Demo09FunctionalComparators/README.md) | Named class, anonymous class and lambda | A25–A29 |
| [10 Functional operations](Demo10FunctionalOperations/README.md) | `Predicate`, `Function`, `Consumer` and `Supplier` | A30; A42–A44 |
| [11 Wildcards](Demo11Wildcards/README.md) | `?`, `? extends Shape`, `? super Rectangle`, comparator reuse | A31–A36 |
| [12 Fibonacci](Demo12Fibonacci/README.md) | An iterator that computes a sequence | A39–A41 |
| [13 Bags](Demo13Bags/README.md) | Immutable and mutable objects, bag equality, iterator removal and sorted storage | Closing exercise after Collection and AbstractCollection |

[ExpectedOutput.md](ExpectedOutput.md) contains the complete output of all demonstrations.

## Relationship to the slides

- `Rational` includes the observer operations used in the main sequence. These examples do not implement the appendix's extended factory/reduction interface. Both provided representations expose no mutators.
- `MyIntArray` and its three classes retain the hierarchy in the presentation. The abstract class supplies only `isEmpty` and `contains`; each concrete class owns its storage and implements `add`.
- `FixedMyArray` supplies fixed-capacity storage using an ordinary array. It implements the local `MyArray` interface without requiring Java Collections.
- `Maximum` and `FlexibleMaximum` are separate alternatives. Their `max` methods cannot both be added to the same class as overloads because their parameter types have the same erasure.
- `Movie` extends the examples without changing the presentation. Its titles and ratings are fictional. Its natural order is chronological, with title and rating as tie-breakers; equality uses the same three fields.
- The cost example counts additions in an array traversal and contrasts them with direct array access. It does not introduce linked lists.

Intentional compilation errors are commented out. Uncomment one at a time, inspect the error, and undo the edit before continuing.

## References

- [VS Code: managing Java projects](https://code.visualstudio.com/docs/java/java-project).
- [Java API: Comparable](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Comparable.html), [Comparator](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Comparator.html), [functional interfaces](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/function/package-summary.html).
- [Java API: Iterator](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Iterator.html), [AbstractCollection](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/AbstractCollection.html), [ArrayList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/ArrayList.html), [LinkedList](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/LinkedList.html).
- [GeeksforGeeks: Comparable vs Comparator](https://www.geeksforgeeks.org/java/comparable-vs-comparator-in-java/) supplied the suggested movie scenario. The code here was written for this project, with explicit tie-breaking and consistent equality.
