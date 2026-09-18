#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
case "${1:-run}" in
    run) source=main.cpp ;;
    test) source=tests.cpp ;;
    *) echo 'Use: bash run.sh [run|test]' >&2; exit 2 ;;
esac
mkdir -p bin
"${CXX:-g++}" -std=c++17 -Wall -Wextra -Wpedantic -Werror "$source" -o bin/stack
./bin/stack
