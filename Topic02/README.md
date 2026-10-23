# Linear data structures

Each example is self-contained and includes a Java 17 program; open the folder
of the demo you want to study. Demo07 also includes optional C++, C# and Python
versions.

Start with Demo01, which implements a mutable `Collection` by extending
`AbstractCollection`, using singly linked nodes and a forward iterator. Continue
with Demo02 for the full `ListIterator` contract and for reuse through
`AbstractSequentialList`. Demo03 adds backward links, so that moving the cursor
and editing the list take constant time.

- [01 — A collection backed by singly linked
  nodes](Demo01SinglyLinkedList/README.md).
- [02 — A singly linked list with ListIterator](Demo02LinkedList/README.md).
- [03 — A doubly linked list with
  ListIterator](Demo03DoublyLinkedList/README.md).
- [FIFO and LIFO through AbstractQueue](Demo04QueuePolicies/README.md).
- [Priority queue backed by a sorted list](Demo05SortedPriorityQueue/README.md).
- [An iterator with one-element lookahead](Demo06PeekingIterator/README.md).
- [A stack in Java, C++, C# and Python](Demo07StacksAcrossLanguages/README.md).

You can read Demo07 straight after Topic 1 to compare interfaces, generic
programming and LIFO operations across languages. Its Java version is part of
the topic launcher; run the C++, C# and Python scripts separately, as its README
explains.

Run `bash run.sh list`, `bash run.sh all` or `bash run.sh test`, or choose one
of the listed demos.

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd list
.\run.cmd all
.\run.cmd test
```

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01SinglyLinkedList`.
The launcher sets its own working directory and copes with paths containing
spaces and with any bundled JAR libraries. It runs on Windows PowerShell 5.1,
which comes with Windows, and `run.ps1` also works with PowerShell 7. The VS Code
**Run** and **Debug** buttons are still available when the example folder is open.
