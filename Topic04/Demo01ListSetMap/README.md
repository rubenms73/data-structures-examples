# Sets and maps backed by lists

## Problem statement and prerequisites

Implement a set through AbstractSet and a map through AbstractMap. Prerequisites:
List, Set, Map.Entry, iterators, equals/hashCode and optional operations.
Null elements, keys and values are allowed. Keys must have stable equality.

## Guided walkthrough

1. ListSet adds uniqueness by checking membership before insertion.
2. ListMap searches entries and replaces the value of an equal key.
3. SimpleEntry supplies the standard Map.Entry equals/hashCode and setValue contract.
4. entrySet is a live inner view, not a copy. Its iterator removes from the map.
5. AbstractMap derives get, containsKey, remove, keySet and values from entrySet.
6. EntrySet does not support add; new associations go through put.

## Costs and contracts

Membership, get and put cost O(n); complete traversal is O(n). This is useful
for understanding contracts, not a fast general-purpose map. AbstractSet and
AbstractMap provide compatible equality/hashCode. Mutable entry values are
supported; changing key equality while stored is not. Views are backed by the map.

## Open and run

Open this individual folder in VS Code with JDK 17 and Extension Pack for Java.
Run `src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

The project is self-contained. `src/ds` contains the implementation, `src/app`
the demonstration and `tests/tests` the automated checks. No external libraries
are required. Code and comments use English and Allman style.

## What to observe and try

Trace Main by hand before running it. Exercise the empty case, boundaries and
rejected operations described above. Verify that a rejected single operation
leaves the structure unchanged. Compare the representation with its public contract.

## Expected output

```text
Set: [A, B]
Previous: 1
Map after view removal: {A=2}
```
