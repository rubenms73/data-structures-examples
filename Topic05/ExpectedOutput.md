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
