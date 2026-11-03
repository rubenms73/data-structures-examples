#!/usr/bin/env bash
set -euo pipefail
cd -- "$(dirname -- "$0")"
action="${1:-run}"
if [[ "$action" != "run" && "$action" != "test" ]]; then
    echo "Use: bash run.sh [run|test]" >&2
    exit 2
fi
mkdir -p bin
case "$(uname -s)" in
    MINGW*|MSYS*|CYGWIN*) runtime_cp='bin;lib/*' ;;
    *) runtime_cp='bin:lib/*' ;;
esac
# Quote paths in javac's argument file, including directory names with spaces.
find src -name '*.java' -print | LC_ALL=C sort | sed 's/.*/"&"/' > .sources.txt
trap 'rm -f .sources.txt' EXIT
javac -J-Dfile.encoding=UTF-8 -J-Dstdout.encoding=UTF-8 -J-Dstderr.encoding=UTF-8 -encoding UTF-8 -cp 'lib/*' -d bin @.sources.txt
if [[ "$action" == "test" ]]; then
    java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$runtime_cp" org.junit.runner.JUnitCore tests.ReadOnlySortedCollectionTest
else
    java -Dfile.encoding=UTF-8 -Dstdout.encoding=UTF-8 -Dstderr.encoding=UTF-8 -cp "$runtime_cp" app.App
fi
