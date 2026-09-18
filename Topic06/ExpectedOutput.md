# Topic 6: expected navigation output

Run `bash run.sh` or `.\run.cmd` inside `Demo01WeightedGraph`.

```text
Network: 42 localities, 120 directed road alternatives
Map-derived distances: OpenStreetMap / OSRM snapshot; no live traffic.
Cost = kilometres + teaching penalty (equivalent-distance units).
Parallel alternatives from Oviedo to León:
  road-59 | O-12, A-66, AP-66, N-120 | 122.762 km
  pajares-south | O-12, A-66, N-630, N-630A | 114.917 km

1. Original route
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107

Incident: N-630-Pajares in both directions (4 represented connections)

2. Affected connections removed
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | road-59 | O-12, A-66, AP-66, N-120 | 122.762 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 528.953 km; routing cost: 528.953

3. Connections restored
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107

4. Temporary penalty of 1000 per affected connection
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | road-59 | O-12, A-66, AP-66, N-120 | 122.762 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 528.953 km; routing cost: 528.953

5. Penalty removed
[Gijón, Oviedo, León, Palencia, Valladolid, Madrid]
  Gijón -> Oviedo | road-02 | GJ-81, A-8, A-66R, A-63, O-12, N-630 | 33.768 km | penalty 0.000
  Oviedo -> León | pajares-south | O-12, A-66, N-630, N-630A | 114.917 km | penalty 0.000
  León -> Palencia | road-44 | LE-20, LE-30, A-60, A-231, CL-615 | 133.424 km | penalty 0.000
  Palencia -> Valladolid | road-45 | P-11, A-67, A-62, VA-20 | 48.758 km | penalty 0.000
  Valladolid -> Madrid | road-55 | N-601, AP-6, A-6 | 190.241 km | penalty 0.000
Physical distance: 521.107 km; routing cost: 521.107
Both algorithms give the same distances for every scenario.
```
