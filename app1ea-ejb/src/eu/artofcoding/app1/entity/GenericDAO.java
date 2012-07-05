package eu.artofcoding.app1.entity;

import eu.artofcoding.app1.ejb.GenericDAORemote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 */
public abstract class GenericDAO<T extends Serializable> implements GenericDAORemote<T> {

    /**
     * Persistence context.
     */
    @PersistenceContext(unitName = "app1-PU")
    private EntityManager entityManager;

    /**
     * Class of entity.
     */
    private Class<T> entityClass;

    /**
     * Constructor.
     * @param entityClass
     */
    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Create an entity.
     *
     * @param entity
     * @return Boolean Was entity successfully persisted?
     */
    @Override
    public T create(T entity) throws Exception {
        if (null != entity) {
            try {
                entityManager.persist(entity);
            } catch (Exception e) {
                throw e;
            }
        }
        return entity;
    }

    /**
     * Update an entity.
     * @param entity
     */
    @Override
    public T update(T entity) {
        T _entity = entityManager.merge(entity);
        return _entity;
    }

    /**
     * Delete an entity.
     * @param entity
     */
    @Override
    public boolean delete(T entity) {
        boolean b = false;
        try {
            T _entity = entityManager.merge(entity);
            entityManager.remove(entity);
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /**
     * Get an entity by its id.
     * @param id
     */
    @Override
    public T findById(int id) {
        return entityManager.find(entityClass, id);
    }

    /**
     * Find all entities.
     * @return List with all entities.
     */
    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
        criteriaQuery.select(criteriaQuery.from(entityClass));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Find a single entity by a parameterized named query.
     * @param namedQuery Name of named query, {@link EntityManager#createNamedQuery(String)}.
     * @param parameters Parameters.
     * @return Single result, see {@link javax.persistence.TypedQuery#getSingleResult()}.
     */
    public T findOne(String namedQuery, Map<String, Object> parameters) {
        T result = null;
        try {
            TypedQuery<T> query = entityManager.createNamedQuery(namedQuery, entityClass);
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
            result = query.getSingleResult();
        } catch (Exception e) {
            System.out.println(this + ".findOne: " + e.getMessage());
        }
        return result;
    }

    /**
     * Convenience method to find one entity by named query but wihtout parameter.
     * @param namedQuery Name of named query, {@link EntityManager#createNamedQuery(String)}.
     * @return Single result, see {@link javax.persistence.TypedQuery#getSingleResult()}.
     */
    public T findOne(String namedQuery) {
        return findOne(namedQuery, null);
    }

    /**
     * Method that will populate parameters if they are passed not null and empty.
     * @param query
     * @param parameters
     */
    protected void populateQueryParameters(Query query, Map<String, Object> parameters) {
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }

}
