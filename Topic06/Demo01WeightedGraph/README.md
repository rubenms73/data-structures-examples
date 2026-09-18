# Weighted graphs and Dijkstra: Asturias and León

## Problem and prerequisites

Find a minimum-distance route in a network of localities. Represent the network
with adjacency maps, then compare two implementations of Dijkstra: a linear
minimum scan and a priority queue. Read the graph terminology, weighted directed
graph and Dijkstra sections of **Topic 6** first. Maps, sets, comparators and the
priority queue from earlier topics are prerequisites.

The supplied JSON contains **29 localities and 40 two-way connections** (80
directed arcs). Names are real; the rounded kilometre weights are **invented
teaching data, not measured road distances**. They do not constitute a navigation
dataset. The example illustrates shortest paths within this model, not the
shortest real journey between two towns.

## Run it

Open **this individual demo folder** in VS Code, with JDK 17 and Extension Pack
for Java. Run `src/app/Main.java`, or use the terminal:

```sh
bash run.sh
bash run.sh test
bash run.sh run data/asturias-leon.json 'Cudillero' 'Ponferrada'
```

The optional arguments are the JSON filename, source and destination. All three
must be supplied together. The default source is Gijón and the target is León.
`run.sh` changes to the example folder before loading relative filenames. The
VS Code launch configurations also use that folder as their working directory.

No Maven, Gradle or download is required at run time. The bundled Gson library
parses JSON; its compile-time annotation dependency and both licences are in
`lib`. The graph and algorithms themselves use only the Java standard library.
JSON parsing is supporting infrastructure, not an additional algorithm students
need to implement in this lesson.

## Files and reading order

1. `data/asturias-leon.json`: inspect vertices and roads before reading Java.
2. `src/ds/WeightedGraph.java`: representation, graph operations, linear Dijkstra,
   immutable queue entries, heap Dijkstra and the shared relaxation operation.
3. `src/ds/ShortestPaths.java`: a result snapshot and path reconstruction.
4. `src/app/Main.java`: load the network, print adjacency lists, compare distances
   and show both paths. One search produces all distances; printing another path
   from that result does not repeat Dijkstra.
5. `src/io/RoadNetworkReader.java`: UTF-8 loading and schema checks.
6. `tests/tests/ExampleChecks.java`: contracts, JSON errors and independent
   shortest-path verification. These are checks, not a second teaching demo.

## Graph contract and relation to theory

The class follows the current Topic 6 presentation's simple directed graph:

- The constructor receives `Comparator<? super V>`. Vertices need not implement
  `Comparable`. The comparator must be consistent with `equals`, and comparison,
  equality and hash codes must remain stable while vertices are in use.
- A vertex cannot be null. `addEdge` creates missing endpoints, but rejects
  non-finite weights before changing anything. Finite negative weights are valid
  graph data; **both Dijkstra methods reject every negative edge**, including
  edges in components unreachable from the source.
- Self-loops and duplicate arcs return `false` without changing the graph or an
  existing weight. A rejected self-loop does not create its vertex.
- `weightEdge` throws `NoSuchElementException` for an absent arc. There is no
  ambiguous sentinel weight for an absent edge.
- Removing a vertex removes and counts all its outgoing and incoming arcs.
  Removing an arc keeps the endpoint vertices. Missing removals return `false`.
- `vertices` and `adjacentsTo` are ordered, read-only views. Their elements cannot
  be removed through those views. Obtain a new neighbour view after removing and
  re-adding its vertex. Degree queries require an existing vertex.
- A copy has independent outer and adjacency maps; vertex objects are shared.

`vertexCount`, `edgeCount` and `vertices` correspond to the slides' `getNumVertices`,
`getNumEdges` and `getAllVertices`. `ShortestPaths` is an ordinary documented class
rather than a record. Its maps are immutable snapshots in comparator order, so
subsequent graph changes cannot invalidate an earlier result. Paths are newly
allocated lists. The API does not keep a mutable 'last Dijkstra run' in the graph.

The earlier public minimal graph allowed self-loops and rejected negative edges
at insertion. This version deliberately aligns those contracts, the sorted-map
representation and the neighbour views with the current theory presentation.

## Follow Dijkstra step by step

Initially the source has distance zero and every other vertex has infinity.
The next vertex is the unsettled one with the smallest tentative distance.
Once settled, its distance is final because remaining edges are nonnegative.
Relaxing an outgoing edge means testing whether travelling through this vertex
improves its neighbour's distance. Record the new predecessor only for a strict
improvement; zero-weight edges and equal-cost alternatives are valid.

In `dijkstra`, scan all distances to choose the minimum. Stop when no finite
unsettled distance remains: disconnected vertices keep infinity.

In `dijkstraHeap`, insert an immutable `(vertex, distance)` entry for every
improvement. `PriorityQueue` does not provide decrease-key. An earlier, worse
entry may remain in the queue; skip it after its vertex has been settled. Never
make the queue comparator read a mutable distance map. Ties use the graph's
vertex comparator, making this example reproducible.

For a small trace, use arcs A→B:10, A→C:1, C→B:2. Processing A inserts B:10 and
C:1; processing C inserts B:3. B:3 is removed first. The later B:10 entry is
ignored. Follow this sequence with a breakpoint at `queue.remove()`.

The default Gijón→León route is:

`Gijón → Oviedo → Mieres → Pola de Lena → Campomanes → Pajares → Villamanín → La Robla → León`

Its model cost is `30 + 20 + 15 + 8 + 25 + 21 + 25 + 26 = 170 km`.
The alternative Campomanes→La Robla arc costs 72; the route through Pajares and
Villamanín costs 71, illustrating that fewer arcs need not mean a shorter route.

To reconstruct a path, follow predecessor links from the target and add each
vertex to the front of a list. An unreachable target gives an empty list. The
source gives a one-element list. Unknown targets and sources are rejected.
Overflow of a candidate distance throws `ArithmeticException`; infinity is
reserved for unreachable vertices. Ordinary double rounding still applies.

## JSON format and changes to try

```json
{
  "description": "A small example; this text is optional",
  "directed": false,
  "vertices": ["Gijón", "Oviedo", "León"],
  "roads": [
    {"from": "Gijón", "to": "Oviedo", "km": 30},
    {"from": "Oviedo", "to": "León", "km": 140}
  ]
}
```

`directed`, `vertices` and `roads` are required and have the types shown. With
`directed: false`, list each road once: the loader adds both arcs with the same
weight. With `true`, each entry adds only `from → to`. Do not include a second
reversed entry for a two-way road. Locality names must be nonempty strings with
no outer spaces. Every road endpoint must already be listed in `vertices`.
Distances must be JSON numbers, finite and nonnegative; numeric strings are not
accepted. Duplicate vertices, duplicate arcs and self-loops are errors. Invalid
input never returns a partially built graph. Description and other extra metadata
are ignored. UTF-8 preserves accents in locality names.

Try these experiments without changing the algorithm:

- Add an isolated locality and observe infinity and an empty path.
- Set `directed` to true and compare the two directions of the same trip.
- Remove a connection or change its cost and predict the new predecessor links.
- Make two routes equally cheap; compare distances rather than requiring a unique path.
- Add a zero-weight connection and trace strict relaxation.
- Supply an unknown endpoint, negative distance or duplicate road and inspect
  the simple validation conditional that rejects it.

## Costs

Write V for the number of vertices and E for the number of directed arcs.
TreeMap membership and single-arc updates take O(log V), assuming constant-time
comparisons. Reading neighbours costs O(log V + out-degree); removing a vertex
or computing its incoming degree takes O(V log V).

With sorted distance maps and expected constant-time HashSet marking, the linear
version takes O(V² + E log V) time and O(V) auxiliary storage. The heap version
on this simple graph takes O((V + E) log(V + 1)) time and O(V + E) auxiliary
storage, including stale queue entries. Both validate all E arcs first. These
bounds describe this representation, not an indexed-array decrease-key heap.

## Corrections to the recovered example

The original TreeMap silently required comparable vertices although its type
parameter did not declare that requirement. It shared adjacency maps in copies,
returned modifiable internal views, failed to update edge counts when deleting
vertices, and did not validate weights or the search source. The heap variant
reprocessed outdated queue entries, and results were stored as mutable graph
state. Those issues are corrected here. Console printing belongs to `Main`,
not to the graph implementation. Code and comments use English, Allman style,
four spaces, simple validation conditions and Java 17.

## Verification

`bash run.sh test` checks graph mutations, copy isolation, read-only results,
invalid inputs, unreachable vertices, zero weights, stale heap entries and
overflow. Twenty seeded random directed graphs are checked against an independent
Floyd–Warshall distance table. Every returned route's arcs and summed cost are
verified. The JSON network is checked from every source with both algorithms.
The example is also copied to an isolated folder to verify its scripts and data
paths. Expected output below uses the unmodified supplied JSON.

## Expected output

```text
Road network: 29 localities, 80 directed arcs
Teaching data: illustrative distances in km, not measured road distances.

Adjacency lists (destination: km)
Arriondas -> [Cangas de Onís: 8] [Infiesto: 20] [Ribadesella: 19]
Astorga -> [León: 52] [Ponferrada: 62]
Avilés -> [Cudillero: 28] [Gijón: 28] [Oviedo: 30]
Bembibre -> [Ponferrada: 22] [Villablino: 61]
Campomanes -> [La Robla: 72] [Pajares: 25] [Pola de Lena: 8]
Cangas de Onís -> [Arriondas: 8] [Riaño: 68]
Cangas del Narcea -> [Tineo: 31] [Villablino: 63]
Cistierna -> [León: 60] [Riaño: 35]
Cudillero -> [Avilés: 28] [Pravia: 15]
Gijón -> [Avilés: 28] [Langreo: 33] [Oviedo: 30] [Villaviciosa: 27]
Grado -> [Oviedo: 26] [Pravia: 29] [Salas: 23]
Infiesto -> [Arriondas: 20] [Oviedo: 46] [Villaviciosa: 30]
La Robla -> [Campomanes: 72] [León: 26] [Riaño: 80] [Villablino: 72] [Villamanín: 25]
Langreo -> [Gijón: 33] [Mieres: 20] [Oviedo: 26]
León -> [Astorga: 52] [Cistierna: 60] [La Robla: 26]
Llanes -> [Ribadesella: 29]
Mieres -> [Langreo: 20] [Oviedo: 20] [Pola de Lena: 15]
Oviedo -> [Avilés: 30] [Gijón: 30] [Grado: 26] [Infiesto: 46] [Langreo: 26] [Mieres: 20]
Pajares -> [Campomanes: 25] [Villamanín: 21]
Pola de Lena -> [Campomanes: 8] [Mieres: 15]
Ponferrada -> [Astorga: 62] [Bembibre: 22] [Villablino: 65]
Pravia -> [Cudillero: 15] [Grado: 29]
Riaño -> [Cangas de Onís: 68] [Cistierna: 35] [La Robla: 80]
Ribadesella -> [Arriondas: 19] [Llanes: 29] [Villaviciosa: 46]
Salas -> [Grado: 23] [Tineo: 23]
Tineo -> [Cangas del Narcea: 31] [Salas: 23]
Villablino -> [Bembibre: 61] [Cangas del Narcea: 63] [La Robla: 72] [Ponferrada: 65]
Villamanín -> [La Robla: 25] [Pajares: 21]
Villaviciosa -> [Gijón: 27] [Infiesto: 30] [Ribadesella: 46]

Distances from Gijón (km)
Arriondas: 77
Astorga: 222
Avilés: 28
Bembibre: 257
Campomanes: 73
Cangas de Onís: 85
Cangas del Narcea: 133
Cistierna: 188
Cudillero: 56
Gijón: 0
Grado: 56
Infiesto: 57
La Robla: 144
Langreo: 33
León: 170
Llanes: 102
Mieres: 50
Oviedo: 30
Pajares: 98
Pola de Lena: 65
Ponferrada: 261
Pravia: 71
Riaño: 153
Ribadesella: 73
Salas: 79
Tineo: 102
Villablino: 196
Villamanín: 119
Villaviciosa: 27

Linear scan path: [Gijón, Oviedo, Mieres, Pola de Lena, Campomanes, Pajares, Villamanín, La Robla, León]
Heap path:        [Gijón, Oviedo, Mieres, Pola de Lena, Campomanes, Pajares, Villamanín, La Robla, León]
Total distance: 170 km
Both algorithms give the same distances.
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

For another route, pass the JSON file, source and target after `run`:

```powershell
.\run.cmd run data/asturias-leon.json "Cudillero" "Cangas de Onís"
```
