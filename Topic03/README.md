# Trees

Each example is self-contained and targets Java 17; open the folder of the demo
you want to study.

- [01 — Binary search tree](Demo01BinarySearchTree/README.md).
- [02 — General rooted tree and preorder
  traversal](Demo02GeneralTree/README.md).
- [03 — AVL trees and logarithmic height](Demo03AVLTree/README.md).
- [04 — Red-black trees and their use in Java](Demo04RedBlackTree/README.md).
- [05 — Preorder, inorder, postorder and level-order
  iterators](Demo05TreeTraversals/README.md).

The two balanced-tree demos include the full insertion and deletion algorithms.
In class the focus is on why trees are balanced and how balancing affects costs;
the code for rotations and recolouring is optional study and is not required for
the exam. Following the presentation, both demos store the height in each node
(an empty tree has height 0 and a leaf has height 1). Demo04 gives a brief
preview of `TreeMap` and `TreeSet`, while the full collection API belongs to
Topic 4. These two demos store one element per comparison key, whereas Demo01
keeps repeated occurrences; their READMEs explain why this matters for
rotations.

Run `bash run.sh list`, `bash run.sh all` or `bash run.sh test`, or choose one
of the listed demos.

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd list
.\run.cmd all
.\run.cmd test
```

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01BinarySearchTree`.
The launcher sets its own working directory and copes with paths containing
spaces and with any bundled JAR libraries. It runs on Windows PowerShell 5.1,
which comes with Windows, and `run.ps1` also works with PowerShell 7. The VS Code
**Run** and **Debug** buttons are still available when the example folder is open.

All five demos print the actual structure of the tree, with ASCII branches
connecting the nodes. Binary trees are shown sideways; AVL nodes include their
height and balance factor, and red-black nodes include their colour. The general
tree is shown as an indented hierarchy, root first. Each README explains its
legend.
