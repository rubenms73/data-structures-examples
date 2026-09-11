#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
mkdir -p bin
find src tests -name '*.java' -print | LC_ALL=C sort > bin/sources.txt
javac --release 21 -encoding UTF-8 -Xlint:all -Werror -d bin @bin/sources.txt
choice="${1:-list}"
case "$choice" in
  list) cat demos.txt ;;
  all) while IFS= read -r name; do
         printf '\n--- %s ---\n' "$name"
         java -cp bin "ds.topic01.demos.$name"
       done < demos.txt ;;
  test) java -cp bin ds.topic01.tests.ExampleChecks ;;
  Demo*) java -cp bin "ds.topic01.demos.$choice" ;;
  *) printf 'Use: ./run.sh [list|all|test|Demo01Rational|another demo name]\n' >&2; exit 2 ;;
esac
