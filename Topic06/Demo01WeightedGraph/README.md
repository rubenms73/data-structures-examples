# Directed weighted graph and Dijkstra

## Problem statement and prerequisites

Represent a directed graph by a map of adjacency maps and find shortest paths
with Dijkstra. Prerequisites: maps, sets, graph terminology and relaxation.
Weights must be finite and nonnegative; zero-weight edges and self-loops are
allowed. Null vertices are rejected; queries require existing vertices.

## Guided walkthrough

1. addEdge validates before inserting endpoints. Duplicate edges are unchanged.
2. The copy constructor copies every adjacency map, avoiding shared edge storage.
3. removeVertex removes outgoing and incoming edges; a self-loop is counted once.
4. Dijkstra repeatedly selects the reachable unvisited vertex of minimum distance.
5. Relax outgoing edges and record predecessors. Break when no reachable vertex remains.
6. Reconstruct a path backwards, then reverse into a new list. An unreachable target
   yields an empty path; start-to-start yields one vertex.

## Costs and contracts

The linear minimum selection keeps the algorithm explicit: O(V² + E) expected
with hash-based maps/sets. A search uses O(V) extra storage. Each query recomputes
results rather than caching stale state. Returned vertices and distances are
independent containers. Vertex objects are shared and must have stable equals
and hashCode. Floating-point arithmetic applies; a nonrepresentable candidate
path cost throws ArithmeticException. A heap-based version is an extension.

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
Distances: {A=0.0, B=3.0, C=1.0, D=Infinity}
Path to B: [A, C, B]
Path to D: []
Original/copy edges: 3/1
```
