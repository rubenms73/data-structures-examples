"""Check the offline dataset against its saved routing evidence (Python 3)."""
import json
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
data = json.loads((ROOT / "data/northern-spain.json").read_text(encoding="utf-8"))
evidence = json.loads((ROOT / "data/route-evidence.json").read_text(encoding="utf-8"))
by_id = {item["id"]: item for item in evidence}
assert len(by_id) == len(evidence) == len(data["roads"])
assert len(set(data["vertices"])) == len(data["vertices"])
for road in data["roads"]:
    source = by_id[road["id"]]
    assert road["sourceUrl"] == source["sourceUrl"]
    assert road["km"] == source["distanceMetres"] / 1000
    assert road["km"] >= 0
    assert road["from"] in data["vertices"] and road["to"] in data["vertices"]
    assert len(source["responseSha256"]) == 64
    assert ("AP-66" in road["corridors"]) == ("AP-66" in road["name"].split(", "))
    assert ("N-630-Pajares" in road["corridors"]) == bool(source["corridorEvidence"])
    for point in source["corridorEvidence"]:
        lon, lat = point["location"]
        assert point["road"] == "N-630" and -5.79 <= lon <= -5.74 and 42.99 <= lat <= 43.005
    if road["id"].startswith("pajares-"):
        assert "N-630" in road["name"] and "AP-66" not in road["name"]
print(f"Verified {len(data['vertices'])} localities and {len(data['roads'])} sourced directed alternatives.")

# Decode display geometry and compare its endpoints with the original OSRM snaps.
# Geometry is never used to compute Dijkstra's weights.
def decode_polyline(encoded):
    position = 0
    latitude = longitude = 0
    points = []
    while position < len(encoded):
        changes = []
        for axis in range(2):
            value = shift = 0
            while True:
                assert position < len(encoded) and shift <= 30
                byte = ord(encoded[position]) - 63
                position += 1
                assert 0 <= byte <= 63
                value |= (byte & 31) << shift
                shift += 5
                if byte < 32:
                    break
            changes.append(~(value >> 1) if value & 1 else value >> 1)
        latitude += changes[0]
        longitude += changes[1]
        points.append([longitude / 100000, latitude / 100000])
    return points

for road in data["roads"]:
    points = decode_polyline(road["geometry"])
    source = by_id[road["id"]]
    assert len(points) >= 2
    for point in points:
        assert -180 <= point[0] <= 180 and -90 <= point[1] <= 90
    for point, waypoint in [(points[0], source["waypoints"][0]),
                            (points[-1], source["waypoints"][-1])]:
        assert all(abs(a - b) <= 0.00002 for a, b in zip(point, waypoint["location"]))
print("Verified all road geometries and their directed endpoints.")
