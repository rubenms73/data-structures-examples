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
