package com.app.mvc.dataBaseDomainModelDao.AbstractDaoImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

public abstract class AbstractJpaDao<E extends Serializable>
{
    private Class<E> entityClass;

    AbstractJpaDao() {}

    public void SetDaoClass (Class<E> classToSet)
    {
        this.entityClass = classToSet;
    }

    @PersistenceContext
    private EntityManager entityManager;

    public List<E> findAll() { return entityManager.createQuery("FROM "+entityClass.getName()).getResultList(); }

    public E findByPk(long id) { return entityManager.find(entityClass, id); }

    public void save(E e) { entityManager.persist(e); }

    public void update(E e) { entityManager.merge(e); }

    public void delete(E e) { entityManager.remove(e); }

    public void deleteByPk(long id)
    {
        E e = findByPk(id);
        delete(e);
    }
}
