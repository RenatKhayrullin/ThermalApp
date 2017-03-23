package com.app.mvc.dataBaseDomainModelDao.AbstractDaoImpl;

import com.app.mvc.dataBaseDomainModelDao.GenericDao;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericDaoImpl<E extends Serializable> extends AbstractJpaDao<E> implements GenericDao<E> {
    //
}
