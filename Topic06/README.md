# Graphs: traversals, minimum spanning trees and road navigation

Each example is self-contained and targets Java 17. Read them in this order:

1. [Breadth-first and depth-first traversals](Demo01GraphTraversals/README.md):
   the exact graph from the theory presentation, with parent trees and complete
   traversal forests matching its alphabetical DFS and BFS traces.
2. [Minimum spanning tree for a campus network](Demo02NetworkSpanningTree/README.md):
   illustrates the minimum spanning tree concept with six buildings and cable costs.
   **Only the concept is mentioned in class. Prim's algorithm is not taught and is
   not required for the exam; its implementation is optional supplementary material.**
3. [Road navigation and Dijkstra](Demo03WeightedGraph/README.md):
   the final, larger application, with national-road/motorway alternatives,
   closures, restoration and temporary incident penalties.

Windows: `.\run.cmd list`, `.\run.cmd all`, `.\run.cmd test`.
macOS/Linux: `bash run.sh list`, `bash run.sh all`, `bash run.sh test`.
Open the individual demo folder in VS Code to run or debug `app.Main`.

The navigation demo generates `Demo03WeightedGraph/bin/navigation-map.html`:
an offline map with road geometry, incident scenarios, distances and costs.
Run route-specific arguments from the demo folder; see its README for commands.
