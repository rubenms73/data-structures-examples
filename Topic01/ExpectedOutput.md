# Topic 1 — Expected output

Output from the unmodified examples. The demonstrations use fixed inputs.

## Demo01Rational

```text
3/4 = 0.75
3/4 = 0.75
r and a refer to the same object: true
a and b refer to the same object: false
a and b return the same value: true
Rejected: Zero denominator
```

## Demo02IntArrays

```text
Fixed: [4, 8, 12]; sum = 24
Dynamic: [4, 8, 12]; sum = 24
After set(1, 10): [4, 10, 12]
size = 3, contains(10) = true
Fixed add(16): IllegalStateException
Fixed still contains: [4, 10, 12]
Dynamic after add(16): [4, 8, 12, 16]; sum = 40
```

## Demo03GenericArrays

```text
First name: Ana
First score: 8
Object[] accepts mixed values; the wrong cast fails at run time.
```

## Demo04StringSorting

```text
pear.compareTo(banana): 14
Original: [pear, banana, fig]
Natural order: [banana, fig, pear]
Length order: [fig, pear, banana]
```

## Demo05Movies

```text
Natural order: year, then title, then rating
  Blue Planet (2018, 8.6)
  Winter Lights (2018, 7.9)
  A Quiet Harbour (2020, 8.6)
  The Last Train (2022, 7.4)
Rating: highest first
  Blue Planet (2018, 8.6)
  A Quiet Harbour (2020, 8.6)
  Winter Lights (2018, 7.9)
  The Last Train (2022, 7.4)
Title order
  A Quiet Harbour (2020, 8.6)
  Blue Planet (2018, 8.6)
  The Last Train (2022, 7.4)
  Winter Lights (2018, 7.9)
```

## Demo06Maximum

```text
Alphabetical maximum: pear
Longest word: banana
Original array: [pear, banana, fig]
Rejected: Empty array
```

## Demo07Iterators

```text
hasNext(): true
hasNext() again: true
first.next(): Ana
first.next(): Ruben
second.next(): Ana
first.hasNext(): false
Next after the end: NoSuchElementException
A fresh enhanced for loop: Ana Ruben
Integer vector sum: 18
```

## Demo08Costs

```text
n = 4: sum = 4, additions = 4
n = 8: sum = 8, additions = 8
n = 16: sum = 16, additions = 16
Array middle: C
Array middle: direct access, O(1).
```

## Demo09FunctionalComparators

```text
Named class: [fig, pear, banana]
Anonymous class: [fig, pear, banana]
Lambda: [fig, pear, banana]
```

## Demo10FunctionalOperations

```text
Predicate.test(4): true
Predicate.test(5): false
Supplier.get(): 4
Function.apply(4): Number 4
Consumer.accept: Number 4
Even values from an array:
Consumer.accept: Number 2
Consumer.accept: Number 4
Consumer.accept: Number 6
Values greater than 4, squared:
Consumer.accept: Square 25
Consumer.accept: Square 36
```

## Demo11Wildcards

```text
Count: 2
Count: 2
Read through ? extends Shape: 6.0
Total rectangle area: 16.0
Total square area: 13.0
Rectangle comparison: -1
Largest rectangle area: 10.0
Largest shape area: 10.0
Direct wildcard lambda: -1
Destination sizes: 2, 3
First Object destination element: Existing text
```

## Demo12Fibonacci

```text
First ten terms: 0 1 1 2 3 5 8 13 21 34
Iterator a: 0, 1
Iterator b starts at: 0
Rejected: Expected 0 to 93 terms
```

## Demo13Bags

```text
Immutable original: [2, -3, 2, 18]
New immutable bag: [2, -3, 2, 18, 7]
Without one 2: [-3, 2, 18]
Mutable after add and remove: [-3, 2, 18, 7]
Equal bags (same multiplicities): 2
Sorted: [-3, 2, 2, 18]
Same bag despite order: true
Words: 322
Occurrences of the: 15
Occurrences of for: 1
```
