# Demo 06 — A stack in Java, C++, C# and Python

## Problem statement

Implement the same stack abstract data type in four languages. Push 1, 2 and 3,
inspect the top, traverse from top to bottom without removing elements, and
finally pop all elements. Separate the interface from its implementation.

This corrects and extends the historical Java `ArrayStack` and C++/C# `CA2`
material. The Python version adds a comparison of static and dynamic typing.
Java is the course version. The other languages are optional comparative
material and are self-contained in their respective subdirectories.

## Prerequisites

Topic 1 interfaces, generics, references, arrays, exceptions and inner iterators;
Topic 2's stack contract. Read “Stacks: last in, first out” and “Choosing the top
of a stack” in the presentation before running this example.

The optional C++ version uses templates, references, pure virtual functions,
a nested class and automatic resource management. The C# and Python versions
introduce generator-based iteration. Their details are explained below.

## Common behaviour

All four versions retain duplicates, report the number of elements rather than
capacity, reject empty pop/peek operations, and traverse in LIFO order without
changing membership. Each traversal has its own state. Insertion can grow storage.

| Operation | Java / C++ | C# | Python |
| --- | --- | --- | --- |
| Insert at top | `push(item)` | `Push(item)` | `push(item)` |
| Remove top | `pop()` | `Pop()` | `pop()` |
| Observe top | `peek()` | `Peek()` | `peek()` |
| Number of elements | `size()` | `Size()` | `size()` |
| Test emptiness | `isEmpty()` | `IsEmpty()` | `is_empty()` |

| Property | Java | C++ | C# | Python |
| --- | --- | --- | --- | --- |
| Interface | `Stack<E>` | `Stack<T>`, abstract class template | `IStack<T>` | `Stack[T]`, generic abstract base class |
| Implementation | `ArrayStack<E>` | `ListStack<T>` | `ArrayStack<T>` | `ArrayStack[T]` |
| Storage | Growing array | `std::list<T>` | Growing array | Built-in list, used as a growing array |
| Empty pop / peek | `NoSuchElementException` | `std::underflow_error` | `InvalidOperationException` | `IndexError` |
| Capacity parameter | Default 10; nonnegative initial capacity | Managed by list | Default 10; nonnegative initial capacity | Managed by list |
| Growth | Double the array, or grow zero to one | Allocate another node | Double the array, or grow zero to one | List manages its capacity |
| Traversal implementation | Private inner iterator | Private nested iterator; also standard range iteration | `yield return` | `yield` |
| Type checking | Static, with erased generic parameters | Static, with template instantiation | Static, with runtime generic type information | Dynamic; annotations support separate static analysis |

Capacity policy is now aligned: Java's historical fixed-capacity implementation
has been replaced by a growing array, matching C#. Negative initial capacities
are rejected with a simple conditional. Zero is valid and grows to one on the
first insertion. C++ and Python do not expose a capacity argument in this example.
Allocations can still fail when resources are exhausted.

## Java: explicit iterator state and array growth

`data[0..size)` holds the active elements and `size - 1` is the top. `push` grows
the array when full, then writes the new reference and increments size. The
capacity multiplication is checked for integer overflow. `Arrays.copyOf` copies
the existing references into a larger array before the field is replaced.

`pop` checks emptiness, decreases size, saves the reference and clears the unused
cell. This prevents the array from keeping removed objects alive. `peek` reads
the same top cell without changing size. Null elements are permitted, so null
cannot be used as the empty-stack signal.

The constructor allocates `Object[]` and uses one localized, suppressed cast to
store it as `E[]`. Java cannot directly allocate `new E[capacity]`. The array
stays private, and element access needs no further cast.

`StackIterator` keeps a position starting at `size - 1`. Each `next()` returns
one element and decreases that position. Exhaustion throws
`NoSuchElementException`. The default iterator `remove` is unsupported.

## C++: nested iterator and value semantics

`Stack<T>` declares pure virtual operations and a virtual destructor.
`ListStack<T>` uses the front of `std::list<T>` as the top. The client creates
an automatic local object and refers to it through `Stack<int>&` to demonstrate
virtual dispatch. The list manages its nodes without explicit new/delete.

`push(const T&)` avoids a parameter copy but storing the element still copies
it into a node. `peek() const` returns `const T&`, avoiding a copy and preventing
assignment through that reference. `pop` copies the value before destroying its
node. This teaching version requires copyable element types; it does not cover
move-only values or strong exception guarantees for throwing element copies.

The new `Iterator<T>` course interface declares `hasNext` and `next`, mirroring
the Java protocol. `Stack<T>::iterator()` returns a
`std::unique_ptr<Iterator<T>>`. This owning pointer destroys the traversal object
automatically and allows clients to traverse through the abstract stack type.
It owns the iterator, not the stack or its elements.

The private nested `StackIterator` holds two `std::list<T>::const_iterator`
positions: the next element and the end. `hasNext` compares the positions.
`next` checks exhaustion, dereferences the position, advances it and returns a
constant reference. Exhaustion throws `std::out_of_range`. The `typename`
keyword identifies a nested type whose meaning depends on template parameter T.

This course iterator is not an STL iterator. For idiomatic C++ traversal, the
concrete `ListStack<T>` also exposes const `begin()` and `end()`:

```cpp
for (const auto& value : storage)
{
    std::cout << value << '\n';
}
```

Both forms traverse top to bottom. The simple mutation rule is the same as in
the other versions: do not change the stack during traversal. The C++ stack
must also outlive its iterators and any references returned by peek/next.

Copying a C++ stack copies its list nodes and values according to T's copy
semantics. Its container storage is independent. If T is a pointer, copying it
does not copy the pointed-to object.

## C#: iterator state generated by the compiler

C# permits `new T[capacity]` directly. Growth doubles capacity (or changes zero
to one), checks for integer overflow, allocates the replacement and copies active
elements. Pop clears the unused cell with `default(T)`, which is null for
reference types and zero for integers.

The generic `GetEnumerator()` loops backwards and uses `yield return`. The
compiler creates an enumerator that remembers the loop position between calls.
The nongeneric `IEnumerable` implementation delegates to this method.

The public iterator protocol uses `MoveNext()` to advance and `Current` to read.
`MoveNext` returns false at exhaustion. This differs from Java's `hasNext/next`
protocol, even though `foreach` produces the same traversal. The generator body
starts on the first advancement, not when `GetEnumerator` is called.

## Python: the same contract with dynamic typing

`Stack[T]` combines `ABC` and `Generic[T]`. `@abstractmethod` requires a concrete
subclass to supply the operations. `TypeVar("T")` relates the element types
accepted by push and returned by pop, peek and iteration. `ArrayStack[T]`
implements that contract using a private-by-convention list field `_data`.

Python is strongly typed and dynamically typed. Values have types at runtime;
annotations do not change the runtime dispatch model or enforce the generic
argument. A static analyzer can reject this client code before execution:

```python
stack: Stack[int] = ArrayStack[int]()
stack.push("hello")  # Deliberate static type error.
```

Without a checker, the example implementation would accept the string. Python
checks the operations actually performed at runtime, not this annotation. In
contrast, `1 + "hello"` raises `TypeError`: dynamic typing does not imply that
arbitrary implicit conversions are allowed.

`list[T]` already provides a growing array, so the implementation delegates
allocation to it. `append` inserts at the end, `pop` removes that end, and index
`-1` observes it. A simple conditional rejects empty access. This keeps attention
on the ADT rather than reproducing Python's internal memory management.

`__iter__` creates a generator. `yield` returns one value and suspends the
backward loop, much like C#'s `yield return`. `iter(stack)` obtains an iterator,
`next(iterator)` advances it, and exhaustion raises `StopIteration`.
Python's `for` loop handles that signal automatically. Each generator has an
independent index. None is a valid value when the chosen annotation permits it,
for example `ArrayStack[int | None]`; it does not mark exhaustion.

## Mutation and copying

None of these stacks is thread-safe. Do not modify a stack during a traversal.
No iterator removes elements, takes a snapshot or checks for concurrent changes.

Assigning a Java, C# or Python stack variable shares the same stack object.
Their reference-type elements are shared too. In particular, copying references
into a larger array does not clone the elements. Compare this with C++'s value
copy of `ListStack<T>`. The interfaces deliberately do not attempt to hide these
language-level differences.

## Complexity

For n stored elements, peek, size and emptiness checks take O(1). Full traversal
takes O(n), with O(1) iterator state in each version. Linked-stack push/pop take
O(1) container work, plus allocation and element-copy costs. Growing-array push
is amortised O(1), with O(n) work when storage grows. Java/C# pop is O(1).
Python's built-in list manages resizing, so end insertion/removal are amortised
O(1) under its usual dynamic-array implementation.

Java/C# retain allocated capacity after pops; the C++ list releases removed
nodes; Python controls its own spare-capacity policy. The complexity comparison
assumes constant-time element operations. Copying an arbitrary C++ T value can
cost more than copying an object reference.

## Run and test

Java 17, from this demo directory:

```sh
bash run.sh
bash run.sh test
```

C++17 with GCC (or `CXX=clang++`):

```sh
bash cpp/run.sh
bash cpp/run.sh test
```

C# with .NET 8, from `csharp`:

```sh
dotnet run --project StackExample.csproj
dotnet run --project StackExample.csproj -- test
```

`bash csharp/run.sh [run|test]` uses .NET if available, otherwise Mono's `mcs`
and `mono`. Java, C++ and C# compile with warnings treated as errors.

Python 3.10 or newer:

```sh
bash python/run.sh
bash python/run.sh test
```

From `python`, the portable equivalents are `python main.py` and
`python -m unittest -v test_stack`. There are no third-party runtime dependencies.
An optional static check, with mypy installed, is:

```sh
python -m mypy --strict python/stack.py python/main.py python/test_stack.py
```

The Topic 2 launcher and repository Java checker run the Java version. The
other language runners are separate.

## Expected output in every language

```text
Size: 3
Top: 3
Traversal: 3 2 1
Pop: 3
Pop: 2
Pop: 1
Size: 0
```

Traversal leaves all three elements in place; the following pops prove that it
does not consume the stack. Tests also cover empty access, duplicates, multiple
insertions/removals, reuse, independent traversals and exhaustion. Java and C#
add explicit zero/negative capacity cases. C++ checks both iterator protocols
and independent container copies. Python checks None, zero and shared objects.

## Validation of this revision

Compiled and executed with Java 17, GCC in C++17 mode and Mono C# 6.8. Python
behaviour tests run with the installed Python interpreter, and all three Python
source files pass `mypy --strict`. All four main programs
produce the output above. The .NET project is supplied for SDK users; C# runtime
validation uses Mono rather than .NET 8.

## Experiments

1. Push a duplicate and verify that both occurrences appear in iteration and pops.
2. Create two iterators and advance only one. Observe their independent positions.
3. Compare Java's explicit index with the C#/Python suspended loops and the C++
   nested iterator's stored list positions.
4. Copy a C++ stack, then pop the copy. Compare with assigning a second variable
   to the same Java, C# or Python object.
5. Ask a Python static analyzer to check a deliberately incorrect push, then
   distinguish that diagnostic from what the unannotated runtime permits.
