package com.app.mvc.applicationServices.ServiceImpl;

import com.app.mvc.applicationServices.ResourceService;
import com.app.mvc.dataBaseDomainModel.ResourceColumns;
import com.app.mvc.dataBaseDomainModel.ThirdPartyResource;
import com.app.mvc.dataBaseDomainModelDao.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Катерина on 11.04.2017.
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    private GenericDao<ThirdPartyResource> thirdPartyResourceDao;
    private GenericDao<ResourceColumns> resourceColumnsDao;

    @Autowired
    public void setThirdPartyResourceDao(GenericDao<ThirdPartyResource> thirdPartyResourceDao) {
        this.thirdPartyResourceDao = thirdPartyResourceDao;
        this.thirdPartyResourceDao.SetDaoClass(ThirdPartyResource.class);
    }

    @Autowired
    public void setResourceColumnsDao(GenericDao<ResourceColumns> resourceColumnsDao) {
        this.resourceColumnsDao = resourceColumnsDao;
        this.resourceColumnsDao.SetDaoClass(ResourceColumns.class);
    }

    @Override
    public List getAllResources() {
        return thirdPartyResourceDao.findAll();
    }

    @Override
    public List getResourceColumns(Long id) {
        return resourceColumnsDao.findColumnsOfResource(id);
    }

    @Transactional
    public void saveResource(ThirdPartyResource resource) { thirdPartyResourceDao.save(resource); }

    @Override
    public ThirdPartyResource getResourceById(Long id) { return thirdPartyResourceDao.findByPk(id); }

    @Override
    public ThirdPartyResource getResourceByName(String name) { return thirdPartyResourceDao.findResourceByName(name); }

    @Transactional
    public void saveColumn(ResourceColumns column) { resourceColumnsDao.save(column); }

}
