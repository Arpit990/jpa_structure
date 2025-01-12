package com.example.demo.generic;

import org.jinq.jpa.JinqJPAStreamProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

@Repository
@Transactional
public abstract class BaseRepository<T, ID extends Serializable> implements IBaseRepository<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;
    private final JinqJPAStreamProvider jinqProvider;

    private final Class<T> entityClass;

    protected BaseRepository(JinqJPAStreamProvider jinqProvider, Class<T> entityClass) {
        this.jinqProvider = jinqProvider;
        this.entityClass = entityClass;
    }

    public List<T> getAll() {
        return jinqProvider.streamAll(entityManager, entityClass).toList();
    }

    public List<T> getList(Predicate<T> predicate) {
        return jinqProvider.streamAll(entityManager, entityClass).filter(predicate).toList();
    }

    public Optional<T> get(Predicate<T> predicate) {
        return jinqProvider.streamAll(entityManager, entityClass).filter(predicate).findFirst();
    }

    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public void saveRange(List<T> entities) {
        for (T entity : entities) {
            entityManager.persist(entity);
        }
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void remove(Predicate<T> predicate) {
        Optional<T> entityOptional = get(predicate);
        entityOptional.ifPresent(entity -> entityManager.remove(entity));
    }

    @Transactional
    public void removeAll(Predicate<T> predicate) {
        List<T> entitiesToRemove = getList(predicate);
        Optional.ofNullable(entitiesToRemove).filter(list -> !list.isEmpty())
                .ifPresent(entities -> entities.forEach(entityManager::remove));
    }

    // Additional Jinq-specific method for complex queries
    public Stream<T> createQuery() {
        return jinqProvider.streamAll(entityManager, entityClass);
    }
}
