#!/usr/bin/env python3
"""Regenerate the README's complete regional graph diagrams from the bundled JSON.

Python is only needed by maintainers regenerating this documentation, not by the
Java demo. Every directed road must occur exactly once in the regional panels.
"""
from pathlib import Path
import json
from decimal import Decimal, ROUND_HALF_UP

ROOT = Path(__file__).resolve().parents[1]
START = '<!-- BEGIN GENERATED ROAD GRAPH -->'
END = '<!-- END GENERATED ROAD GRAPH -->'


def kilometres(value):
    return str(Decimal(str(value)).quantize(Decimal("0.001"), rounding=ROUND_HALF_UP))


def generate():
    data = json.loads((ROOT / 'data/northern-spain.json').read_text(encoding='utf-8'))
    roads = data['roads']
    groups = [
        ('Central coast and western Asturias', list(range(1, 11))),
        ('Western mountains and El Bierzo', list(range(11, 14)) + list(range(34, 40))),
        ('Central Asturias and the mountain passes', list(range(14, 24))),
        ('Eastern Asturias and eastern Leon', list(range(24, 34)) + [40]),
        ('Castile and the connection to Madrid', list(range(41, 47)) + list(range(55, 59))),
        ('Cantabrian coast', list(range(47, 50))),
        ('Galicia', list(range(50, 55))),
        ('Direct Oviedo-Leon alternatives', [59]),
    ]
    ids = {name: 'v' + str(i) for i, name in enumerate(data['vertices'])}
    sections = [START, '## Graph of the bundled road network', '',
        'The following diagrams cover **all 42 localities and all 120 directed road',
        'alternatives** in `data/northern-spain.json`. They are schematic graphs,',
        'not geographic maps: line shapes and positions do not represent road geometry.',
        'Localities shared by panels refer to the same vertex in the one complete graph.', '',
        '**Legend:** a node is a locality reference point. For readability, each',
        'double-headed connection groups **two separate directed arcs**. Its label',
        'uses compact IDs: `01` means `road-01`, `01R` means `road-01-reverse`.',
        '`PS` and `PN` mean `pajares-south` and `pajares-north`. Each ID is followed',
        'by its own physical distance in kilometres, rounded to three decimals.',
        'Look up the ID in the table for its exact direction, road references and',
        'incident tags; label order is not a left-to-right or top-to-bottom direction.',
        'The paired arcs can differ in both distance and road references.', '',
        'At baseline, **weight = kilometres**. With an incident, **weight = kilometres',
        '+ penalty**; a closure removes the arc instead. These diagrams show the original',
        'network with zero penalties. Penalties are teaching cost units, not minutes.',
        'Distances are the saved map-derived snapshot, not live measurements.', '',
        'An arc may cover several roads and pass through other places without a graph',
        'vertex there. A line crossing is not a junction unless there is a labelled node.',
        'Parallel connections between Oviedo and Leon represent distinct route alternatives.',
        'For geographic road shapes and the five incident scenarios, use the offline',
        'HTML viewer described below.', '']
    covered = []
    for title, numbers in groups:
        selected = [r for r in roads if any(r['id'] in (f'road-{n:02}', f'road-{n:02}-reverse') for n in numbers)
                    or (59 in numbers and r['id'].startswith('pajares-'))]
        names = {r[k] for r in selected for k in ('from', 'to')}
        sections += ['### ' + title, '', '```mermaid', 'flowchart TD']
        for name in data['vertices']:
            if name in names:
                sections.append(f'    {ids[name]}["{name}"]')
        for i in range(0, len(selected), 2):
            forward, reverse = selected[i:i + 2]
            assert (forward['from'], forward['to']) == (reverse['to'], reverse['from'])
            def compact(road):
                return road['id'].replace('road-', '').replace('-reverse', 'R').replace('pajares-south', 'PS').replace('pajares-north', 'PN')
            label = f"{compact(forward)}: {kilometres(forward['km'])}; {compact(reverse)}: {kilometres(reverse['km'])} km"
            sections.append(f'    {ids[forward["from"]]} <-->|"{label}"| {ids[forward["to"]]}')
            covered.extend([forward['id'], reverse['id']])
        sections += ['```', '', '| Arc ID | Direction | Road references | Distance (km) | Incident tags |',
                     '| --- | --- | --- | ---: | --- |']
        for r in selected:
            tags = ', '.join(r.get('corridors', [])) or 'None'
            sections.append(f'| `{r["id"]}` | {r["from"]} → {r["to"]} | {r["name"]} | {kilometres(r["km"])} | {tags} |')
        sections.append('')
    assert len(covered) == len(set(covered)) == len(roads), 'Missing or duplicated roads'
    assert set(covered) == {r['id'] for r in roads}
    assert {r[k] for r in roads for k in ('from', 'to')} == set(data['vertices'])
    sections += ['The diagrams and tables are generated from the JSON, so their IDs and',
                 'distances remain traceable to the input. After changing that file, maintainers',
                 'can run `python tools/update_readme_graph.py` from this demo folder.', END]
    return '\n'.join(sections)


def main():
    path = ROOT / 'README.md'
    text = path.read_text(encoding='utf-8')
    block = generate()
    if START in text:
        before, rest = text.split(START, 1)
        _, after = rest.split(END, 1)
        text = before + block + after
    else:
        marker = '## Run the demonstration'
        assert marker in text
        text = text.replace(marker, block + '\n\n' + marker, 1)
    path.write_text(text, encoding='utf-8')


if __name__ == '__main__':
    main()
