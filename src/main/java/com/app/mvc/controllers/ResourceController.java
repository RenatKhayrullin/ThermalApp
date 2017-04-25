package com.app.mvc.controllers;

import com.app.mvc.applicationServices.ResourceService;
import com.app.mvc.dataBaseDomainModel.ResourceColumns;
import com.app.mvc.dataBaseDomainModel.ThirdPartyResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Катерина on 19.04.2017.
 */

@Controller
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/resources", method = RequestMethod.GET)
    public String getResorces(Model model) {

        List<ThirdPartyResource> thirdPartyResources = resourceService.getAllResources();

        model.addAttribute("resources", thirdPartyResources);

        return "resources/resourcePage";
    }

    @RequestMapping(value = "/resources/add", method = RequestMethod.GET)
    public String addResource(Model model) {

        model.addAttribute("resourceAttribute", new ThirdPartyResource());
        return "resources/addResource";
    }

    @RequestMapping(value = "/resources/added", method = RequestMethod.POST)
    public String addedResource(@ModelAttribute("resourceAttribute")
                                @RequestParam String url, @RequestParam String apikey,
                                @RequestParam String acceptData, @RequestParam String acceptCharset,
                                @RequestParam String resourceName, @RequestParam String additional,
                                Model model) {

        ThirdPartyResource resource = new ThirdPartyResource();

        resource.setUrl(url);
        resource.setApikey(apikey);
        resource.setAcceptData(acceptData);
        resource.setAcceptCharset(acceptCharset);
        resource.setResourceName(resourceName);
        resource.setAdditional(additional);

        resourceService.saveResource(resource);

        List<ThirdPartyResource> thirdPartyResources = resourceService.getAllResources();
        model.addAttribute("resources", thirdPartyResources);

        return "resources/resourcePage";
    }

    @RequestMapping(value = "/resources/columns", method = RequestMethod.GET)
    public String getResourceColumns(@RequestParam(value="id", required=true) Long id,
                                  Model model) {

        ThirdPartyResource existingResource = resourceService.getResourceById(id);
        List<ResourceColumns> columns = resourceService.getResourceColumns(existingResource.getId());

        model.addAttribute("resource", existingResource);
        model.addAttribute("columns", columns);

        return "resources/resourceColumns";
    }

    @RequestMapping(value = "/resources/addColumn", method = RequestMethod.GET)
    public String addColumns(@RequestParam(value="id", required=true) Long id, Model model) {

        ThirdPartyResource resource = resourceService.getResourceById(id);

        model.addAttribute("resource", resource);
        model.addAttribute("columnAttribute", new ResourceColumns());

        return "resources/addResourceColumns";
    }

    @RequestMapping(value = "/resources/addedColumns", method = RequestMethod.POST)
    public String addedColumns(@ModelAttribute("columnAttribute")
                               @RequestParam(value="id", required=true) Long id,
                               @RequestParam String columnName,
                               @RequestParam String comment, Model model) {

        ResourceColumns column = new ResourceColumns();
        ThirdPartyResource resource = resourceService.getResourceById(id);

        column.setColumnName(columnName);
        column.setColumnComment(comment);
        column.setResourceNumber(resource);

        resourceService.saveColumn(column);

        List<ThirdPartyResource> thirdPartyResources = resourceService.getAllResources();
        model.addAttribute("resources", thirdPartyResources);
        //model.addAttribute("resource", resource);

        return "resources/resourcePage";
    }
}
