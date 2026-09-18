#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
export PYTHONDONTWRITEBYTECODE=1
case "${1:-run}" in
    run) python3 main.py ;;
    test) python3 -m unittest -v test_stack ;;
    *) echo 'Use: bash run.sh [run|test]' >&2; exit 2 ;;
esac
