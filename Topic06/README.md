# Graphs: traversals and road navigation

Each example is self-contained and targets Java 17.

- [Road navigation and Dijkstra](Demo01WeightedGraph/README.md): a sourced road
  network in northern Spain, national-road/motorway alternatives, temporary
  closures, removal/restoration and incident penalties. Both Dijkstra variants
  recompute the route after each change.

- [Breadth-first and depth-first traversals](Demo02GraphTraversals/README.md):
  a small directed graph with a cycle, parent trees, disconnected vertices and
  complete traversal forests, following the theory pseudocode.

- [Minimum spanning tree for a campus network](Demo03NetworkSpanningTree/README.md):
  connect six buildings with minimum total cable cost, using Prim; remove a link
  and compute an alternative. Includes diagrams and comparison with shortest paths.

For teaching, start with Demo02 to study traversal, then use Demo01 for weighted
shortest paths and the navigation application. Existing demo names are retained.

Windows: `.\run.cmd list`, `.\run.cmd all`, `.\run.cmd test`.
macOS/Linux: `bash run.sh list`, `bash run.sh all`, `bash run.sh test`.
Open the individual demo folder in VS Code to run or debug `app.Main`.

The navigation demo also generates `Demo01WeightedGraph/bin/navigation-map.html`: an offline map with road geometry, incident scenarios, distances and costs. Run route-specific arguments from the demo folder; see its README for commands.
