# 08 Factorials

Compute a sequence with an iterator, reusing the result of the previous step.

**Topic 1 placement:** after [07 Iterators](../Demo07Iterators/README.md) and before [09 Costs](../Demo09Costs/README.md). This is an additional example for the sections “Traversal without knowing the representation” and “Time and space costs”.

## Problem statement

Generate the first `n` factorials: `0!, 1!, ..., (n - 1)!`. First compute each factorial separately; then use `FactorialIterator` to obtain the same sequence one element at a time.

The iterator needs only three fields: the next factorial, the number of values already returned and the requested number of values. It computes values as requested instead of storing the sequence.

The main program uses a `List<Long>` backed by a `LinkedList` to collect the results. Its `add` operation appends each value. Understanding the list implementation is not required for this example.

## Follow the iterator state

Before each successful call to `next()`, `currentFactorial` contains `index!`.

1. Check that another value exists.
2. Save the factorial to return.
3. Increase `index` and multiply by that new index to prepare the next factorial.
4. Return the saved value.

| `index` before the call | Value returned | Factorial prepared for the next call |
| --- | --- | --- |
| 0 | 1 = 0! | 1 = 1! |
| 1 | 1 = 1! | 2 = 2! |
| 2 | 2 = 2! | 6 = 3! |
| 3 | 6 = 3! | 24 = 4! |

`hasNext()` only checks `index < limit`: repeated calls do not advance the iterator. A limit of zero produces no values. A negative limit throws `IllegalArgumentException`; `next()` throws `NoSuchElementException` when exhausted.

## Compare the costs

Computing each factorial from scratch repeats the earlier multiplications. Across the first `n` factorials, that is **O(n²)** work.

The iterator performs one multiplication and a fixed amount of bookkeeping per returned value: **O(1)** per `next()` and **O(n)** to generate all `n` values. Appending to the linked list is O(1), so collecting the sequence also takes O(n).

The iterator itself uses **O(1)** additional space. The list that stores the `n` results uses **O(n)** space.

Use up to 21 terms (`0!` through `20!`) for exact results with `long`. This example retains the simple iterator; larger factorials exceed the range of `long`.

## Open and run

Open this `Demo08Factorials` folder in VS Code with JDK 17 or newer and the Extension Pack for Java. Run [src/app/Main.java](src/app/Main.java).

macOS/Linux:

```sh
bash run.sh
bash run.sh test
```

Windows PowerShell or Command Prompt:

```powershell
.\run.cmd
.\run.cmd test
```

No external libraries are required. The project includes its own launchers and editor settings.

## Files

- [FactorialIterator.java](src/ds/FactorialIterator.java): the iterator.
- [Main.java](src/app/Main.java): both ways of generating the sequence.
- [ExampleChecks.java](tests/tests/ExampleChecks.java): sequence, exhaustion, independent positions and boundary checks.

## Expected output

```text
Recomputing factorials: [1, 1, 2, 6, 24, 120]
Using the iterator: [1, 1, 2, 6, 24, 120]
More values: false
```

## Try it

Trace the two initial values of 1: why are both correct? Try `n = 0`, `n = 1` and `n = 6`. Explain why the iterator saves `result` before updating its fields, and why creating a second iterator starts a fresh traversal.
