package com.app.mvc.dataBaseDomainModelDao;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class JpaQueryProvider {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Object[]> getMetaInfo(List<Long> substance, List<Long> state, List<Long> quantity) {
        String queryHead = "SELECT " +
                "chs.chemical_formula " +
                ",st.phase_name " +
                ",string_agg(DISTINCT cast(pq1.id as TEXT) ||' '|| pq1.quantity_designation, ',') as constsQtys ";
        String queryBody =
                "FROM ont.pure_chemical_substance chs " +
                        "JOIN ont.substances_in_states sis " +
                        "ON chs.id = sis.substance_id " +
                        "JOIN ont.states st " +
                        "ON st.id = sis.state_id " +
                        "JOIN ont.quantity_state pqs1 " +
                        "ON st.id = pqs1.state_id " +
                        "JOIN ont.physical_quantities pq1 " +
                        "ON (pq1.id = pqs1.func_quantity_id or pq1.id = pqs1.func_argument_id) " +
                        "WHERE chs.id in ?1 " +
                        "AND st.id in ?2 ";
        String queryAdd = "AND pq1.id in ?3 ";
        String queryEnd = "group by chs.chemical_formula, st.phase_name";
        String query = "";
        if (quantity != null) {
            query = queryHead + queryBody + queryAdd + queryEnd;
            System.out.println(query);
            return (List<Object[]>) entityManager.createNativeQuery(query)
                    .setParameter(1, substance)
                    .setParameter(2, state)
                    .setParameter(3, quantity)
                    .getResultList();
        }
        else {
            query = queryHead + queryBody + queryEnd;
            System.out.println(query);
            return (List<Object[]>) entityManager.createNativeQuery(query)
                    .setParameter(1, substance)
                    .setParameter(2, state)
                    .getResultList();
        }
    }

    public String getDimensions(int id) {
        String query = "select " +
                "string_agg(DISTINCT d.dimension_name, ',') " +
                "from ont.physical_quantities pq " +
                "join ont.points_of_measure pm " +
                "on pq.id = pm.quantity_id " +
                "join ont.dimensions d " +
                "on d.id = pm.dimension_id " +
                "where pq.id = ?1";
        return (String) entityManager.createNativeQuery(query)
                .setParameter(1, id)
                .getSingleResult();
    }
}
