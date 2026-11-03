# Practical exam solutions — 2026–2027

| Date | Exam | Class to implement |
| --- | --- | --- |
| 28 October 2026 | [Exam A](ExamA/ExamA.pdf) · [solution](ExamA/) | `UniqueLinkedCollection<E>` |
| 29 October 2026 | [Exam B](ExamB/ExamB.pdf) · [solution](ExamB/) | `ReadOnlySortedCollection<E>` |

Both exams cover practice sessions 1 to 5 and Topics 1 and 2.

## Run a solution

Open an exam folder in VS Code with JDK 17 or newer and the Extension Pack for
Java, or import it into Eclipse as an existing project. JUnit 4.13.2 and
Hamcrest are included in `lib/`.

Windows (Command Prompt or PowerShell), from the exam folder:

```powershell
.\run.cmd
.\run.cmd test
```

macOS or Linux:

```sh
bash run.sh
bash run.sh test
```

The first command runs `app.App`, which prints the expected and actual result of
each check; the second runs the JUnit tests.
