package com.app.mvc.dataBaseDomainModelDao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaQueryProvider {

    @PersistenceContext
    private EntityManager entityManager;

}
