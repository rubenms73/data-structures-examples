## Demo01ChainedHashSet

```text
1. Separate chaining; initial capacity 3, load limit 0.75
Initial: []; size = 0
add(-1): true; contents = [-1]; size = 1
add(-2147483648): true; contents = [-2147483648, -1]; size = 2
add(7): true; contents = [7, -2147483648, -1]; size = 3
The third distinct insertion exceeds 3 * 0.75 and triggers rehashing.
Iteration follows buckets; it is not sorted or arrival order.
contains(-1): true
contains(Integer.MIN_VALUE): true
contains(99): false
add(7) again: false; size = 3
remove(99): false; size = 3

2. Equal hash codes do not imply equal elements
Aa.hashCode(): 2112; BB.hashCode(): 2112
Aa.equals(BB): false
Both strings remain: [Aa, BB]; size = 2
remove(Aa): true; contains(BB): true

3. Iterator removal updates the total size
next(): 7; hasNext(): true
iterator.remove(): [-2147483648, -1]; size = 2
next(): -2147483648; hasNext(): true
iterator.remove(): [-1]; size = 1
next(): -1; hasNext(): false
iterator.remove(): []; size = 0
Empty: true
Reuse with add(42): [42]; size = 1
```

## Demo02MinHeap

```text
1. Inserting sifts the new value up
add(20): array = [20]
add(30): array = [20, 30]
add(25): array = [20, 30, 25]
add(25): array = [20, 25, 25, 30]
add(10): array = [10, 20, 25, 30, 25]
element(): 10; size = 5

2. Removing the minimum sifts the last element down
remove(): 10; array = [20, 25, 25, 30]
remove(): 20; array = [25, 30, 25]
remove(): 25; array = [25, 30]
remove(): 25; array = [30]
remove(): 30; array = []

3. Building a heap bottom-up from an array
From [9, 4, 7, 1, 3, 6, 2]: array = [1, 3, 2, 4, 9, 6, 7]

4. A reversed comparator gives a max-heap
Removed in order: plum pear fig apple
```

## Demo03LinearProbingSet

```text
1. Collisions move on to the next slot
Initial: [-, -, -, -, -, -, -]
add(7), start 0: true; table = [7, -, -, -, -, -, -]
add(1), start 1: true; table = [7, 1, -, -, -, -, -]
add(8), start 1: true; table = [7, 1, 8, -, -, -, -]
add(15), start 1: true; table = [7, 1, 8, 15, -, -, -]

2. A removed key leaves a DELETED marker
remove(8): true; table = [7, 1, DEL, 15, -, -, -]
contains(15): true (the search goes past DEL)
contains(8): false

3. Insertion reuses the first DELETED slot
add(15) again: false; table = [7, 1, DEL, 15, -, -, -]
add(22), start 1: true; table = [7, 1, 22, 15, -, -, -]
size = 4

4. A full table rejects a new key
add(3) to [2, 1]: Full
```
