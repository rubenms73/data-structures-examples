# Demo 04 — Lists, sets and an array-backed bag

## Problem statement

Store the same sequence of words in a list, a set and a bag, and compare which
occurrences each one keeps. Then implement a fixed snapshot collection by
extending `AbstractCollection<E>`: supply `size()` and an inner iterator, and
see which operations the abstract class provides automatically.

This example belongs to Topic 4, after sets and their library implementations.
It used to be kept outside the teaching sequence, in `DeferredExamples`.

## Prerequisites and position in the theory

From Topic 1 you need interfaces, generics, `Iterable`, inner iterators,
conversion constructors, copying references and `? extends E`; from Topic 2, how
lists behave and `ArrayList`; and from Topic 4, the `Set` contract, the
difference between `AbstractSet` and `AbstractCollection`, and the
insertion-order traversal of `LinkedHashSet`.

In the current Topic 4 PDF, read “Set in Java”, “AbstractCollection and
AbstractSet” and “Hash-based sets” first. The example
relies on the contract of the library's set; you do not need to know how hash
tables work inside, which is covered in Topic 5. It does not introduce map
operations or streams.

## Three collections, different contracts

| Collection | Duplicates | Traversal in this example | Modification |
| --- | --- | --- | --- |
| `ArrayList<String>` | Retained | List order | Supported |
| `LinkedHashSet<String>` | Equal words appear once | First insertion order | Supported |
| `ArrayBag<String>` | Retained | Order of the source when copied | Membership is fixed |

`Collection` gives the list and set variables a common type, but it is the
object created by each constructor that determines the actual behaviour. Because
the set's traversal order is stable, the printed comparison is reproducible; a
`HashSet` would not promise this order.

## Guided walkthrough

1. `Main` builds a list containing `pear`, `fig` and `pear`. `Arrays.asList`
   supplies the initial values, and the `ArrayList` constructor copies them into
   a mutable list.
2. The `LinkedHashSet` conversion constructor keeps the first occurrence of each
   word, so the second `pear` does not increase its size.
3. `ArrayBag` rejects a `null` source with an explicit conditional. It allocates
   its own `E[]` and copies every element reference from the source, including
   duplicates and `null` references. The source must not change during
   construction.
4. Clearing the source list does not empty the bag, because their storage is
   independent.
5. `size()` returns the length of the array, since every position belongs to the
   snapshot. The private `BagIterator` stores one index per traversal.
6. The inherited `contains` traverses the bag and compares values, and the
   inherited `isEmpty` uses `size`, so the class does not need to repeat either
   algorithm.
7. The inherited `add` throws `UnsupportedOperationException`, and removal
   through the iterator is not supported either. Calling `next` on an exhausted
   iterator throws `NoSuchElementException`.

Java cannot create `new E[n]` directly, so a single cast at allocation, with its
warning suppressed, creates the private generic backing array. Retrieving an
element needs no further cast.

## Copy semantics and optional operations

The bag fixes its membership, but it does not clone the element objects. If a
stored `StringBuilder` changes, the bag sees the same, changed object. The
implementation is therefore a shallow snapshot; it does not guarantee deep
immutability.

The bulk mutators inherited from `AbstractCollection` may throw an exception
when they need to add or remove an element. A call that changes nothing, such as
removing an absent element or clearing an empty bag, may return normally. The
example does not promise that every call to every modifying method throws; it
promises that the membership cannot change.

The bag inherits equality from `Object`, rather than list, set or multiset
equality, so two bags are not compared by their contents automatically. Defining
multiset equality would mean checking the number of occurrences and choosing a
matching hash-code contract, which is beyond this demonstration.

## Costs

For n source elements, construction takes O(n) time and O(n) storage, provided
that iterating over the source and asking for its size take constant time.
`size` and `isEmpty` are O(1). Each step of an iterator takes O(1), a complete
traversal takes O(n), and each iterator uses O(1) extra storage. The inherited
`contains` makes O(n) equality tests in the worst case. These bounds assume that
comparing two elements for equality takes constant time.

## Open, run and test

Open this folder in VS Code with JDK 17 or newer and the Extension Pack for Java,
and run `src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

From the Topic04 directory, use `bash run.sh Demo04Collections` or
`bash run.sh test`. The sources are in the `ds` and `app` packages, and the tests
in the `tests` package. The example is self-contained and uses only the Java
standard library.

The tests cover duplicates, `null` elements, a `null` source, independent
storage, shared element references, independent iterators, exhaustion, and the
unsupported insertion and removal. They also exercise the inherited conversion
and membership methods.

## Expected output

```text
List: pear fig pear
Set: pear fig
Same arrivals: list size = 3; set size = 2
list.add(pear): true; list = [pear, fig, pear, pear]
set.add(pear): false; set = [pear, fig]
Bag copied from the list: [pear, fig, pear, pear]; size = 4
Source after clear(): []; size = 0
Bag after clearing its source: pear fig pear pear
Inherited contains(fig): true
Inherited isEmpty(): false
Adding to the bag: UnsupportedOperationException
Bag after rejected add: [pear, fig, pear, pear]; size = 4
```

## Experiments

- Change the order of the source and look at the iteration order of the list,
  the set and the bag.
- Add a new word to the source after copying it, and explain why the bag does
  not change.
- Store a mutable element and change it after copying. Explain why the change
  can be seen through the bag.
- Trace the inherited `contains` through `iterator()`, `hasNext()` and `next()`.
- Compare removing an absent element with removing one that is present.

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
