# CS-499 Computer Science Capstone

## Gerus Hays

Welcome to my Computer Science Capstone ePortfolio.

This repository documents the progressive enhancement of a Java service application originally developed in **CS-320: Software Testing, Automation, and Quality Assurance**. Rather than using unrelated artifacts for each portion of the capstone, I chose to continue improving the same application so that the repository shows how the software evolved over time.

The project began as three Java services for managing **Contacts, Tasks, and Appointments**. During CS-499, I expanded the original artifact across three major areas of computer science:

- Software Design and Engineering
- Algorithms and Data Structures
- Databases

The goal is not only to demonstrate the finished application, but also to show my ability to evaluate existing software, identify limitations, make design decisions, implement improvements, and verify those improvements through testing.

---

## ePortfolio

My CS-499 ePortfolio is published through GitHub Pages:

**https://gerushays.github.io/CS-499-Capstone/**

The site provides an overview of the project, my professional direction, and the progression of each capstone enhancement.

---

## Project Structure

```text
CS-499-Capstone/
│
├── docs/
│   └── index.html
│
├── enhanced-artifact/
│   ├── pom.xml
│   └── src/
│       ├── main/
│       │   └── java/com/gerushays/cs320/
│       └── test/
│           └── java/com/gerushays/cs320/
│
├── narratives/
│
├── original-artifact/
│   └── CS-320/
│
└── README.md
```

### `original-artifact`

Contains the original CS-320 project before the capstone enhancements.

### `enhanced-artifact`

Contains the enhanced version of the application. Each CS-499 milestone built on this version rather than creating an unrelated project.

### `docs`

Contains the GitHub Pages ePortfolio.

### `narratives`

Contains the written narratives explaining each enhancement and reflecting on the skills demonstrated.

---

# Enhancement One: Software Design and Engineering

The first enhancement focused on improving the architecture and maintainability of the original CS-320 project.

Major changes included:

- Reorganizing the application into a consistent package structure
- Introducing a reusable repository abstraction
- Implementing an in-memory repository
- Applying dependency injection between services and repositories
- Centralizing common validation logic
- Improving exception handling
- Reducing duplicated code
- Improving protection of mutable application data

The original application met the requirements of CS-320, but the three services contained similar logic and were largely designed as separate assignments. The enhancement reorganized them into a more cohesive application and created a stronger foundation for future enhancements.

---

# Enhancement Two: Algorithms and Data Structures

The second enhancement expanded the ways users can retrieve and organize records.

The application already used a `HashMap` for storing records by unique ID. I kept this structure because it remains appropriate when an exact ID is known rather than replacing it only for the sake of using a different data structure.

Additional algorithms were added for operations that require working across collections of records.

### Contacts

- Search by first or last name
- Case-insensitive partial matching
- Alphabetical sorting by last name and first name

### Tasks

- Search task names and descriptions
- Case-insensitive partial matching
- Alphabetical sorting by task name

### Appointments

- Chronological appointment sorting
- Filtering of upcoming appointments
- Chronological ordering of filtered results

The enhancement demonstrates the use of:

- HashMap-based lookup
- Lists
- Linear searching
- Filtering
- Java Comparators
- Collection sorting
- Algorithm and data-structure trade-offs

---

## Automated Testing

Testing was expanded alongside the algorithms and data structures enhancement.

The current enhancement tests cover:

- Contact searching
- Case-insensitive matching
- Contact sorting
- Task name searching
- Task description searching
- Task sorting
- Searches with no matches
- Empty collections
- Invalid search terms
- Appointment chronological sorting
- Upcoming appointment filtering

**Algorithms runner result: 23 tests passed, 0 failed** (verified in the prior audit; rerun locally using the instructions below).

---

# Enhancement Three: Databases

The third enhancement adds persistent SQLite storage through JDBC without replacing the existing service layer. `DatabaseManager` initializes three tables and indexes, and the `SqliteContactRepository`, `SqliteTaskRepository`, and `SqliteAppointmentRepository` classes implement the same `Repository<T>` interface as the in-memory repository.

Implemented features:
- Persistent create, read, update, and delete operations for contacts, tasks, and appointments
- Prepared statements and try-with-resources for parameterized SQL and connection cleanup
- Primary-key, NOT NULL, and length constraints, plus indexes on names and appointment dates
- Dependency injection so services can switch storage implementations without rewriting business logic
- A database integration test runner for persistence across connections, constraints, and index verification

See [`DATABASE_ENHANCEMENT.md`](enhanced-artifact/DATABASE_ENHANCEMENT.md) for test instructions and evidence requirements. The SQLite integration runner requires the Maven-managed JDBC driver; its result must be recorded after execution.

---

# Professional Direction

My professional experience has given me a background in complex systems, integration, testing, and real-world operations.

I am currently transitioning further into **software test automation**, where I can combine that experience with software development and automated testing. My longer-term goal is to continue building my software engineering experience and eventually move further into software engineering.

I see test automation as a strong bridge between those areas because it requires understanding both how a system is expected to behave and how to build software that can reliably verify that behavior.

---

## Technologies

- Java
- Python
- Git / GitHub
- Automated Testing
- Standalone Java test runners
- Object-Oriented Programming
- Algorithms and Data Structures
- Software Architecture
- Repository Pattern
- CI/CD Concepts
- Database Development

---

## Repository

This project is maintained as part of my **Southern New Hampshire University CS-499 Computer Science Capstone**.

The repository documents the three completed capstone enhancements. Final integration-test results should be added after running the SQLite test suite.
---

## Running the tests

From `enhanced-artifact/`, use `mvn test-compile dependency:build-classpath -Dmdep.outputFile=classpath.txt` to compile and resolve the SQLite driver. Run the standalone algorithms runner and database integration runner with the compiled classes and the dependency classpath. See [`TESTING.md`](enhanced-artifact/TESTING.md) for platform-specific commands and an evidence checklist. These runners are not JUnit tests; `mvn test` alone does not execute them.
