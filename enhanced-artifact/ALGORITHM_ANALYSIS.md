# Algorithms and Data Structures — Complexity and Evidence

The in-memory repository uses a `HashMap` for exact ID lookup (expected O(1) average time; O(n) worst case under pathological collisions). This is appropriate because IDs are unique and direct lookup is common. The SQLite repositories instead use indexed primary-key queries; their performance is governed by the database query plan rather than HashMap complexity.

Let **n** be the number of records examined and **k** the number matching a filter. Contact and task substring searches traverse all records, so they take O(n) record comparisons, plus the cost of substring matching for the field lengths. The service returns up to k records, requiring O(k) result storage. Upcoming appointment filtering scans n records in O(n), then sorts k matches in O(k log k) worst-case time, for O(n + k log k) overall. Sorting all contacts, tasks, or appointments with Java's object `List.sort` is O(n log n) worst-case and requires O(n) additional space for the copied list (with additional temporary sorting space). Chronological ordering uses a date comparator and ID tie-breaker.

As the dataset doubles, a full linear scan requires approximately twice as many record examinations, while sorting generally increases by more than a factor of two. Keeping HashMap ID lookup avoids scanning for exact identifiers. These choices favor readable, maintainable code over premature indexing of a small dataset.

## Concrete algorithm test evidence

The standalone `AlgorithmsDataStructuresTest` runner reported **23 passed / 0 failed** in the repository audit. Representative asserted cases include: contact search for `ha` returns two matches; task search for `FLIGHT` returns two matches; upcoming appointments returns three matches with `A1` first. Rerun the test suite for final evidence; the code and assertion output are the authoritative record.

## Course outcomes

- **Outcome 3 (algorithms/data structures):** `ContactService.searchByName`, `TaskService` search methods, and the sorted/filtered appointment methods demonstrate collection traversal, comparators, and complexity trade-offs.
- **Software design:** The `Repository<T>` contract separates services from storage, allowing algorithms to operate against either implementation.
- **Testing and security:** Invalid-term and empty-result tests exercise edge cases, while unmodifiable results protect callers from mutating returned collections.
