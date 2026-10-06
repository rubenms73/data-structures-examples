# Topic 1 — Expected output

Output from the unmodified examples. The demonstrations use fixed inputs.

## Demo01Rational

```text
Same Rational interface, two representations:
RationalImp1 (two fields): 3/4 = 0.75
RationalImp2 (array): 3/4 = 0.75

Reference identity versus numeric value:
r and a refer to the same object: true
a and b refer to the same object: false
a and b return the same value: true

Constructing 3/0 violates the denominator invariant:
Rejected: Zero denominator
```

## Demo02IntArrays

```text
Both arrays start with capacity 3 and size 0.
Fill the fixed array:
  add(4): [4]; size = 1
  add(8): [4, 8]; size = 2
  add(12): [4, 8, 12]; size = 3
Fill the dynamic array:
  add(4): [4]; size = 1
  add(8): [4, 8]; size = 2
  add(12): [4, 8, 12]; size = 3
Fixed: [4, 8, 12]; size = 3; sum = 24
Dynamic: [4, 8, 12]; size = 3; sum = 24
After set(1, 10): [4, 10, 12]
size = 3, contains(10) = true
Fixed add(16): IllegalStateException
Fixed still contains: [4, 10, 12]
Dynamic after add(16): [4, 8, 12, 16]; size = 4; sum = 40
```

## Demo03GenericArrays

```text
MyArray<String>: Ana, Ruben; size = 2
MyArray<Integer>: 8, 10; size = 2
The same generic first() method preserves each element type:
First name: Ana
First score: 8
Object[] contents: [Ana, 8]
Attempt to cast the Integer at index 1 to String:
Object[] accepts mixed values; the wrong cast fails at run time.
```

## Demo04StringSorting

```text
pear.compareTo(banana): 14
A positive comparison places pear after banana alphabetically.
Original: [pear, banana, fig]
Natural order: [banana, fig, pear]
LengthComparator.compare(pear, banana): -1
A negative comparison places the shorter word first.
Length order: [fig, pear, banana]
```

## Demo05Movies

```text

Original arrival order (4 movies):
  The Last Train (2022, 7.4)
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)

Natural order: year, then title, then rating (4 movies):
  Blue Planet (2018, 8.6)
  Winter Lights (2018, 7.9)
  A Quiet Harbour (2020, 8.6)
  The Last Train (2022, 7.4)

Rating: highest first (4 movies):
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)
  The Last Train (2022, 7.4)

Title order (4 movies):
  A Quiet Harbour (2020, 8.6)
  Blue Planet (2018, 8.6)
  The Last Train (2022, 7.4)
  Winter Lights (2018, 7.9)
```

## Demo06Maximum

```text
Input: [pear, banana, fig]
The comparator determines what maximum means:
Alphabetical maximum: pear
Longest word: banana
Original array: [pear, banana, fig]
Request maximum of an empty array:
Rejected: Empty array
```

## Demo07Iterators

```text
Source vector: [Ana, Ruben]; size = 2
Two iterators over the same source keep independent positions:
hasNext(): true
hasNext() again: true
first.next(): Ana
first.next(): Ruben
second.next(): Ana
first.hasNext(): false
Next after the end: NoSuchElementException
A fresh enhanced for loop: Ana Ruben
Copy of scores: [8, 10]
Integer vector sum: 18
After copy.set(0, 99): copy[0] = 99; source[0] = 8
```

## Demo08Costs

```text
Sum arrays filled with ones; count additions rather than elapsed time:
n = 4: sum = 4, additions = 4
n = 8: sum = 8, additions = 8
n = 16: sum = 16, additions = 16
Doubling n doubles the additions: linear work, O(n).
Input for direct access: [A, B, C, D, E]
Middle index: 2
Array middle: C
Array middle: direct access, O(1).
```

## Demo09FunctionalComparators

```text
Three implementations of Comparator<String>, all ordering by length:

Named class input: [pear, banana, fig]
compare(pear, banana): -1
Named class: [fig, pear, banana]

Anonymous class input: [pear, banana, fig]
compare(pear, banana): -1
Anonymous class: [fig, pear, banana]

Lambda input: [pear, banana, fig]
compare(pear, banana): -1
Lambda: [fig, pear, banana]
```

## Demo10FunctionalOperations

```text
Predicate.test(4): true
Predicate.test(5): false
Supplier.get(): 4
Function.apply(4): Number 4
Consumer.accept: Number 4

Input array: [1, 2, 3, 4, 5, 6]
process(): test each value, transform accepted values, then consume them.
Even values from an array:
Consumer.accept: Number 2
Consumer.accept: Number 4
Consumer.accept: Number 6
Values greater than 4, squared:
Consumer.accept: Square 25
Consumer.accept: Square 36
Input after both traversals: [1, 2, 3, 4, 5, 6]
```

## Demo11Wildcards

```text
Rectangles: areas 6.0, 10.0
Unbounded wildcard: inspect size without knowing the element type.
Count: 2
Count: 2

? extends Shape: read shapes from a rectangle array.
Read through ? extends Shape: 6.0
Total rectangle area: 16.0
Total square area: 13.0

? super Rectangle: reuse a comparator that accepts any Shape.
Rectangle comparison: -1
Largest rectangle area: 10.0
Largest shape area: 10.0
Direct wildcard lambda: -1

? super Rectangle: add rectangles and squares to Shape/Object destinations.
Destination sizes: 2, 3
First Object destination element: Existing text
Added Shape destination areas: 6.0, 4.0
Object destination element types: String, Rectangle, Square
```

## Demo12Fibonacci

```text
First ten terms: 0 1 1 2 3 5 8 13 21 34
A fresh iterator starts again; traversal does not consume the sequence.
Iterator a: 0, 1
Iterator b starts at: 0
Independent positions: a.next() = 1; b.next() = 1
A zero-term sequence hasNext(): false
Request 94 terms, exceeding the safe long range:
Rejected: Expected 0 to 93 terms
```

## Demo13Bags

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

