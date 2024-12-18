package com.example.demo.generic;

import com.example.demo.data.GridResult;
import com.example.demo.data.GridSearch;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<T, UUID> {
    List<T> getAll();
    Optional<T> getById(UUID id);
    T save(T entity);
    T update(T entity);
    void delete(UUID id);

    GridResult getGridResult(GridSearch gridSearch);
}
