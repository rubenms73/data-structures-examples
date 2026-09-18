# Topic 3 expected output

## Demo01BinarySearchTree

```text
Initial tree (right above, left below):
    /-- 14
    |   \-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   \-- 4
\-- 3
    \-- 1
In order: [1, 3, 4, 6, 7, 8, 10, 13, 14]
Size: 9
Contains 6: true
Add duplicate 6: true
With the duplicate: [1, 3, 4, 6, 6, 7, 8, 10, 13, 14], size: 10
    /-- 14
    |   \-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   |   /-- 6
|   |   \-- 4
\-- 3
    \-- 1
Remove one 6: true
One 6 remains: true
Remove leaf 1: true -> [3, 4, 6, 7, 8, 10, 13, 14]
    /-- 14
    |   \-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   \-- 4
\-- 3
Remove node 14 with one child: true -> [1, 3, 4, 6, 7, 8, 10, 13]
    /-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   \-- 4
\-- 3
    \-- 1
Remove node 3 with two children: true -> [1, 4, 6, 7, 8, 10, 13, 14]
    /-- 14
    |   \-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   \-- 4
\-- 1
Remove root 8: true -> [1, 3, 4, 6, 7, 10, 13, 14]
    /-- 14
    |   \-- 13
/-- 10
+-- 7 [ROOT]
|   /-- 6
|   |   \-- 4
\-- 3
    \-- 1
Remove absent 99: false -> [1, 3, 4, 6, 7, 8, 10, 13, 14]
    /-- 14
    |   \-- 13
/-- 10
+-- 8 [ROOT]
|       /-- 7
|   /-- 6
|   |   \-- 4
\-- 3
    \-- 1
Reverse order: [14, 13, 10, 8, 7, 6, 4, 3, 1]
Original still contains 8: true
Ordered insertion: [1, 2, 3, 4, 5]
            /-- 5
        /-- 4
    /-- 3
/-- 2
+-- 1 [ROOT]
After clear: [], size: 0
(empty)
```

## Demo02GeneralTree

```text
Company hierarchy:
+-- Company [ROOT]
+-- Team
|   \-- Developer
\-- Support
Company
Team
Developer
Support
Nodes: 4; height: 3
After removing Support:
+-- Company [ROOT]
\-- Team
    \-- Developer
```

## Demo03AVLTree

```text
Inserted 1 through 15 in ascending order.
Tree structure (right above, left below):
        /-- 15 [h=1, bf=0]
    /-- 14 [h=2, bf=0]
    |   \-- 13 [h=1, bf=0]
/-- 12 [h=3, bf=0]
|   |   /-- 11 [h=1, bf=0]
|   \-- 10 [h=2, bf=0]
|       \-- 9 [h=1, bf=0]
+-- 8 [ROOT] [h=4, bf=0]
|       /-- 7 [h=1, bf=0]
|   /-- 6 [h=2, bf=0]
|   |   \-- 5 [h=1, bf=0]
\-- 4 [h=3, bf=0]
    |   /-- 3 [h=1, bf=0]
    \-- 2 [h=2, bf=0]
        \-- 1 [h=1, bf=0]
Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Adding duplicate 8: false
Removing 8: true
        /-- 15 [h=1, bf=0]
    /-- 14 [h=2, bf=0]
    |   \-- 13 [h=1, bf=0]
/-- 12 [h=3, bf=0]
|   |   /-- 11 [h=1, bf=0]
|   \-- 10 [h=2, bf=-1]
+-- 9 [ROOT] [h=4, bf=0]
|       /-- 7 [h=1, bf=0]
|   /-- 6 [h=2, bf=0]
|   |   \-- 5 [h=1, bf=0]
\-- 4 [h=3, bf=0]
    |   /-- 3 [h=1, bf=0]
    \-- 2 [h=2, bf=0]
        \-- 1 [h=1, bf=0]
Removing 1: true
        /-- 15 [h=1, bf=0]
    /-- 14 [h=2, bf=0]
    |   \-- 13 [h=1, bf=0]
/-- 12 [h=3, bf=0]
|   |   /-- 11 [h=1, bf=0]
|   \-- 10 [h=2, bf=-1]
+-- 9 [ROOT] [h=4, bf=0]
|       /-- 7 [h=1, bf=0]
|   /-- 6 [h=2, bf=0]
|   |   \-- 5 [h=1, bf=0]
\-- 4 [h=3, bf=0]
    |   /-- 3 [h=1, bf=0]
    \-- 2 [h=2, bf=-1]
Removing 15: true
    /-- 14 [h=2, bf=1]
    |   \-- 13 [h=1, bf=0]
/-- 12 [h=3, bf=0]
|   |   /-- 11 [h=1, bf=0]
|   \-- 10 [h=2, bf=-1]
+-- 9 [ROOT] [h=4, bf=0]
|       /-- 7 [h=1, bf=0]
|   /-- 6 [h=2, bf=0]
|   |   \-- 5 [h=1, bf=0]
\-- 4 [h=3, bf=0]
    |   /-- 3 [h=1, bf=0]
    \-- 2 [h=2, bf=-1]
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
        /-- 15 [B]
    /-- 14 [B]
    |   \-- 13 [B]
/-- 12 [B]
|   |   /-- 11 [B]
|   \-- 10 [B]
|       \-- 9 [B]
+-- 8 [ROOT] [B]
|       /-- 7 [B]
|   /-- 6 [B]
|   |   \-- 5 [B]
\-- 4 [B]
    |   /-- 3 [B]
    \-- 2 [B]
        \-- 1 [B]
Ordered contents: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
Balanced tree height (nodes): 4
A plain BST with this insertion order would have height 15.
Search for 15: true; comparisons: 4
Adding duplicate 8: false
Removing 8: true
    /-- 15 [B]
/-- 14 [B]
|   |   /-- 13 [B]
|   \-- 12 [R]
|       \-- 11 [B]
|           \-- 10 [R]
+-- 9 [ROOT] [B]
|       /-- 7 [B]
|   /-- 6 [B]
|   |   \-- 5 [B]
\-- 4 [R]
    |   /-- 3 [B]
    \-- 2 [B]
        \-- 1 [B]
Removing 1: true
    /-- 15 [B]
/-- 14 [B]
|   |   /-- 13 [B]
|   \-- 12 [R]
|       \-- 11 [B]
|           \-- 10 [R]
+-- 9 [ROOT] [B]
|   /-- 7 [B]
\-- 6 [B]
    |   /-- 5 [B]
    \-- 4 [R]
        \-- 3 [B]
            \-- 2 [R]
Removing 15: true
    /-- 14 [B]
    |   \-- 13 [R]
/-- 12 [B]
|   \-- 11 [B]
|       \-- 10 [R]
+-- 9 [ROOT] [B]
|   /-- 7 [B]
\-- 6 [B]
    |   /-- 5 [B]
    \-- 4 [R]
        \-- 3 [B]
            \-- 2 [R]
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
