# 13 Bags: immutable and mutable objects

## Problem statement

Implement three kinds of bag (multiset): `ImmutableBag`, `MutableBag` and `SortedMutableBag`. A bag permits repeated elements. Its equality depends on the elements and their multiplicities, not on iteration order.

Use this example **at the end of Topic 1**, after explaining generic interfaces, inheritance, `Collection`, `AbstractCollection`, `Iterable`, `Iterator` and `Comparator`. Text-file reading is supporting code; it is not necessary to understand the bag implementations. This is an additional closing exercise, not a numbered slide example.

1. Construct an immutable bag and produce new bags with `withAdded` and `withoutOne`. Show that the original stays unchanged, as in the distinction between `String` and `StringBuffer`.
2. Construct a mutable bag, then add and remove elements from that same object.
3. Iterate and remove elements correctly. Test inherited operations such as `removeAll`, `retainAll` and `removeIf`.
4. Maintain sorted iteration using natural order or a supplied comparator. A failed comparison during `add` must leave the bag unchanged.
5. Compare bags across implementations and count occurrences of nested bags. Equal contents in a different order must still compare equal.
6. Read `text.txt`, store its words in a sorted bag, and count `the` and `for`.

## Contracts and design

- `AbstractBag` extends `AbstractCollection` and shares `occurrences`, `equals` and `hashCode`. Equality is restricted to this bag hierarchy to avoid asymmetric equality with lists and sets. The simple counting implementation of equality is quadratic; no map is required.
- `ImmutableBag` is final, copies its input storage, exposes no mutable backing array and rejects all in-place mutators, even no-op calls. `withoutOne` removes only one occurrence and may return the same instance if no occurrence exists.
- Immutability is **structural**: element objects are not deep-copied. A mutable element can still change. Use immutable elements when stable values and hash codes matter. Do not mutate a bag used as a hash key, or elements affecting its equality. Cyclic self-containing bags are outside this exercise's equality contract.
- `MutableBag` uses a resizable array. Iterator removal adjusts the cursor and clears the unused slot. Independent iterators have independent positions. Structural changes outside an iterator cause `ConcurrentModificationException`; this is diagnostic behaviour, not thread safety.
- `SortedMutableBag` extends `MutableBag`. It computes the insertion position before mutation, including comparison validation for the first element. Without a comparator, elements must be mutually comparable. Comparators must obey their usual ordering contract and must not mutate the bag. Do not change an element's ordering fields while it is stored. Comparator equivalence does not define bag equality: `equals` does.
- All three reject null elements. Mutating bulk operations on mutable bags may complete partially if a later element fails; the exception guarantee above concerns one `add` operation.

## Open and run

Open this example folder in VS Code using JDK 17 or newer and the Extension Pack for Java. Run `src/app/Main.java`. The local `text.txt` is used by default; an optional command-line argument selects another UTF-8 file.

On macOS or Linux:

```sh
bash run.sh
bash run.sh test
```

From this folder, also in Windows PowerShell:

```sh
javac --release 17 -encoding UTF-8 -d bin src/ds/*.java src/app/*.java
java -cp bin app.Main
java -cp bin app.Main text.txt
```

The example is self-contained. Teaching classes are in `src/ds`, the runnable client in `src/app`, and automated checks in `tests/tests`. Test code uses standard-library collections as reference implementations; follow `app.Main` for the teaching sequence. Code uses Allman braces, four spaces and unbraced single-statement conditional branches; loops retain braces.

## Text processing

A word is a maximal sequence of Unicode letters. Punctuation, digits, hyphens and apostrophes separate words; for example, `Spider-Man` produces two words. Case conversion uses `Locale.ROOT`, reading uses UTF-8, and the scanner is closed automatically. The supplied text is preserved from the original PA-1 material.

## Expected output

```text
Immutable original: [2, -3, 2, 18]
New immutable bag: [2, -3, 2, 18, 7]
Without one 2: [-3, 2, 18]
Mutable after add and remove: [-3, 2, 18, 7]
Equal bags (same multiplicities): 2
Sorted: [-3, 2, 2, 18]
Same bag despite order: true
Words: 322
Occurrences of the: 15
Occurrences of for: 1
```

## Changes from the old PA-1 material

`BagImmu`, `BagMut` and `BagSorted` become `ImmutableBag`, `MutableBag` and `SortedMutableBag`. The iterator cursor and reference-retention bugs are corrected, failed sorted insertions no longer modify the bag, equality and hashing respect multiplicities, and all sources use UTF-8 and Java 17. The default program demonstrates both styles of object explicitly and gives 2 for the nested-bag count.
