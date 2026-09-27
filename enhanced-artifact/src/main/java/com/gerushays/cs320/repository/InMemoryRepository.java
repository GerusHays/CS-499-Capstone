package com.gerushays.cs320.repository;

import com.gerushays.cs320.exception.DuplicateIdException;
import com.gerushays.cs320.exception.RecordNotFoundException;
import com.gerushays.cs320.model.Identifiable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<T extends Identifiable> implements Repository<T> {
    private final Map<String, T> items = new HashMap<>();

    @Override
    public void add(T item) {
        if (item == null) throw new IllegalArgumentException("Item cannot be null.");
        if (items.containsKey(item.getId())) throw new DuplicateIdException(item.getId());
        items.put(item.getId(), item);
    }

    @Override
    public Optional<T> findById(String id) {
        validateId(id);
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public T requireById(String id) {
        return findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    @Override
    public void deleteById(String id) {
        T existing = requireById(id);
        items.remove(existing.getId());
    }

    @Override
    public List<T> findAll() {
        return List.copyOf(new ArrayList<>(items.values()));
    }

    @Override
    public int size() {
        return items.size();
    }

    private static void validateId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID cannot be null or blank.");
    }
}
