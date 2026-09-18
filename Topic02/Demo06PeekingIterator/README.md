# An iterator with one-element lookahead

## Problem statement and prerequisites

Wrap an iterable so peek observes the next value without consuming it from the
wrapper. Prerequisites: Iterator, generics and composition. Null values are valid;
a null iterable is not. Construction prefetches one element from the source.

## Guided walkthrough

1. Inspect available and next: presence is independent of the element value.
2. Repeated peek calls return the same element without advancing the wrapper.
3. next obtains the buffered element and advances the underlying iterator.
4. At exhaustion, both peek and next throw NoSuchElementException.
5. remove is unsupported: prefetching complicates delegation to the underlying iterator.

## Costs and contracts

The wrapper keeps O(1) extra state. Each next performs one source advancement;
its cost therefore depends on the source iterator. Do not modify the source
while traversing. The old use of null as an end marker is removed. This version
can support later exercises that merge ordered sequences.

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
Peek: A; next: A
Peek: null; next: null
Peek: B; next: B
```
