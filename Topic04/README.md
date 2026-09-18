# Sets, maps and applications

Each example is self-contained and targets Java 17. Open an individual demo folder.

- [Sets and maps backed by lists](Demo01ListSetMap/README.md).
- [Sparse polynomial as an ordered map](Demo02Polynomial/README.md).
- [Sparse vectors and matrices](Demo03SparseStorage/README.md).
- [Lists, sets and inherited collection operations](Demo04Collections/README.md).

Demo04 follows the Set contract, AbstractCollection and LinkedHashSet discussion
in the presentation. It compares list/set behaviour before revisiting an
array-backed bag. The numerical folder order is not a prerequisite chain.

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

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01ListSetMap`.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
