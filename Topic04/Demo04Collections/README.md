# Demo 04 — Lists, sets and an array-backed bag

## Problem statement

Store the same sequence of words in a list, a set and a bag. Compare which
occurrences each retains. Then implement a fixed snapshot collection by extending
`AbstractCollection<E>`, supplying `size()` and an inner iterator, and observing
which operations the abstract class supplies automatically.

This example belongs to Topic 4, after sets and their library implementations.
It was previously held outside the teaching sequence in `DeferredExamples`.

## Prerequisites and position in the theory

Topic 1: interfaces, generics, `Iterable`, inner iterators, conversion constructors,
reference copying and `? extends E`. Topic 2: list behaviour and `ArrayList`.
Topic 4: the Set contract, `AbstractSet` versus `AbstractCollection`, and
`LinkedHashSet`'s insertion-order traversal.

In the current Topic 4 PDF, read “Set in Java”, “AbstractCollection and
AbstractSet” and “Hash-based sets” first (PDF pages 5, 9 and 34). The example
uses the library set's contract; knowledge of hash-table internals from Topic 5
is not required. It does not introduce map operations or streams.

## Three collections, different contracts

| Collection | Duplicates | Traversal in this example | Modification |
| --- | --- | --- | --- |
| `ArrayList<String>` | Retained | List order | Supported |
| `LinkedHashSet<String>` | Equal words appear once | First insertion order | Supported |
| `ArrayBag<String>` | Retained | Order of the source when copied | Membership is fixed |

`Collection` provides a common type for the list and set variables. The object
created by each constructor determines the actual behaviour. The set's stable
traversal makes the printed comparison reproducible; `HashSet` would not promise
this order.

## Guided walkthrough

1. `Main` builds a list containing pear, fig and pear. `Arrays.asList` supplies
   initial values; the `ArrayList` constructor copies them into a mutable list.
2. The `LinkedHashSet` conversion constructor retains the first occurrence of
   each word. The second pear does not increase its size.
3. `ArrayBag` rejects a null source with an explicit conditional. It allocates
   its own `E[]` and copies every source element reference, including duplicates
   and nulls. The source must remain unchanged during construction.
4. Clearing the source list does not empty the bag: their storage is independent.
5. `size()` returns the array length, since all its positions belong to the
   snapshot. The private `BagIterator` stores one index per traversal.
6. The inherited `contains` traverses the bag and compares values. The inherited
   `isEmpty` uses `size`. The class does not need to repeat either algorithm.
7. The inherited `add` throws `UnsupportedOperationException`. Iterator removal
   is also unsupported. An exhausted `next` throws `NoSuchElementException`.

Java cannot create `new E[n]` directly. One suppressed cast at allocation creates
the private generic backing array. Retrieving an element requires no further cast.

## Copy semantics and optional operations

The bag fixes its membership, but it does not clone element objects. If a stored
`StringBuilder` changes, the bag sees that same changed object. The implementation
is therefore a shallow snapshot, not a guarantee of deep immutability.

Bulk mutators inherited from `AbstractCollection` may throw when they need to
add or remove an element. A no-op, such as removing an absent element or clearing
an empty bag, may return normally. This example does not promise that every call
to every modifying method throws. It promises that membership cannot change.

The bag inherits Object equality rather than list equality, set equality or
multiset equality. Two bags are not compared by their contents automatically.
Defining multiset equality would require checking occurrence counts and choosing
a matching hash-code contract; that is outside this demonstration.

## Costs

For n source elements, construction takes O(n) time and O(n) storage, assuming
constant-time source iteration and size. `size` and `isEmpty` are O(1).
Each iterator step takes O(1), a complete traversal takes O(n), and each iterator
uses O(1) additional storage. Inherited `contains` takes O(n) equality tests in
the worst case. These bounds assume constant-time element equality.

## Open, run and test

Open this folder in VS Code with JDK 17 or newer and the Extension Pack for Java.
Run `src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

From the Topic04 directory, use `bash run.sh Demo04Collections` or
`bash run.sh test`. Sources use packages `ds` and `app`; tests use package `tests`.
The example is self-contained and uses only the Java standard library.

Tests cover duplicates, null elements, a null source, independent storage,
shared element references, independent iterators, exhaustion and unsupported
insertion/removal. They also exercise inherited conversion and membership methods.

## Expected output

```text
List: pear fig pear
Set: pear fig
Bag after clearing its source: pear fig pear
Inherited contains(fig): true
Inherited isEmpty(): false
Adding to the bag: UnsupportedOperationException
```

## Experiments

- Change the source order and observe the list, set and bag iteration order.
- Add a new word to the source after copying it. Explain why the bag is unchanged.
- Store a mutable element and change it after copying. Explain why that change
  is visible through the bag.
- Trace inherited `contains` through `iterator()`, `hasNext()` and `next()`.
- Compare removing an absent element with removing an element that is present.

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install a JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
