## Demo01ListSetMap

```text
1. A set stores each distinct element once
Initial: []; size = 0
add(A): true; set = [A]; size = 1
add(A): false; set = [A]; size = 1
add(B): true; set = [A, B]; size = 2
contains(A): true; contains(X): false
remove(A): true; set = [B]
remove(A) again: false; set = [B]

2. A map associates one value with each key
put(A, 1), previous value: null; map = {A=1}
put(A, 2), previous value: 1; map = {A=2}; size = 1
put(B, 3), previous value: null; map = {A=2, B=3}
get(A): 2; get(X): null

3. Collection views are connected to the map
Keys: [A, B]; values: [2, 3]; entries: [A=2, B=3]
keys.remove(B): true; map = {A=2}
put(C, 4): map = {A=2, C=4}; existing key view = [A, C]
values().remove(2): true; map = {C=4}
clear(): map = {}; existing key view = []
```

## Demo02Polynomial

```text
Sparse notation: {exponent=coefficient}; absent exponents have coefficient zero.
Initial polynomial: {}; degree = -1
addTerm(2, 3), previous coefficient: 0.0; first = {2=3.0}
addTerm(0, 1), previous coefficient: 0.0; first = {0=1.0, 2=3.0}
First: {0=1.0, 2=3.0}; degree = 2
Second: {1=2.0, 2=-3.0}; degree = 2

Sum: {0=1.0, 1=2.0}; degree = 1
Coefficient of x^2 after cancellation: 0.0
Operands after plus(): first = {0=1.0, 2=3.0}; second = {1=2.0, 2=-3.0}
At x = 2: first = 13.0; second = -8.0; sum = 5.0
sum.addTerm(1, -2), previous coefficient: 2.0; sum = {0=1.0}
sum.addTerm(0, -1), previous coefficient: 1.0; sum = {}; degree = -1
Zero polynomial at x = 2: 0.0
```

## Demo03SparseStorage

```text
1. Logical length and stored entries are different
Initial vector: length = 1000000; stored entries = 0
Unset get(123): 0
set(999999, 7): stored entries = 1; logical length = 1000000
set(3, -2): stored entries = 2; logical length = 1000000
set(999999, 9): replacing a value: stored entries = 2; logical length = 1000000
set(999999, 0): remove its stored entry: stored entries = 1; logical length = 1000000
get(999999) after removal: 0

2. A 1000 x 1000 matrix stores only nonempty rows
Initial stored rows: 0
Unset get(5, 9): 0
set(5, 9, 12): value = 12; stored rows = 1
set(5, 10, 4): stored rows = 1
set(8, 2, -3): stored rows = 2
Clear (5, 9): stored rows = 2; row 5 still contains (5, 10) = 4
Clear last entry of row 5: stored rows = 1
Clear last entry of row 8: stored rows = 0
```

## Demo04Collections

```text
List: pear fig pear
Set: pear fig
Same arrivals: list size = 3; set size = 2
list.add(pear): true; list = [pear, fig, pear, pear]
set.add(pear): false; set = [pear, fig]
Bag copied from the list: [pear, fig, pear, pear]; size = 4
Source after clear(): []; size = 0
Bag after clearing its source: pear fig pear pear
Inherited contains(fig): true
Inherited isEmpty(): false
Adding to the bag: UnsupportedOperationException
Bag after rejected add: [pear, fig, pear, pear]; size = 4
```
