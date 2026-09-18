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
