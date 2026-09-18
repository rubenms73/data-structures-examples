# General rooted tree and preorder traversal

## Problem statement and prerequisites

Represent a nonempty rooted tree with an ordered list of children. This is not
a search tree: children have a sequence order, not a key ordering. Prerequisites:
Tree terminology, recursion, interfaces, abstract classes, lists and stacks.
Null labels and null subtrees are rejected with explicit conditionals.

## Guided walkthrough

1. Read Tree and AbstractTree: size and height use only the abstract operations.
2. ListTree owns its child list. addChild copies structure but shares label objects.
3. children returns a snapshot of child references, so removing from that snapshot
   cannot alter links. Child objects remain mutable and are not deep-copied there.
4. Copying on insertion prevents this representation from introducing cycles.
5. Preorder visits the root, then children in order. Push children in reverse order.
6. Height counts nodes on a longest root-to-leaf path, as in the presentation;
   every leaf has height one. This interface represents only nonempty trees.
   The presentation assigns height zero to an empty binary tree.

## Costs and contracts

Size, height, copying and traversal cost O(n). The preorder iterator's explicit
stack can hold O(n) pending nodes for a wide tree, not just O(height). Recursion
uses O(height) call frames; snapshots add temporary storage. addChild costs the
size of the copied subtree. Custom Tree sources must be finite and acyclic.
This introductory version omits parent links and a mutable children iterator:
those are reserved for instructor exercises. Do not mutate during traversal.

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

Trace Main by hand before running it. Exercise the empty case, boundaries and
rejected operations described above. Verify that a rejected single operation
leaves the structure unchanged. Compare the representation with its public contract.

## Expected output

```text
Company
Team
Developer
Support
Nodes: 4; height: 3
```

## Windows

Open a terminal in this folder (PowerShell, Command Prompt or the VS Code
terminal). Install a JDK 17 or newer and put its `bin` directory on `PATH`;
`java -version` and `javac -version` should both work. No Bash, WSL or Git Bash
is required.

```powershell
.\run.cmd
.\run.cmd test
```

The first command runs the demonstration; the second compiles and runs its checks.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
