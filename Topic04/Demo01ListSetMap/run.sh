#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
choice="${1:-run}"
case "$choice" in run|test) ;; *) printf 'Use: bash run.sh [run|test]\n' >&2; exit 2 ;; esac
mkdir -p bin
find bin -type f -name '*.class' -delete
find src -name '*.java' -print | LC_ALL=C sort > bin/sources.txt
if [ "$choice" = test ]; then
  find tests -name '*.java' -print | LC_ALL=C sort >> bin/sources.txt
fi
# JDK 17 is the classroom target. JAVA_RELEASE supports checks with other installed JDKs.
javac --release "${JAVA_RELEASE:-17}" -encoding UTF-8 -Xlint:all -Werror -d bin @bin/sources.txt
if [ "$choice" = test ]; then
  java -cp bin tests.ExampleChecks
else
  java -cp bin app.Main
fi
