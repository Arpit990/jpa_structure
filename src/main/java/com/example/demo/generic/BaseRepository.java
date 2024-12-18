package com.example.demo.generic;

import com.example.demo.data.GridResult;
import com.example.demo.data.GridSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public abstract class BaseRepository<T, UUID> implements IBaseRepository<T, UUID> {
    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    protected BaseRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    public Optional<T> getById(UUID id) {
        return Optional.ofNullable(entityManager.find(entityClass, id));
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void delete(UUID id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public GridResult getGridResult(GridSearch gridSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        // Add search filtering if provided (use searchColumn, searchType, and searchValue)
        if (gridSearch.getSearch() != null && !gridSearch.getSearch().trim().isEmpty()) {
            Predicate searchPredicate = buildSearchPredicate(root, gridSearch);
            criteriaQuery.where(searchPredicate);
        }

        // Get the total number of records (for recordsTotal)
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(countRoot));
        Long totalRecords = entityManager.createQuery(countQuery).getSingleResult();

        // Apply sorting if order and orderDir are provided
        if (gridSearch.getOrder() != null && gridSearch.getOrderDir() != null) {
            applySorting(criteriaQuery, criteriaBuilder, root, gridSearch);
        }

        // Apply pagination (set start and length)
        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(gridSearch.getStart());
        query.setMaxResults(gridSearch.getLength());

        // Get filtered data (for recordsFiltered)
        List<T> resultData = query.getResultList();
        int recordsFiltered = resultData.size(); // We get this size after applying filters.

        // Construct GridResult
        GridResult gridResult = new GridResult();
        gridResult.setData(resultData);
        gridResult.setDraw(gridSearch.getDraw());
        gridResult.setRecordsTotal(totalRecords.intValue());
        gridResult.setRecordsFiltered(recordsFiltered);
        gridResult.setQuery(gridSearch.getSearch()); // Include search query in the response
        gridResult.setAdditionalData(null); // Can be used for any other additional information

        return gridResult;
    }

    /**
     * This method builds the search predicate based on the search column, type, and value.
     *
     * @param root The root of the query
     * @param gridSearch The GridSearch object containing the search parameters
     * @return Predicate representing the search condition
     */
    private Predicate buildSearchPredicate(Root<T> root, GridSearch gridSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        String searchColumn = gridSearch.getSearchColumn();
        String searchValue = gridSearch.getSearchValue();

        if ("string".equalsIgnoreCase(gridSearch.getSearchType())) {
            return criteriaBuilder.like(criteriaBuilder.lower(root.get(searchColumn)), "%" + searchValue.toLowerCase() + "%");
        } else if ("number".equalsIgnoreCase(gridSearch.getSearchType())) {
            return criteriaBuilder.equal(root.get(searchColumn), searchValue);
        }
        // Add more search types as needed, like for dates or booleans
        return criteriaBuilder.conjunction(); // Default to no-op predicate if unknown type
    }

    /**
     * This method applies sorting based on the column and direction specified in GridSearch.
     *
     * @param criteriaQuery The criteria query
     * @param criteriaBuilder The criteria builder
     * @param root The root of the query
     * @param gridSearch The GridSearch object containing the sorting parameters
     */
    private void applySorting(CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root, GridSearch gridSearch) {
        String orderColumn = gridSearch.getOrder();
        String orderDir = gridSearch.getOrderDir();

        if (orderColumn != null && orderDir != null) {
            if ("asc".equalsIgnoreCase(orderDir)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderColumn)));
            } else if ("desc".equalsIgnoreCase(orderDir)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderColumn)));
            }
        }
    }
}
