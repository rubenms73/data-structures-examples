#!/usr/bin/env python3
"""Windows-specific quoting, failure propagation and topic-dispatch checks."""
from pathlib import Path
import os
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1]


def invoke(script, *arguments, cwd=None):
    return subprocess.run([str(script), *arguments], cwd=cwd, capture_output=True,
                          text=True, encoding="utf-8", timeout=120)


def main():
    if os.name != "nt":
        raise SystemExit("Run this check on Windows.")
    with tempfile.TemporaryDirectory(prefix="Windows runner checks ") as temporary:
        project = Path(temporary) / "example with spaces and áccents"
        shutil.copytree(ROOT / "Topic06/Demo03WeightedGraph", project,
                        ignore=shutil.ignore_patterns("bin", "*.class"))
        runner = project / "run.cmd"
        original = project / "data/northern-spain.json"
        network = project / "data/localidades de León.json"
        shutil.copyfile(original, network)
        result = invoke(runner, "run", str(network), "Cudillero", "Cangas de Onís",
                        cwd=temporary)
        assert result.returncode == 0, result.stderr
        assert "Cangas de Onís]" in result.stdout, result.stdout
        assert "Both algorithms give the same distances for every scenario." in result.stdout
        invalid_mode = invoke(runner, "unknown", cwd=temporary)
        assert invalid_mode.returncode == 2, invalid_mode
        invalid_source = invoke(runner, "run", str(network), "Unknown", "León",
                                cwd=temporary)
        assert invalid_source.returncode != 0, invalid_source
        # A failed compilation must never execute an old successful .class file.
        (project / "src/app/Main.java").write_text("this is not Java\n", encoding="utf-8")
        failed_compile = invoke(runner, cwd=temporary)
        assert failed_compile.returncode != 0, failed_compile
        assert "Network:" not in failed_compile.stdout

        for topic in sorted(ROOT.glob("Topic[0-9][0-9]")):
            names = (topic / "demos.txt").read_text(encoding="utf-8").splitlines()
            listing = invoke(topic / "run.cmd", "list", cwd=temporary)
            assert listing.returncode == 0, listing.stderr
            assert listing.stdout.splitlines() == names
            invalid = invoke(topic / "run.cmd", "unknown", cwd=temporary)
            assert invalid.returncode == 2, invalid
        topic = ROOT / "Topic05"
        for mode in ("all", "test", "Demo01ChainedHashSet"):
            selected = invoke(topic / "run.cmd", mode, cwd=temporary)
            assert selected.returncode == 0, selected.stderr
        # Verify that a child script's failure reaches the topic command.
        broken = Path(temporary) / "topic with a failing demo"
        shutil.copytree(topic, broken, ignore=shutil.ignore_patterns("bin", "*.class"))
        (broken / "Demo01ChainedHashSet/run.ps1").write_text("exit 7\n", encoding="ascii")
        assert invoke(broken / "run.cmd", "all", cwd=temporary).returncode == 7
        assert invoke(broken / "run.cmd", "test", cwd=temporary).returncode == 7
    print("Windows quoting, compilation/runtime errors and topic dispatch passed.")


if __name__ == "__main__":
    main()
