# An iterator with one-element lookahead

## Problem statement and prerequisites

Wrap an iterable so that `peek` can look at the next value without the wrapper
consuming it. You should already know `Iterator`, generics and composition.
`null` values are valid, but a `null` iterable is not. The constructor
prefetches one element from the source.

## Guided walkthrough

1. Look at `available` and `next`. Whether an element is present does not depend
   on its value.
2. Repeated calls to `peek` return the same element without advancing the
   wrapper.
3. `next` takes the buffered element and advances the underlying iterator.
4. Once the iterator is exhausted, both `peek` and `next` throw
   `NoSuchElementException`.
5. `remove` is not supported, because prefetching makes it awkward to delegate
   to the underlying iterator.

## Costs and contracts

The wrapper keeps O(1) extra state. Each call to `next` advances the source
once, so its cost depends on the source iterator. Do not modify the source
during a traversal. The old practice of using `null` as an end marker has been
dropped. This version can be used in later exercises that merge ordered
sequences.

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

Trace `Main` by hand before you run it. Try the empty case, the boundary cases
and the rejected operations described above, and check that an operation that is
rejected leaves the structure unchanged. Compare the representation with the
public contract it implements.

## Expected output

```text
Source: [A, null, B]
Repeated peek() keeps the same cached element:
hasNext(): true
  First peek(): A
  Second peek(): A
  next(), consuming that element: A
hasNext(): true
  First peek(): null
  Second peek(): null
  next(), consuming that element: null
hasNext(): true
  First peek(): B
  Second peek(): B
  next(), consuming that element: B
After traversal, hasNext(): false
Exhausted peek(): NoSuchElementException
Exhausted next(): NoSuchElementException
Source after traversal: [A, null, B]
```

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
