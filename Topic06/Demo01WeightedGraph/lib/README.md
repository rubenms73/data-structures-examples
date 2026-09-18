# Bundled JSON dependencies

These unmodified jars make the example runnable offline. They are supporting
infrastructure; the graph and Dijkstra classes have no third-party dependency.

- [Gson 2.13.2](https://github.com/google/gson/releases/tag/gson-parent-2.13.2),
  Apache License 2.0: see `LICENSE-gson.txt`.
  Official Maven Central artifact: `com.google.code.gson:gson:2.13.2`.
  [Gson user guide](https://github.com/google/gson/blob/gson-parent-2.13.2/UserGuide.md).
- [Error Prone annotations 2.41.0](https://github.com/google/error-prone/tree/v2.41.0),
  Apache License 2.0: see `LICENSE-error-prone.txt`.
  Official artifact: `com.google.errorprone:error_prone_annotations:2.41.0`.
  Gson declares this annotation dependency; including it also permits strict
  `-Xlint:all -Werror` compilation without missing-annotation warnings.

SHA-256:

```text
gson-2.13.2.jar
dd0ce1b55a3ed2080cb70f9c655850cda86c206862310009dcb5e5c95265a5e0
error_prone_annotations-2.41.0.jar
a56e782b5b50811ac204073a355a21d915a2107fce13ec711331ad036f660fcc
```
