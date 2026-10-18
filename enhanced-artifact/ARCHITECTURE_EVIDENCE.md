# Software Engineering Enhancement — Design and Verification

`ContactService`, `TaskService`, and `AppointmentService` receive a `Repository<T>` through their constructors. This is **constructor dependency injection**: the services know the repository contract, not the concrete storage mechanism. For example, `new ContactService(new InMemoryRepository<>())` can be replaced with `new ContactService(new SqliteContactRepository(db))`. The service methods do not need to be rewritten to switch storage backends.

The abstraction improves testability (tests can use isolated in-memory repositories), maintainability (storage changes stay in repository classes), and separation of concerns (validation and business operations remain in services/models). Shared validation, defensive copying for mutable appointment dates, and immutable result lists reduce accidental changes to stored data.

## Regression evidence

The algorithms test runner exercises contact, task, and appointment service behavior and reported 23 passed / 0 failed in the earlier audit. This verifies selected operations after refactoring, **not** every original CS-320 test. The original tests remain under `original-artifact/CS-320/` for comparison. For final submission, capture the runner output and add any missing regression cases you identify.

## Course outcome evidence

- Architecture and engineering: generic `Repository<T>`, `InMemoryRepository`, and service constructor injection.
- Algorithmic reasoning: case-insensitive searching, sorting, filtering, and complexity discussion in `ALGORITHM_ANALYSIS.md`.
- Secure database design: parameterized SQL, constraints, and explicit resource closure in the SQLite repositories.
- Quality assurance: standalone algorithms and SQLite integration runners and their reproducible execution instructions in `TESTING.md`.
