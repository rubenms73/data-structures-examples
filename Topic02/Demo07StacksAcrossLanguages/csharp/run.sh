#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
case "${1:-run}" in
    run) arguments=() ;;
    test) arguments=(test) ;;
    *) echo 'Use: bash run.sh [run|test]' >&2; exit 2 ;;
esac
if command -v dotnet >/dev/null 2>&1; then
    dotnet build --nologo --verbosity quiet
    dotnet bin/Debug/net8.0/StackExample.dll "${arguments[@]}"
else
    mkdir -p bin
    mcs -warn:4 -warnaserror+ -out:bin/StackExample.exe IStack.cs ArrayStack.cs Program.cs ExampleChecks.cs
    mono bin/StackExample.exe "${arguments[@]}"
fi
