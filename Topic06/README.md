# Graphs: traversals, minimum spanning trees and road navigation

Each example is self-contained and targets Java 17. Read them in this order:

1. [Breadth-first and depth-first traversals](Demo01GraphTraversals/README.md):
   the exact graph from the theory presentation, with parent trees and complete
   traversal forests that match its alphabetical DFS and BFS traces.
2. [Minimum spanning tree for a campus
   network](Demo02NetworkSpanningTree/README.md): illustrates the idea of a
   minimum spanning tree with six buildings and the cost of cabling them. **In
   class, only the concept is mentioned. Prim's algorithm is not taught and is
   not required for the exam; its implementation is optional additional
   material.**
3. [Road navigation and Dijkstra](Demo03WeightedGraph/README.md): the final and
   largest application, with alternatives between national roads and motorways,
   closures, reopening and temporary penalties for incidents.

Each demo has its own launcher. Inside the demo's folder, on Windows, run
`.\run.cmd` or `.\run.cmd test`; on macOS and Linux, run `bash run.sh` or
`bash run.sh test`. To run or debug `app.Main`, open the demo's own folder in
VS Code.

The navigation demo generates `Demo03WeightedGraph/bin/navigation-map.html`, an
offline map with the road geometry, the incident scenarios, the distances and
the costs. Commands with route-specific arguments are run from the demo folder;
its README lists them.
