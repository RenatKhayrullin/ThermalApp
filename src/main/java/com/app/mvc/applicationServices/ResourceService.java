package com.app.mvc.applicationServices;

import com.app.mvc.dataBaseDomainModel.ResourceColumns;
import com.app.mvc.dataBaseDomainModel.ThirdPartyResource;

import java.util.List;

/**
 * Created by Катерина on 11.04.2017.
 */
public interface ResourceService {

    List getAllResources();
    List getResourceColumns(Long id);
    void saveResource(ThirdPartyResource resource);
    ThirdPartyResource getResourceById(Long id);
    ThirdPartyResource getResourceByName(String name);
    void saveColumn(ResourceColumns column);
}
