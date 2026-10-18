# Test execution and evidence

The project uses standalone Java test runners rather than JUnit. The SQLite runner needs the JDBC driver downloaded through Maven. From `enhanced-artifact/`:

```bash
mvn test-compile dependency:build-classpath -Dmdep.outputFile=classpath.txt
# Git Bash / Linux / macOS
CP="target/classes:target/test-classes:$(cat classpath.txt)"
java -cp "$CP" com.gerushays.cs320.service.AlgorithmsDataStructuresTest
java -cp "$CP" com.gerushays.cs320.database.SqliteIntegrationTest
```

For Windows PowerShell:

```powershell
mvn test-compile dependency:build-classpath '-Dmdep.outputFile=classpath.txt'
$cp = "target/classes;target/test-classes;$(Get-Content classpath.txt -Raw)".Trim()
java -cp $cp com.gerushays.cs320.service.AlgorithmsDataStructuresTest
java -cp $cp com.gerushays.cs320.database.SqliteIntegrationTest
```

**Evidence to retain:** terminal output with passed/failed counts; SQLite persistence checks after reconnecting to the same file; constraint rejections; index existence and query-plan results. Do not claim the SQLite tests passed until they have been executed in an environment with the JDBC driver. `mvn test` by itself does not run these standalone runners.
