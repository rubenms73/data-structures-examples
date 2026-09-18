# Hash set with separate chaining

## Problem statement and prerequisites

Implement an equality-based set with linked-list buckets. Prerequisites:
Set, hashCode/equals, iterators and rehashing. Elements need not be Comparable.
Capacity must be positive (unlike a zero-capacity vector). Nulls are rejected.
The load threshold must be positive and finite, including rejection of NaN.

## Guided walkthrough

1. Reduce the hash modulo capacity, then correct a negative remainder.
2. contains checks equality within a bucket. Equal objects must have equal hashes.
3. Before a new insertion, grow once if the prospective load exceeds the threshold.
4. Recompute each entry's index using the new capacity; never copy old bucket indices.
5. The iterator scans buckets. Keep lastUsed separate because hasNext may move on.
6. Iterator removal must also decrement the outer set's size.

## Costs and contracts

Expected lookup/update cost is O(1) at suitable load and hash distribution;
worst-case cost is O(n). Rehashing costs O(n + new capacity); iteration costs
O(n + capacity). One insertion triggers at most one doubling-plus-one, so very
small thresholds may be exceeded temporarily. Structural changes during iteration
are unsupported except through that iterator. Hash/equality must remain stable.
The old negative-index bug, Comparable-only buckets and stale removal count
are corrected. Inherited clear works through the corrected iterator.

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
Contains minimum integer: true
Size after iterator removal: 0
```
