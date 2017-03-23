package com.app.mvc.applicationServices.ServiceImpl;

import com.app.mvc.applicationServices.StartupPageServices;
import com.app.mvc.dataBaseDomainModel.ChemicalSubstance;
import com.app.mvc.dataBaseDomainModel.PhysicalQuantity;
import com.app.mvc.dataBaseDomainModel.State;
import com.app.mvc.dataBaseDomainModelDao.GenericDao;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StartupPageServiceImpl implements StartupPageServices {

    private GenericDao<ChemicalSubstance> chemicalSubstanceDao;
    private GenericDao<State> stateDao;
    private GenericDao<PhysicalQuantity> physicalQuantityDao;


    @Autowired
    public void setChemicalSubstanceDao(GenericDao<ChemicalSubstance> chemicalSubstanceDao) {
        this.chemicalSubstanceDao = chemicalSubstanceDao;
        this.chemicalSubstanceDao.SetDaoClass(ChemicalSubstance.class);
    }

    @Autowired
    public void setStateDao(GenericDao<State> stateDao) {
        this.stateDao = stateDao;
        this.stateDao.SetDaoClass(State.class);
    }

    @Autowired
    public void setPhysicalQuantityDao(GenericDao<PhysicalQuantity> physicalQuantityDao) {
        this.physicalQuantityDao = physicalQuantityDao;
        this.physicalQuantityDao.SetDaoClass(PhysicalQuantity.class);
    }

    @Override
    public List getAllSubstances() {
        return chemicalSubstanceDao.findAll();
    }

    @Override
    public List getAllStates() {
        return stateDao.findAll();
    }

    @Override
    public List getAllProperties() {
        return physicalQuantityDao.findAll();
    }

    @Override
    public String getDataAsString(List data) throws IOException {
        ObjectMapper jsonDataMapper = new ObjectMapper();
        return jsonDataMapper.writeValueAsString(data);
    }
}
