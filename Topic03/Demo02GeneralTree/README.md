# General rooted tree and preorder traversal

## Problem statement and prerequisites

Represent a non-empty rooted tree in which each node has an ordered list of
children. This is not a search tree: the children are kept in a sequence, not
ordered by key. You should already know tree terminology, recursion, interfaces,
abstract classes, lists and stacks. `null` labels and `null` subtrees are
rejected with explicit conditionals.

## Guided walkthrough

1. Read `Tree` and `ChildrenIterator`. They are the interfaces used in the
   presentation and in Session 7: `isLeaf`, `label`, `setLabel` and
   `childrenIterator`, which returns the child subtrees from first to last.
2. `AbstractTree` computes `size` and `height` using only those operations.
3. `ListTree` owns its list of children. `addChild` copies the structure of the
   subtree but shares the label objects, and `removeChild` detaches one child.
4. Its children iterator is read-only: the optional `set` and `add`, and
   `remove`, throw `UnsupportedOperationException`. It returns the actual child
   subtrees, not copies. Session 7 implements a children iterator that can
   replace and insert children.
5. Because subtrees are copied on insertion, this representation cannot create
   cycles.
6. Preorder visits the root and then the children in order, so the children are
   pushed onto the stack in reverse order.
7. As in the presentation, the height counts the nodes on a longest path from
   the root to a leaf, so every leaf has height one. This interface represents
   only non-empty trees; the presentation gives an empty binary tree height
   zero.

## Costs and contracts

`size`, `height`, copying and traversal cost O(n). For a wide tree, the explicit
stack of the preorder iterator can hold O(n) pending nodes, not just O(height).
Recursion uses O(height) call frames.
`addChild` costs time proportional to the size of the copied subtree. Any other
implementation of `Tree` used as a source must be finite and acyclic.

This introductory version leaves out parent links, `equals` and an iterator
over the children that can modify them; those are left for Session 7. Do not modify the tree during a traversal.

## Open and run

Open this individual folder in VS Code with JDK 17 and Extension Pack for Java.
Run `src/app/Main.java`, or use:

```sh
bash run.sh
bash run.sh test
```

The project is self-contained. `src/ds` contains the implementation, `src/app`
the demonstration and `tests/tests` the automated checks. No external libraries
are required. Code and comments use English and Allman style.

## What to observe and try

Trace `Main` by hand before you run it. Try the empty case, the boundary cases
and the rejected operations described above, and check that an operation that is
rejected leaves the structure unchanged. Compare the representation with the
public contract it implements.

## Expected output

```text
addChild(team) copies the subtree into Company.
Original team label before change: Team
Original team label after change: Changed outside
The Company copy retains the Team label:
Company hierarchy:

+-- Company [ROOT]
+-- Team
|   \-- Developer
\-- Support

childrenIterator() of Company: Team Support (leaf)
This iterator is read-only: add throws UnsupportedOperationException.

Preorder traversal: parent before its children.
Visit: Company
Visit: Team
Visit: Developer
Visit: Support
Nodes: 4; height: 3
removeChild(1) returns: Support

============================================================
After removing Support: nodes = 3; height = 3

+-- Company [ROOT]
\-- Team
    \-- Developer

```

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher sets its own working directory and copes with paths containing
spaces and with any bundled JAR libraries. It runs on Windows PowerShell 5.1,
which comes with Windows, and `run.ps1` also works with PowerShell 7. The VS Code
**Run** and **Debug** buttons are still available when the example folder is open.

## Reading the text tree

The general tree prints its root first, followed by its children in order.
`+--`, `\--` and vertical `|` lines connect each child to its parent, and `\--`
marks the last child. There is no distinction between left and right as in a
binary tree. Read it in a monospaced font.

`toTreeString()` returns text, which `Main` prints with `System.out.print`; it
does not change the tree. The ordered traversal is still available. Labels that
contain line breaks or tabs are escaped, so that each node takes up one line.
The diagrams show the actual links, not a reconstruction from the sorted values,
and they show completed operations, not intermediate rotation states. They are
meant for small classroom trees: for a long chain, the indentation can make the
output quadratic in the height.

Each diagram is separated by blank lines from its heading and from the output
that follows. Removal scenarios have a horizontal separator. Binary diagrams use
wider spacing between levels, with lines that contain only connectors between a
parent level and its children.
