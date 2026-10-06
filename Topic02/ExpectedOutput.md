## Demo01SinglyLinkedList

```text
Initial collection: []; size = 0
add(A): [A]; size = 1
add(B): [A, B]; size = 2
add(C): [A, B, C]; size = 3

Traversal with for-each:
Visit: A
Visit: B
Visit: C
After traversal: [A, B, C]

Remove C through the iterator:
next(): A
next(): B
next(): C
iterator.remove(): [A, B]
add(D), reusing the tail after removal: [A, B, D]

Inherited Collection operations:
contains(B): true
remove(B): true; contents = [A, D]
remove(X): false; contents = [A, D]
clear(): []; size = 0; empty = true
```

## Demo02LinkedList

```text
Initial list and cursor: [1, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
iterator.add(0): insert before the cursor: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
next(): 1
After moving forwards: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 1, nextIndex = 2, hasPrevious = true, hasNext = true
remove(): delete the element returned by next(): [0, 2, 3]; size = 3
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
previous(): 0
After moving backwards: [0, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
set(10): replace the element returned by previous(): [10, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
remove(): delete that element without skipping its successor: [2, 3]; size = 2
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true

Indexed operations inherited from AbstractSequentialList:
get(1): 3
Copy before modification: [2, 3]
copy.set(0, 2.5) replaced: 2
Copy after modification: [2.5, 3]
Original remains: [2, 3]
Original after clear(): []; size = 0
Copy after clearing original: [2.5, 3]
```

## Demo03DoublyLinkedList

```text
Initial list and cursor: [1, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
iterator.add(0): insert before the cursor: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
next(): 1
After moving forwards: [0, 1, 2, 3]; size = 4
  Cursor: previousIndex = 1, nextIndex = 2, hasPrevious = true, hasNext = true
remove(): delete the element returned by next(): [0, 2, 3]; size = 3
  Cursor: previousIndex = 0, nextIndex = 1, hasPrevious = true, hasNext = true
previous(): 0
After moving backwards: [0, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
set(10): replace the element returned by previous(): [10, 2, 3]; size = 3
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true
remove(): delete that element without skipping its successor: [2, 3]; size = 2
  Cursor: previousIndex = -1, nextIndex = 0, hasPrevious = false, hasNext = true

Indexed operations inherited from AbstractSequentialList:
get(1): 3
Copy before modification: [2, 3]
copy.set(0, 2.5) replaced: 2
Copy after modification: [2.5, 3]
Original remains: [2, 3]
Original after clear(): []; size = 0
Copy after clearing original: [2.5, 3]
```

## Demo04QueuePolicies

```text
1. Same arrivals, different insertion policies
  FIFO: []; size = 0
  LIFO: []; size = 0
offer(1) to both queues:
  FIFO: [1]; size = 1
  LIFO: [1]; size = 1
offer(2) to both queues:
  FIFO: [1, 2]; size = 2
  LIFO: [2, 1]; size = 2
offer(3) to both queues:
  FIFO: [1, 2, 3]; size = 3
  LIFO: [3, 2, 1]; size = 3

2. Inspect and traverse without removing
FIFO peek(), oldest arrival: 1
LIFO peek(), newest arrival: 3
FIFO for-each: 1 2 3
LIFO for-each: 3 2 1
After inspection and traversal:
  FIFO: [1, 2, 3]; size = 3
  LIFO: [3, 2, 1]; size = 3

3. Extract, then accept a new arrival
FIFO remove(): 1; remaining = [2, 3]
LIFO remove(): 3; remaining = [2, 1]
offer(4) to both queues:
  FIFO: [2, 3, 4]; size = 3
  LIFO: [4, 2, 1]; size = 3
FIFO remove(): 2; remaining = [3, 4]
LIFO remove(): 4; remaining = [2, 1]
FIFO remove(): 3; remaining = [4]
LIFO remove(): 2; remaining = [1]
FIFO remove(): 4; remaining = []
LIFO remove(): 1; remaining = []

4. Empty queues: special values and exceptions
FIFO peek(): null; poll(): null
FIFO element(): NoSuchElementException
FIFO remove(): NoSuchElementException
FIFO offer(null): NullPointerException; contents = []
LIFO peek(): null; poll(): null
LIFO element(): NoSuchElementException
LIFO remove(): NoSuchElementException
LIFO offer(null): NullPointerException; contents = []
add(9): both queues can be reused
  FIFO: [9]; size = 1
  LIFO: [9]; size = 1
clear():
  FIFO: []; size = 0
  LIFO: []; size = 0
```

## Demo05SortedPriorityQueue

```text
Natural order: smallest value leaves first
Initial: []; size = 0
offer(7): [7]; size = 1
offer(2): [2, 7]; size = 2
offer(5): [2, 5, 7]; size = 3
offer(2): [2, 2, 5, 7]; size = 4
peek(): 2; contents unchanged = [2, 2, 5, 7]
remove(): 2; remaining = [2, 5, 7]; size = 3
remove(): 2; remaining = [5, 7]; size = 2
remove(): 5; remaining = [7]; size = 1
remove(): 7; remaining = []; size = 0
Empty peek(): null; empty poll(): null

Reverse comparator: largest value leaves first
Initial: []; size = 0
offer(7): [7]; size = 1
offer(2): [7, 2]; size = 2
offer(5): [7, 5, 2]; size = 3
offer(2): [7, 5, 2, 2]; size = 4
peek(): 7; contents unchanged = [7, 5, 2, 2]
remove(): 7; remaining = [5, 2, 2]; size = 3
remove(): 5; remaining = [2, 2]; size = 2
remove(): 2; remaining = [2]; size = 1
remove(): 2; remaining = []; size = 0
Empty peek(): null; empty poll(): null
```

## Demo06PeekingIterator

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

## Demo07StacksAcrossLanguages

```text
Initial stack: size = 0; empty = true
push(1): size = 1; top = 1
push(2): size = 2; top = 2
push(3): size = 3; top = 3
Traversal from top to bottom: 3 2 1
After traversal: size = 3; top = 3
pop(): 3; size = 2; top = 2
pop(): 2; size = 1; top = 1
pop(): 1; size = 0; top = (empty)
Reuse with push(9): size = 1; top = 9
pop(): 9; size = 0; top = (empty)
```
