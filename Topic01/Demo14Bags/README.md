# 14 Bags: immutable and mutable objects

## Problem statement

Implement three kinds of bag (multiset): `ImmutableBag`, `MutableBag` and `SortedMutableBag`. A bag allows repeated elements. Two bags are equal when they have the same elements with the same multiplicities, whatever the iteration order.

Use this example **at the end of Topic 1**, after explaining generic interfaces, inheritance, `Collection`, `AbstractCollection`, `Iterable`, `Iterator` and `Comparator`. The code that reads the text file is only there to support the example; you do not need it to understand the bag implementations. This is an additional closing exercise, not one of the numbered examples on the slides.

1. Construct an immutable bag and produce new bags with `withAdded` and `withoutOne`. Show that the original stays unchanged, just as with the distinction between `String` and `StringBuffer`.
2. Construct a mutable bag, and then add elements to that same object and remove elements from it.
3. Iterate over a bag and remove elements correctly. Test inherited operations such as `removeAll`, `retainAll` and `removeIf`.
4. Keep the iteration order sorted, using either the natural order or a comparator passed in by the client. If a comparison fails during `add`, the bag must be left unchanged.
5. Compare bags of different implementations and count the occurrences of nested bags. Bags with the same contents in a different order must still be equal.
6. Read `text.txt`, store its words in a sorted bag, and count `the` and `for`.

## Contracts and design

- `AbstractBag` extends `AbstractCollection` and provides the shared `occurrences`, `equals` and `hashCode`. Equality is restricted to this bag hierarchy so that it cannot become asymmetric when lists or sets are involved. The simple counting implementation of equality takes quadratic time, but it needs no map.
- `ImmutableBag` is `final`, copies the storage it receives, never exposes a mutable backing array and inherits the optional mutators from `AbstractCollection`. A call that would change the bag throws `UnsupportedOperationException`, while a call that requires no change may complete normally. `withoutOne` removes a single occurrence, and it may return the same instance if the element does not occur at all.
- Immutability is **structural**: the element objects are not deep-copied, so a mutable element can still change. Use immutable elements when stable values and hash codes matter. Do not modify a bag that is used as a hash key, nor any of its elements that affect its equality. Bags that contain themselves, directly or through a cycle, fall outside the equality contract of this exercise.
- `MutableBag` uses a resizable array. Removing through the iterator adjusts the cursor and clears the slot that is no longer used. Each iterator has its own position. During a traversal, change the bag only through the iterator in use. There is no modification counter, so changes made by other means are not detected.
- `SortedMutableBag` extends `MutableBag`. It computes the insertion position before changing anything, and it also checks that the first element can be compared. Without a comparator, the elements must be mutually comparable. A comparator must obey the usual ordering contract and must not modify the bag. Do not change the fields that determine an element's order while it is stored in the bag. Bag equality is defined by `equals`, not by comparator equivalence.
- All three reject `null` elements. A bulk operation that modifies a mutable bag may be left half done if a later element fails; the guarantee about exceptions given above applies to a single `add`.

## Guided walkthrough

The update methods of the immutable bag follow three explicit steps: they create a private mutable copy, apply the change to that copy, and build a new immutable snapshot from it. This reuses the operations of the mutable bag and avoids a special constructor flag to say who owns the array. For the sake of clarity it allocates an intermediate copy instead of keeping allocations to a minimum. The original bag is never modified.

1. Start in `Main` with the immutable integer bag. Compare `original` with the result of `withAdded` before you think about what is inherited from the collection classes.
2. Follow `add` and `remove` on the mutable copy. The object stays the same while its contents change.
3. Read `AbstractBag.occurrences`, and then `equals` and `hashCode`. Work through the two nested bags, which have the same multiplicities in a different order.
4. Read `MutableBag.BagIterator.remove`. Once the entries have been shifted left, the next unread entry sits at the index that was removed. That is why the cursor has to move back and the last slot has to be cleared.
5. Read `SortedMutableBag.add`. All comparisons finish before the backing storage is modified, and only then is room made at the insertion position.
6. Finish with the text client, whose collection operations apply the same contracts to strings.

## What to observe and try

Remove consecutive equal elements through an iterator, and then test `clear`, `removeAll` and `retainAll`. Repeat with a reverse-order comparator. Pass a comparator that throws an exception, and check that the failed insertion leaves the size and the contents unchanged.

Create an immutable bag containing a `StringBuilder`, then change the builder. Explain why the structure of the bag is still fixed even though the visible value of the element changes. Contrast this with modifying the array that was originally passed to the constructor, which must not affect the bag.

For equality, try bags of the same size with different numbers of repetitions. Tell the multiset contract apart from positional list equality and from comparator equivalence. The tests check these distinctions on purpose, rather than just the output of the normal case.

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
Original after both immutable operations: [2, -3, 2, 18]; occurrences(2) = 2

Mutable copy before operations: [2, -3, 2, 18]
add(7): true; contents = [2, -3, 2, 18, 7]
remove(2): true; contents = [-3, 2, 18, 7]
Mutable after add and remove: [-3, 2, 18, 7]

Candidate bags: [[100, 2], [2, -3, 2, 18], [18, 2, 2, -3], [-1, 0, 10], [18, 2, -3]]
Equal bags (same multiplicities): 2
Sorted: [-3, 2, 2, 18]
Same bag despite order: true
Sorting changes iteration order, but preserves multiplicities.

Read words from: text.txt
Normalisation: lowercase Unicode words; punctuation separates words.
First 12 words in sorted iteration: a a a a a a a a a abilities accompanied acquiring
Words: 322
Occurrences of the: 15
Occurrences of for: 1
```

## Changes from the old PA-1 material

`BagImmu`, `BagMut` and `BagSorted` are now `ImmutableBag`, `MutableBag` and `SortedMutableBag`. The bugs in the iterator cursor and the retained references have been fixed, a failed sorted insertion no longer modifies the bag, equality and hashing take multiplicities into account, and all sources use UTF-8 and Java 17. The default program shows both kinds of object explicitly and gives 2 as the count of nested bags.

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

