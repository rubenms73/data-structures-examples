# Linear data structures

Each example is self-contained and includes a Java 17 program. Open an individual
demo folder. Demo07 also includes optional C++, C# and Python versions.

Start with Demo01 to study a singly linked list and its forward iterator. Continue
with Demo02 for the full ListIterator contract and AbstractSequentialList reuse.
Demo03 adds backward links so cursor movement and edits take constant time.

- [01 — Singly linked list and forward iterator](Demo01SinglyLinkedList/README.md).
- [02 — A singly linked list with ListIterator](Demo02LinkedList/README.md).
- [03 — A doubly linked list with ListIterator](Demo03DoublyLinkedList/README.md).
- [FIFO and LIFO through AbstractQueue](Demo04QueuePolicies/README.md).
- [Priority queue backed by a sorted list](Demo05SortedPriorityQueue/README.md).
- [An iterator with one-element lookahead](Demo07PeekingIterator/README.md).
- [A stack in Java, C++, C# and Python](Demo07StacksAcrossLanguages/README.md).

Demo07 can be read after Topic 1 to compare interfaces, generic programming and
LIFO operations across languages. Its Java version participates in the topic
launcher; run the C++, C# and Python scripts separately as described in its README.

Run `bash run.sh list`, `bash run.sh all`, `bash run.sh test`, or select a listed demo.
