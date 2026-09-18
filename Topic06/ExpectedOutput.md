# Topic 6: expected output

Run `bash run.sh` or `.\run.cmd` inside the corresponding demo folder.

## Demo01GraphTraversals

```text
Directed graph (outgoing neighbours):
A -> [B, D, E]
B -> [C, D]
C -> [A]
D -> []
E -> []
F -> [G]
G -> []
H -> []

BFS from A: [A, B, D, E, C]
Parents: {B=A, D=A, E=A, C=B}

DFS from A: [A, B, C, D, E]
Parents: {B=A, C=B, D=B, E=A}

BFS forest: [A, B, D, E, C, F, G, H]
Parents: {B=A, D=A, E=A, C=B, G=F}

DFS forest: [A, B, C, D, E, F, G, H]
Parents: {B=A, C=B, D=B, E=A, G=F}
Parent entries mean child=parent; roots have no entry.
BFS minimises the number of arcs from one source, not weighted cost.
```

## Demo02NetworkSpanningTree

```text
Campus network: fictional installation costs in hundreds of euros.
Available undirected links (fictional installation cost units):
  Server -- Library : 4
  Server -- Lab : 3
  Library -- Lab : 1
  Library -- Office : 2
  Lab -- Office : 4
  Lab -- Classroom : 5
  Office -- Classroom : 2
  Office -- Workshop : 6
  Classroom -- Workshop : 3
Installing every link: 30

Minimum spanning tree:
  Server -- Lab : 3
  Lab -- Library : 1
  Library -- Office : 2
  Office -- Classroom : 2
  Classroom -- Workshop : 3
Selected links: 5 for 6 nodes
Total cost: 11

Incident: Library -- Lab is unavailable.

Recomputed minimum spanning tree:
  Server -- Lab : 3
  Server -- Library : 4
  Library -- Office : 2
  Office -- Classroom : 2
  Classroom -- Workshop : 3
Selected links: 5 for 6 nodes
Total cost: 14

Link restored:
  Server -- Lab : 3
  Lab -- Library : 1
  Library -- Office : 2
  Office -- Classroom : 2
  Classroom -- Workshop : 3
Selected links: 5 for 6 nodes
Total cost: 11

A tree has no redundant route: one selected link failure disconnects it.
Recomputation assumes the other candidate links are available.
Minimum total installation cost is not minimum latency from the server.
```

## Demo03WeightedGraph

```text
Network: 42 localities, 120 directed road alternatives
Map-derived distances: OpenStreetMap / OSRM snapshot; no live traffic.
Cost = kilometres + teaching penalty (equivalent-distance units).
Parallel alternatives from Oviedo to León:
  road-59 | O-12, A-66, AP-66, N-120 | 122.762 km
  pajares-south | O-12, A-66, N-630, N-630A | 114.917 km

1. No incident (original weights)
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107

Incident: N-630-Pajares in both directions (4 represented connections)

2. Road closed (affected connections removed)
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | road-59 | O-12, A-66, AP-66, N-120 | 122.762 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 528.953 km; routing cost: 528.953

3. Road reopened (original weights restored)
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107

4. Heavy traffic (+1000 cost units per affected connection; roads remain usable)
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | road-59 | O-12, A-66, AP-66, N-120 | 122.762 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 528.953 km; routing cost: 528.953

5. Traffic cleared (penalties removed)
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107
Both algorithms give the same distances for every scenario.
Offline map: bin/navigation-map.html
```
