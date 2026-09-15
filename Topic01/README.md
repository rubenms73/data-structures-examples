# Topic 1 — Classroom examples

Data Structures · University of Oviedo

Thirteen self-contained Java projects accompany the Topic 1 presentation. Each folder contains its own `ds` package and an `app.Main` test program. Examples share no source folders, compiled classes or external libraries.

## Open one example

1. Choose a folder below, for example **Demo05Movies**.
2. Open **that example folder** in VS Code, with JDK 17 or newer and the Extension Pack for Java.
3. Open `src/app/Main.java` and select **Run** or **Debug** above `main`.

You can copy a single example folder to another location. Its README includes slide references, commands, class links and expected output. Classes needed in two examples, such as `Movie`, are included in both.

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

Slide numbers refer to the **number printed on the slide**, not the PDF page. The presentation has 59 main slides and an appendix numbered A1–A52.

| Demo | Subject | Slides |
| --- | --- | --- |
| [01 Rational](Demo01Rational/README.md) | Contract, interface, implementations, objects and references | 12, 16–18 |
| [02 Integer arrays](Demo02IntArrays/README.md) | Abstract class, fixed and dynamic storage, one client algorithm | 19–24; A2–A3 |
| [03 Generic arrays](Demo03GenericArrays/README.md) | Type parameters, generic methods and compile-time checks | 27–31 |
| [04 String sorting](Demo04StringSorting/README.md) | Natural order, external order and `Arrays.sort` | 32–37 |
| [05 Movies](Demo05Movies/README.md) | A class implementing `Comparable`, plus rating and title comparators | 33–40; additional example |
| [06 Maximum](Demo06Maximum/README.md) | One algorithm with two comparison rules | 38–39 |
| [07 Iterators](Demo07Iterators/README.md) | `IntRange`, independent positions and exhaustion | 43–48 |
| [08 Collections](Demo08Collections/README.md) | List/set behaviour and methods inherited from `AbstractCollection` | 49–52; A45–A46 |
| [09 Costs](Demo09Costs/README.md) | Counted additions and different costs behind `List.get` | 53–55 |
| [10 Functional comparators](Demo10FunctionalComparators/README.md) | Named class, anonymous class and lambda | A25–A29 |
| [11 Functional operations](Demo11FunctionalOperations/README.md) | `Predicate`, `Function`, `Consumer` and `Supplier` | A30; A42–A44 |
| [12 Wildcards](Demo12Wildcards/README.md) | `?`, `? extends Shape`, `? super Rectangle`, comparator reuse | A31–A36 |
| [13 Fibonacci](Demo13Fibonacci/README.md) | An iterator that computes a sequence | A39–A41 |

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
