# Graphs and road navigation

Each example is self-contained and targets Java 17.

- [Road navigation and Dijkstra](Demo01WeightedGraph/README.md): a sourced road
  network in northern Spain, national-road/motorway alternatives, temporary
  closures, removal/restoration and incident penalties. Both Dijkstra variants
  recompute the route after each change.

Windows: `.\run.cmd list`, `.\run.cmd all`, `.\run.cmd test`.
macOS/Linux: `bash run.sh list`, `bash run.sh all`, `bash run.sh test`.
Open the individual demo folder in VS Code to run or debug `app.Main`.

The navigation demo also generates `Demo01WeightedGraph/bin/navigation-map.html`: an offline map with road geometry, incident scenarios, distances and costs. Run route-specific arguments from the demo folder; see its README for commands.
