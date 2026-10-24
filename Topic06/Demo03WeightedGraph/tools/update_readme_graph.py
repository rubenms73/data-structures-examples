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
        ('Eastern Asturias and eastern León', list(range(24, 34)) + [40]),
        ('Castile and the connection to Madrid', list(range(41, 47)) + list(range(55, 59))),
        ('Cantabrian coast', list(range(47, 50))),
        ('Galicia', list(range(50, 55))),
        ('Direct Oviedo–León alternatives', [59]),
    ]
    ids = {name: 'v' + str(i) for i, name in enumerate(data['vertices'])}
    sections = [START, '## Graph of the bundled road network', '',
        'The diagrams below show **all 42 localities and all 120 directed road',
        'alternatives** in `data/northern-spain.json`. They are schematic graphs, not',
        'geographic maps: the shape and position of the lines say nothing about the roads',
        'themselves. A locality that appears in more than one panel is the same vertex of',
        'the single, complete graph.', '',
        '**Legend:** each node is the reference point of a locality. To keep the diagrams',
        'readable, each double-headed connection stands for **two separate directed',
        'arcs**. Its label uses short IDs: `01` means `road-01` and `01R` means',
        '`road-01-reverse`, while `PS` and `PN` mean `pajares-south` and `pajares-north`.',
        'Each ID is followed by the physical distance of that arc in kilometres, rounded',
        'to three decimals. The order of the IDs in a label does not indicate a direction',
        'on the page; look each ID up in the table to find its direction, road references',
        'and incident tags. The two arcs of a pair can differ in both distance and road',
        'references.', '',
        'With no incidents, **weight = kilometres**. During an incident, **weight =',
        'kilometres + penalty**, whereas a closure removes the arc altogether. The',
        'diagrams show the original network, with every penalty at zero. Penalties are',
        'cost units for teaching purposes, not minutes, and the distances come from a',
        'saved snapshot of the map, not from live measurements.', '',
        'An arc may follow several roads and pass through other places that are not',
        'vertices of the graph. Where two lines cross there is no junction unless a',
        'labelled node is drawn there. The parallel connections between Oviedo and León',
        'are separate route alternatives. To see the actual shape of the roads and the',
        'five incident scenarios, use the offline HTML viewer described below.', '']
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
    sections += ['The diagrams and tables are generated from the JSON file, so every ID and',
                 'distance can be traced back to it. After changing that file, run',
                 '`python tools/update_readme_graph.py` from this demo folder to update them.', END]
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
