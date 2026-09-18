# Graphs

Self-contained Java 17 examples accompanying the current Topic 6 presentation.

- [Weighted graph and Dijkstra: Asturias and León](Demo01WeightedGraph/README.md):
  29 localities loaded from JSON, sorted adjacency maps, graph mutations,
  independent result snapshots, linear and heap-based shortest paths.

Read the graph and Dijkstra explanations before this example. Maps, comparators,
sets and priority queues come from earlier topics. The two-way road model uses
pairs of directed arcs; its kilometre weights are illustrative teaching data.

Run `bash run.sh list`, `bash run.sh all`, `bash run.sh test`, or
`bash run.sh Demo01WeightedGraph`. Open the individual demo folder in VS Code.
See [expected output](ExpectedOutput.md) for the default network and route.

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

Select a demo by passing its name from `list`, for example `.\run.cmd Demo01WeightedGraph`.
The launcher handles its own working directory, paths with spaces and any
bundled JAR libraries. It uses the included Windows PowerShell 5.1;
`run.ps1` also works with PowerShell 7. VS Code's **Run** and **Debug** buttons
remain available when the individual example folder is open.
