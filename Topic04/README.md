# Sets, maps and applications

Each example is self-contained and targets Java 17; open the folder of the demo
you want to study.

- [Sets and maps backed by lists](Demo01ListSetMap/README.md).
- [Sparse polynomial as an ordered map](Demo02Polynomial/README.md).
- [Sparse vectors and matrices](Demo03SparseStorage/README.md).
- [Lists, sets and inherited collection
  operations](Demo04Collections/README.md).

Demo04 follows the part of the presentation on the `Set` contract,
`AbstractCollection` and `LinkedHashSet`. It compares the behaviour of lists and
sets before returning to an array-backed bag. The folders are numbered for
convenience; you do not have to study them in that order.

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

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01ListSetMap`.
The launcher sets its own working directory and copes with paths containing
spaces and with any bundled JAR libraries. It runs on Windows PowerShell 5.1,
which comes with Windows, and `run.ps1` also works with PowerShell 7. The VS Code
**Run** and **Debug** buttons are still available when the example folder is open.
