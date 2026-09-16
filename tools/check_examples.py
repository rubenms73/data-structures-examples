#!/usr/bin/env python3
"""Copy each example in isolation, run it and compare its documented output."""
from pathlib import Path
import os
import re
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1] / "Topic01"


def is_block_prefix(prefix):
    text = prefix.strip()
    return bool(
        re.search(r"\b(class|interface|enum|record)\b", text)
        or re.match(r"^(if|for|while|switch|catch|synchronized)\s*\(", text)
        or re.match(r"^(try|else|finally|do)(\s|$)", text)
        or "->" in text
        or (re.search(r"\)\s*(?:throws\s+[\w., <>?]+)?$", text)
            and not re.search(r"=\s*new\s+\w+\s*\[", text))
    )


def check_allman_style():
    violations = []
    for path in sorted(ROOT.rglob("*.java")):
        for number, line in enumerate(path.read_text().splitlines(), 1):
            stripped = line.rstrip()
            if stripped.endswith("{"):
                prefix = stripped[:stripped.rfind("{")]
                if is_block_prefix(prefix):
                    violations.append(f"{path.relative_to(ROOT.parent)}:{number}")
            if re.search(r"}\s*(else|catch|finally)\b", stripped):
                violations.append(f"{path.relative_to(ROOT.parent)}:{number}")
    assert not violations, "Allman style violations:\n" + "\n".join(violations)


def main():
    check_allman_style()
    names = (ROOT / "demos.txt").read_text().splitlines()
    combined = dict(re.findall(
        r"## (Demo\w+)\n\n```text\n(.*?)```",
        (ROOT / "ExpectedOutput.md").read_text(), re.S))
    total = 0
    env = dict(os.environ, CLASSPATH="")
    for name in names:
        source = ROOT / name
        expected = re.search(r"```text\n(.*?)```", (source / "README.md").read_text(), re.S)[1]
        assert expected == combined[name], f"Output documentation differs: {name}"
        with tempfile.TemporaryDirectory(prefix="standalone example ") as temporary:
            project = Path(temporary) / name
            shutil.copytree(source, project, ignore=shutil.ignore_patterns("bin", "*.class"))
            script = str(project / "run.sh")
            output = subprocess.check_output(["bash", script], cwd=temporary, env=env, text=True)
            assert output == expected, f"Unexpected output: {name}\n{output}"
            checks = subprocess.check_output(["bash", script, "test"], cwd=temporary, env=env, text=True)
            count = int(re.fullmatch(r"All (\d+) checks passed\.\n", checks)[1])
            total += count
            print(f"{name}: isolated run and {count} checks passed")
    print(f"All {len(names)} isolated examples passed; {total} behaviour checks.")


if __name__ == "__main__":
    main()
