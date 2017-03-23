package com.app.mvc.dataBaseDomainModelDao;


import java.io.Serializable;
import java.util.List;

public interface GenericDao<E extends Serializable>
{
    void save(final E e);

    void update(final E e);

    void delete(final E e);

    void deleteByPk(final long id);

    List<E> findAll();

    E findByPk(final long id);

    void SetDaoClass(Class<E> daoClass);
}
