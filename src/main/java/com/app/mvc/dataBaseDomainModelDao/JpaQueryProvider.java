package com.app.mvc.dataBaseDomainModelDao;

import com.app.mvc.dataBaseDomainModel.ChemicalSubstance;
import com.app.mvc.dataBaseDomainModel.PhysicalQuantity;
import com.app.mvc.dataBaseDomainModel.State;
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
                "chs.substance_name " +
                ",st.phase_name " +
                ",string_agg(DISTINCT cast(pq1.id as TEXT) ||' '|| pq1.quantity_name, ',') as constsQtys ";
        String queryBody =
                "FROM ont.pure_chemical_substance chs " +
                        "JOIN ont.substance_in_state sis " +
                        "ON chs.id = sis.substance_id " +
                        "JOIN ont.state st " +
                        "ON st.id = sis.state_id " +
                        "JOIN ont.quantity_state pqs1 " +
                        "ON st.id = pqs1.state_id " +
                        "JOIN ont.physical_quantity pq1 " +
                        "ON (pq1.id = pqs1.func_quantity_id or pq1.id = pqs1.func_argument_id) " +
                        "WHERE chs.id in ?1 " +
                        "AND st.id in ?2 ";
        String queryAdd = "AND pq1.id in ?3 ";
        String queryEnd = "group by chs.substance_name, st.phase_name";
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

    public String getConstants(List<Long> substance) {
        String query = "SELECT " +
                "string_agg(DISTINCT cast(pq.id as TEXT) ||' '|| pq.quantity_name, ',') as conQtys " +
                "FROM ont.pure_chemical_substance chs " +
                "JOIN ont.substance_quantity sq " +
                "ON chs.id = sq.substance_id " +
                "JOIN ont.physical_quantity pq " +
                "ON pq.id = sq.quantity_id " +
                "WHERE chs.id in ?1";

        return (String) entityManager.createNativeQuery(query)
                .setParameter(1, substance)
                .getSingleResult();
    }

    public String getDimensions(int id) {
        String query = "select " +
                "string_agg(DISTINCT d.dimension_name, ',') " +
                "from ont.physical_quantity pq " +
                "join ont.point_of_measure pm " +
                "on pq.id = pm.quantity_id " +
                "join ont.dimension d " +
                "on d.id = pm.dimension_id " +
                "where pq.id = ?1";
        return (String) entityManager.createNativeQuery(query)
                .setParameter(1, id)
                .getSingleResult();
    }

    public List<Object[]> getNumericDataList(String substance, String state, String quantity, String dimension) {
        System.out.println(substance + "  " + state+ "  "+ quantity+ "  "+dimension);
        String query = "SELECT " +
                "ds.id as dsid, pm.row_num, " +
                "pcs.substance_name, s.phase_name, pq.quantity_designation, dm.dimension_designation, " +
                "dsc.id as dscid, pm.quantity_value, ut.id, mu.uncertainty_value " +
                "FROM ont.pure_chemical_substance pcs " +
                "JOIN ont.substance_in_state sis " +
                "ON sis.substance_id = pcs.id " +
                "JOIN ont.state s " +
                "ON sis.state_id = s.id " +
                "JOIN ont.data_set ds " +
                "ON sis.id = ds.substance_in_state_id " +
                "JOIN ont.point_of_measure pm " +
                "ON ds.id = pm.data_set_id " +
                "JOIN ont.physical_quantity pq " +
                "ON pm.quantity_id = pq.id " +
                "LEFT JOIN ont.dimension dm " +
                "ON dm.id = pm.dimension_id " +
                "JOIN ont.data_source dsc " +
                "ON dsc.id = pm.data_source_id " +
                "LEFT JOIN ont.measurement_uncertainty mu " +
                "ON pm.id = mu.point_of_measure_id " +
                "LEFT JOIN ont.uncertainty_type ut " +
                "ON mu.uncertainty_type_id = ut.id " +
                "WHERE pcs.substance_name like ?1 " +
                "AND s.phase_name like ?2 " +
                "AND pq.quantity_name like ?3 ";

        if (!dimension.equalsIgnoreCase("none")) {
            query += "AND dm.dimension_designation LIKE ?4 ";
            System.out.println(query);
            return (List<Object[]>) entityManager.createNativeQuery(query)
                    .setParameter(1, substance)
                    .setParameter(2, state)
                    .setParameter(3, quantity)
                    .setParameter(4, dimension)
                    .getResultList();
        }
        System.out.println(query);

        return (List<Object[]>) entityManager.createNativeQuery(query)
                .setParameter(1, substance)
                .setParameter(2, state)
                .setParameter(3, quantity)
                .getResultList();
    }


    public List<Object[]> getDataSources(ArrayList<Integer> requiredDataSources) {
        if (requiredDataSources.size() == 0) return new ArrayList<>();
        String query = "select dsc.id, dsc.bibliographic_reference " +
                "from ont.data_source dsc " +
                "where dsc.id in ?1";
        return (List<Object[]>) entityManager.createNativeQuery(query)
                .setParameter(1, requiredDataSources)
                .getResultList();
    }

    public List<Object[]> getUncertainties(ArrayList<Integer> requiredUncertainties) {
        if (requiredUncertainties.size() == 0) return new ArrayList<>();
        String query = "select ut.id, ut.uncertainty_name "+
                "from ont.uncertainty_type ut " +
                "where ut.id in ?1";
        return (List<Object[]>) entityManager.createNativeQuery(query)
                .setParameter(1, requiredUncertainties)
                .getResultList();
    }
}
