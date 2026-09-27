package com.gerushays.cs320.repository;

import com.gerushays.cs320.model.Identifiable;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends Identifiable> {
    void add(T item);
    Optional<T> findById(String id);
    T requireById(String id);
    void deleteById(String id);
    List<T> findAll();
    int size();
}
