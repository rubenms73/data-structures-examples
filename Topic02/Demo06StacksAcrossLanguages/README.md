# Demo 06 — A stack in Java, C++ and C#

## Problem statement

Implement the stack abstract data type in three languages. Push the integers
1, 2 and 3, inspect the top without removing it, and pop all elements. Separate
the interface from its implementation and explain why clients observe LIFO
(last-in, first-out) behaviour regardless of the internal storage.

This is a corrected adaptation of the historical Java `ArrayStack` project
and the C++/C# examples in `old2/PAs/CA2`. All sources needed for each version
are contained in this folder. Start with Java; the other languages are optional
comparative material, not prerequisites for subsequent Java examples.

## Prerequisites

Java interfaces, generics, arrays, exceptions and inner iterators (Topic 1).
For C++, templates, references, virtual functions and automatic object lifetime.
For C#, generic interfaces, arrays and `IEnumerable<T>`; `yield return` is
explained below. The C++ version reuses `std::list`: it does not implement nodes.

## Operations and contracts

| Operation | Meaning |
| --- | --- |
| `push` / `Push` | Insert one element at the top. Duplicates are allowed. |
| `pop` / `Pop` | Remove and return the top element. Reject an empty stack. |
| `peek` / `Peek` | Inspect the top without changing the number of elements. Reject an empty stack. |
| `size` / `Size` | Return the number of stored elements, not the capacity. |
| `isEmpty` / `IsEmpty` | Return true exactly when the size is zero. |

| Property | Java | C++ | C# |
| --- | --- | --- | --- |
| Interface | `Stack<E>` | `Stack<T>` (abstract class template) | `IStack<T>` |
| Implementation | `ArrayStack<E>` | `ListStack<T>` | `ArrayStack<T>` |
| Storage | Fixed array | `std::list<T>` | Growing array |
| Empty `pop` / `peek` | `NoSuchElementException` | `std::underflow_error` | `InvalidOperationException` |
| Capacity | Default 10; explicit nonnegative capacity | No explicit capacity parameter | Default 10; explicit nonnegative initial capacity |
| Full storage | `IllegalStateException`; stack unchanged | Allocate another list node | Grow the array, including from zero |
| Traversal | Private inner `Iterator<E>` | Not provided in this basic interface | `yield return` implements top-to-bottom enumeration |

Negative capacities are rejected by a simple conditional. A zero-capacity Java
stack stays empty and rejects pushes; a zero-capacity C# stack grows to one on
its first push. Java and C# permit null elements: null must not mean “empty” or
“end of traversal”. C++ stores values of `T`; the example uses integers and
strings, not owning pointers.

## Java walkthrough

`data[0..size)` contains the active elements. The top is at `size - 1` and all
unused cells hold null. `push` checks capacity before changing anything. `pop`
checks emptiness, decreases size, saves the element and clears the unused cell
so the array does not retain a reference to the removed object. `peek` reads
without decreasing size.

Java cannot directly create `new E[capacity]`. The constructor creates an
`Object[]` and uses one localized, suppressed cast to store it as `E[]`. The
array stays private. The fixed capacity is deliberate: it illustrates a bounded
stack and preserves the historical example's policy.

The inner `StackIterator` starts at `size - 1` and walks backwards. Exhaustion
throws `NoSuchElementException`; `remove` uses the interface's default
`UnsupportedOperationException`. Each iterator has its own position.

## C++ walkthrough

`Stack<T>` declares pure virtual operations; its virtual destructor permits
safe destruction through a base pointer. `ListStack<T>` puts the top at the
front of its `std::list<T>` and checks emptiness before calling `front`.
`isEmpty` now tests for zero elements, fixing the historical inverted condition.

`push(const T&)` avoids an extra parameter copy, but storing the value in a node
still copies it. `peek() const` returns a `const T&`, avoiding a copy and preventing
modification through that reference. The reference must not be used after its
element is popped or its stack is destroyed. `pop` copies the value before
removing the node; element types in this example must be copyable. This is not
a fully general move-only container or a study of strong exception guarantees
for element types with throwing copy/move constructors.

The main program creates a local object and uses a base reference to demonstrate
virtual dispatch. No explicit `new`/`delete` is needed. The list manages its nodes.
A C++ stack copy has independent nodes and copies its elements according to `T`'s
copy semantics; pointer elements would still refer to the same pointed-to objects.

## C# walkthrough

C# permits `new T[capacity]` directly. When full, the array grows to twice its
capacity, or to one if it was empty. The new array is allocated and populated
before replacing the old one. An explicit check prevents overflow of the
capacity multiplication; real allocation limits may be reached earlier.
`Pop` clears the vacated cell using `default(T)`: null for reference types, zero
for integers. `Peek` completes the common stack interface.

`yield return` suspends enumeration after each value and lets the compiler
maintain the index between calls. The nongeneric `IEnumerable` entry point
reuses the generic enumerator rather than duplicating its loop. Enumeration is
lazy: its body starts when enumeration advances, not when `GetEnumerator` is
called. C# naming follows its usual PascalCase method convention.

## Mutation, references and limits

Do not structurally modify a Java or C# stack while iterating: these teaching
implementations do not detect concurrent modification and do not take snapshots.
Traversal does not remove elements. None of the implementations is thread-safe.

Assigning a Java or C# stack variable shares the same stack object; no copy
constructor is supplied. Reference-type elements are shared too: pushing an
object does not clone it. This differs from a C++ value copy of `ListStack<T>`.

## Complexity

| Operation | Java fixed array | C++ list | C# growing array |
| --- | --- | --- | --- |
| Push | O(1), or reject when full | O(1) list work plus allocation and element copy | O(1) amortized; O(n) when growing |
| Pop / peek / size / isEmpty | O(1) | O(1) container work plus element copy for pop | O(1) |
| Full traversal | O(n) | Not exposed | O(n) |
| Storage | O(capacity) | O(n) nodes | O(capacity), retained after pops |

Costs assume constant-time element operations. Java and C# copy references for
reference-type elements; C++ copies `T` values. C# resizing temporarily retains
both arrays. No implementation shrinks storage on removal.

## Run and test

Java 17 (the topic launcher runs this version):

```sh
bash run.sh
bash run.sh test
```

Open this demo folder in VS Code to run `src/app/Main.java` using the supplied
Java launch configuration.

C++17 with GCC (or set `CXX=clang++`):

```sh
bash cpp/run.sh
bash cpp/run.sh test
```

C# with the .NET 8 SDK, from the `csharp` directory:

```sh
dotnet run --project StackExample.csproj
dotnet run --project StackExample.csproj -- test
```

Alternatively, `bash csharp/run.sh [run|test]` uses .NET if present, or the Mono
`mcs` compiler and `mono` runtime. The sources use syntax supported by both.
The scripts treat compiler warnings as errors.

All three demonstrations print:

```text
Size: 3
Top: 3
Pop: 3
Pop: 2
Pop: 1
Size: 0
```

The Java tests cover empty/full stacks, negative and zero capacity, null values,
iterator exhaustion and unsupported removal, and repeated fill/drain cycles.
C++ tests cover empty operations, LIFO order, reuse, const access, strings and
independence of container copies. C# tests cover negative/zero initial capacity,
multiple resizes, both enumeration interfaces, null values and reuse after draining.

## Validation of this revision

Compiled and executed with Java 17, GCC in C++17 mode and Mono C# 6.8,
with warnings treated as errors. All three programs produced the output above
and passed their behaviour checks. The .NET project is supplied for SDK users;
this revision was tested with Mono, not the .NET 8 runtime.

## Experiments

1. Replace the third pushed value with a duplicate. Does LIFO depend on uniqueness?
2. Try a fourth push with an initial capacity of three in Java and C#. Explain
   why their capacity policies give different results while both remain stacks.
3. Traverse the Java/C# stack twice and check that its size is unchanged.
4. Compare Java's explicit iterator position with C#'s suspended `for` loop.
5. Copy a C++ stack and pop from the copy. Compare this with assigning a second
   variable to the same Java/C# stack object.
