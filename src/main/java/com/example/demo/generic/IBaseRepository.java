package com.example.demo.generic;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface IBaseRepository<T, ID extends Serializable> {
    List<T> getAll();
    List<T> getList(Predicate<T> predicate);
    Optional<T> get(Predicate<T> predicate);
    T save(T entity);
    void saveRange(List<T> entities);
    T update(T entity);
    void remove(Predicate<T> predicate);
    void removeAll(Predicate<T> predicate);
    Stream<T> createQuery();
}
