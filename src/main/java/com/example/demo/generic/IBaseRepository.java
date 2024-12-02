package com.example.demo.generic;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T, UUID> {
    List<T> getAll();
    Optional<T> getById(UUID id);
    T save(T entity);
    T update(T entity);
    void delete(UUID id);
}
