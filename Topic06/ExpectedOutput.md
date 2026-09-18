## Demo01WeightedGraph

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
