package com.app.mvc.controllers;

import com.app.mvc.applicationServices.StartupPageServices;
import com.app.mvc.controllers.FormClasses.MetaInfo;
import com.app.mvc.dataBaseDomainModel.ChemicalSubstance;
import com.app.mvc.dataBaseDomainModel.PhysicalQuantity;
import com.app.mvc.dataBaseDomainModel.State;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class StatupController {

	@Autowired
	private StartupPageServices startupPageServices;

	@RequestMapping(method = {RequestMethod.GET,RequestMethod.HEAD})
	public String printWelcome(ModelMap model) throws IOException, JsonGenerationException, JsonMappingException{

		List<ChemicalSubstance> chemicalSubstances = startupPageServices.getAllSubstances();
		List<State> states = startupPageServices.getAllStates();
		List<PhysicalQuantity> quantities = startupPageServices.getAllProperties();

        String chemicalSubstancesJson = startupPageServices.getDataAsString(chemicalSubstances);
        String statesJson = startupPageServices.getDataAsString(states);
        String quantitiesJson = startupPageServices.getDataAsString(quantities);

        model.addAttribute("dataMetaInfo", new MetaInfo());
        model.addAttribute("chemSubstList", chemicalSubstancesJson);
		model.addAttribute("statesList", statesJson);
		model.addAttribute("quantitiesList", quantitiesJson);

        model.addAttribute("message", "AVAILABLE DATA");

		return "StartupPage";
	}
}