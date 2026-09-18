# Trees

Each example is self-contained and targets Java 17. Open an individual demo folder.

- [01 — Binary search tree](Demo01BinarySearchTree/README.md).
- [02 — General rooted tree and preorder traversal](Demo02GeneralTree/README.md).
- [03 — AVL trees and logarithmic height](Demo03AVLTree/README.md).
- [04 — Red-black trees and their use in Java](Demo04RedBlackTree/README.md).

The two balanced-tree demos include full insertion and deletion algorithms.
Their classroom focus is the purpose of balancing and its effect on costs;
rotation/recolouring implementation is optional study, not required for the exam.
Both use height in nodes (empty = 0, leaf = 1), following the presentation.
Demo04 briefly previews `TreeMap` and `TreeSet`; the full collection API belongs
to Topic 4. These new demos store one element per comparison key, whereas Demo01
retains repeated occurrences; their READMEs explain why this matters for rotations.

Run `bash run.sh list`, `bash run.sh all`, `bash run.sh test`, or select a listed demo.

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install a JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd list
.\run.cmd all
.\run.cmd test
```

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01BinarySearchTree`.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.

All four demos print the actual tree structure. Binary trees are shown sideways;
AVL nodes include height and balance factor, and red-black nodes include colour.
The general tree uses an indented root-first hierarchy. See each README for the legend.
