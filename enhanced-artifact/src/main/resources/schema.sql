PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS contacts (
    contact_id TEXT PRIMARY KEY NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone TEXT NOT NULL,
    address TEXT NOT NULL,
    CHECK(length(contact_id) <= 10),
    CHECK(length(first_name) <= 10),
    CHECK(length(last_name) <= 10),
    CHECK(length(phone) = 10),
    CHECK(length(address) <= 30)
);

CREATE TABLE IF NOT EXISTS tasks (
    task_id TEXT PRIMARY KEY NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    CHECK(length(task_id) <= 10),
    CHECK(length(name) <= 20),
    CHECK(length(description) <= 50)
);

CREATE TABLE IF NOT EXISTS appointments (
    appointment_id TEXT PRIMARY KEY NOT NULL,
    appointment_date INTEGER NOT NULL,
    description TEXT NOT NULL,
    CHECK(length(appointment_id) <= 10),
    CHECK(length(description) <= 50)
);

CREATE INDEX IF NOT EXISTS idx_contacts_name ON contacts(last_name, first_name);
CREATE INDEX IF NOT EXISTS idx_tasks_name ON tasks(name);
CREATE INDEX IF NOT EXISTS idx_appointments_date ON appointments(appointment_date);
