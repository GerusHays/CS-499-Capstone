# Database Enhancement

This enhancement replaces the original memory-only storage option with a persistent SQLite database implementation while keeping the repository abstraction from the previous enhancement.

## What changed
- Added a normalized SQLite schema for contacts, tasks, and appointments.
- Added primary keys, NOT NULL constraints, length checks, and indexes.
- Added `DatabaseManager` for connection handling and schema initialization.
- Added JDBC repositories for all three model types.
- Used prepared statements for values instead of building SQL with user input.
- Added `update` to the repository contract so service updates are persisted instead of only changing an in-memory object.
- Kept the existing in-memory repository so the same service layer can be used with either storage implementation.

## Running with SQLite
The Maven file includes the Xerial SQLite JDBC driver. Run `mvn compile` to resolve the driver. Create a `DatabaseManager`, then inject a SQLite repository into the existing service, for example:

```java
DatabaseManager db = new DatabaseManager("cs320.db");
ContactService contacts = new ContactService(new SqliteContactRepository(db));
```

The repository initializes the schema automatically. `src/main/resources/schema.sql` is also included so the schema can be reviewed independently.

## Security and reliability
Prepared statements are used for CRUD values, database constraints provide a second layer of validation, resources are closed with try-with-resources, and repository exceptions prevent silent failures when records are missing or duplicated.
