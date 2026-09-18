# Topic 3 expected output

## Demo01BinarySearchTree

```text
Initial tree (right above, left below):
        R: 14
            L: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
            L: 4
    L: 3
        L: 1
In order: [1, 3, 4, 6, 7, 8, 10, 13, 14]
Size: 9
Contains 6: true
Add duplicate 6: true
With the duplicate: [1, 3, 4, 6, 6, 7, 8, 10, 13, 14], size: 10
        R: 14
            L: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
                R: 6
            L: 4
    L: 3
        L: 1
Remove one 6: true
One 6 remains: true
Remove leaf 1: true -> [3, 4, 6, 7, 8, 10, 13, 14]
        R: 14
            L: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
            L: 4
    L: 3
Remove node 14 with one child: true -> [1, 3, 4, 6, 7, 8, 10, 13]
        R: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
            L: 4
    L: 3
        L: 1
Remove node 3 with two children: true -> [1, 4, 6, 7, 8, 10, 13, 14]
        R: 14
            L: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
            L: 4
    L: 1
Remove root 8: true -> [1, 3, 4, 6, 7, 10, 13, 14]
        R: 14
            L: 13
    R: 10
ROOT: 7
        R: 6
            L: 4
    L: 3
        L: 1
Remove absent 99: false -> [1, 3, 4, 6, 7, 8, 10, 13, 14]
        R: 14
            L: 13
    R: 10
ROOT: 8
            R: 7
        R: 6
            L: 4
    L: 3
        L: 1
Reverse order: [14, 13, 10, 8, 7, 6, 4, 3, 1]
Original still contains 8: true
Ordered insertion: [1, 2, 3, 4, 5]
                R: 5
            R: 4
        R: 3
    R: 2
ROOT: 1
After clear: [], size: 0
(empty)
```

## Demo02GeneralTree

```text
Company hierarchy:
ROOT: Company
    - Team
        - Developer
    - Support
Company
Team
Developer
Support
Nodes: 4; height: 3
After removing Support:
ROOT: Company
    - Team
        - Developer
```

## Demo03AVLTree

```text
Inserted 1 through 15 in ascending order.
Tree structure (right above, left below):
            R: 15 [h=1, bf=0]
        R: 14 [h=2, bf=0]
            L: 13 [h=1, bf=0]
    R: 12 [h=3, bf=0]
            R: 11 [h=1, bf=0]
        L: 10 [h=2, bf=0]
            L: 9 [h=1, bf=0]
ROOT: 8 [h=4, bf=0]
            R: 7 [h=1, bf=0]
        R: 6 [h=2, bf=0]
            L: 5 [h=1, bf=0]
    L: 4 [h=3, bf=0]
            R: 3 [h=1, bf=0]
        L: 2 [h=2, bf=0]
            L: 1 [h=1, bf=0]
Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Adding duplicate 8: false
Removing 8: true
            R: 15 [h=1, bf=0]
        R: 14 [h=2, bf=0]
            L: 13 [h=1, bf=0]
    R: 12 [h=3, bf=0]
            R: 11 [h=1, bf=0]
        L: 10 [h=2, bf=-1]
ROOT: 9 [h=4, bf=0]
            R: 7 [h=1, bf=0]
        R: 6 [h=2, bf=0]
            L: 5 [h=1, bf=0]
    L: 4 [h=3, bf=0]
            R: 3 [h=1, bf=0]
        L: 2 [h=2, bf=0]
            L: 1 [h=1, bf=0]
Removing 1: true
            R: 15 [h=1, bf=0]
        R: 14 [h=2, bf=0]
            L: 13 [h=1, bf=0]
    R: 12 [h=3, bf=0]
            R: 11 [h=1, bf=0]
        L: 10 [h=2, bf=-1]
ROOT: 9 [h=4, bf=0]
            R: 7 [h=1, bf=0]
        R: 6 [h=2, bf=0]
            L: 5 [h=1, bf=0]
    L: 4 [h=3, bf=0]
            R: 3 [h=1, bf=0]
        L: 2 [h=2, bf=-1]
Removing 15: true
        R: 14 [h=2, bf=1]
            L: 13 [h=1, bf=0]
    R: 12 [h=3, bf=0]
            R: 11 [h=1, bf=0]
        L: 10 [h=2, bf=-1]
ROOT: 9 [h=4, bf=0]
            R: 7 [h=1, bf=0]
        R: 6 [h=2, bf=0]
            L: 5 [h=1, bf=0]
    L: 4 [h=3, bf=0]
            R: 3 [h=1, bf=0]
        L: 2 [h=2, bf=-1]
After removals: [2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14]
Height after removals: 4
First / last: 2 / 14
All invariants hold: true
After clear: size=0, height=0
(empty)
Focus: purpose and logarithmic costs. Repair code is not required for the exam.
```

## Demo04RedBlackTree

```text
Inserted 1 through 15 in ascending order.
Tree structure (right above, left below):
            R: 15 [B]
        R: 14 [B]
            L: 13 [B]
    R: 12 [B]
            R: 11 [B]
        L: 10 [B]
            L: 9 [B]
ROOT: 8 [B]
            R: 7 [B]
        R: 6 [B]
            L: 5 [B]
    L: 4 [B]
            R: 3 [B]
        L: 2 [B]
            L: 1 [B]
Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Adding duplicate 8: false
Removing 8: true
        R: 15 [B]
    R: 14 [B]
            R: 13 [B]
        L: 12 [R]
            L: 11 [B]
                L: 10 [R]
ROOT: 9 [B]
            R: 7 [B]
        R: 6 [B]
            L: 5 [B]
    L: 4 [R]
            R: 3 [B]
        L: 2 [B]
            L: 1 [B]
Removing 1: true
        R: 15 [B]
    R: 14 [B]
            R: 13 [B]
        L: 12 [R]
            L: 11 [B]
                L: 10 [R]
ROOT: 9 [B]
        R: 7 [B]
    L: 6 [B]
            R: 5 [B]
        L: 4 [R]
            L: 3 [B]
                L: 2 [R]
Removing 15: true
        R: 14 [B]
            L: 13 [R]
    R: 12 [B]
        L: 11 [B]
            L: 10 [R]
ROOT: 9 [B]
        R: 7 [B]
    L: 6 [B]
            R: 5 [B]
        L: 4 [R]
            L: 3 [B]
                L: 2 [R]
After removals: [2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14]
Height after removals: 5
First / last: 2 / 14
All invariants hold: true
TreeSet ordered contents: [10, 20, 30]
TreeMap ordered keys and values: {10=item-10, 20=item-20, 30=item-30}
Java 17: TreeMap uses a red-black tree; TreeSet is based on TreeMap.
Our LLRB implementation is not a copy of TreeMap's internal algorithm.
After clear: size=0, height=0
(empty)
Focus: purpose and logarithmic costs. Repair code is not required for the exam.
```
