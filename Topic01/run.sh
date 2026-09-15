#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"
choice="${1:-list}"
case "$choice" in
  list) cat demos.txt ;;
  all|test)
    while IFS= read -r name; do
      printf '\n--- %s ---\n' "$name"
      if [ "$choice" = test ]; then bash "$name/run.sh" test; else bash "$name/run.sh"; fi
    done < demos.txt ;;
  *)
    if grep -Fxq -- "$choice" demos.txt; then bash "$choice/run.sh";
    else printf 'Use: bash run.sh [list|all|test|Demo05Movies|another listed example]\n' >&2; exit 2; fi ;;
esac
