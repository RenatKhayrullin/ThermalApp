package com.app.mvc.dataBaseDomainModelDao.AbstractDaoImpl;

import com.app.mvc.dataBaseDomainModelDao.GenericDao;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImpl<E extends Serializable> extends AbstractJpaDao<E> implements GenericDao<E> {
    //

    private Class<E> entityClass;

    GenericDaoImpl() {}

    @PersistenceContext
    private EntityManager entityManager;

    public List<E> findColumnsOfResource(Long id) {
        return entityManager.createQuery(
                "SELECT c FROM ResourceColumns c, ThirdPartyResource r WHERE " +
                        "c.resourceNumber = r AND " +
                        "r.id = :id")
                .setParameter("id", id)
                .getResultList();
    }

    public E findResourceByName(String name) {
        return (E) entityManager.createQuery(
                "SELECT r FROM ThirdPartyResource r WHERE r.resourceName LIKE :name"
        ).setParameter("name", name).getSingleResult();
    }
}
