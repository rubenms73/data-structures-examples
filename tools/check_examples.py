#!/usr/bin/env python3
"""Copy each example in isolation, run it and compare its documented output."""
from pathlib import Path
import os
import re
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1] / "Topic01"


def main():
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
