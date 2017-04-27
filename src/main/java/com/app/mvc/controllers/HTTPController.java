package com.app.mvc.controllers;

import com.app.mvc.OntologyModelDao.JenaDAO;
import com.app.mvc.Utility.ChemSpiderInfo;
import com.app.mvc.Utility.ChemSpiderXMLHelper;
import com.app.mvc.Utility.XMLLoader;
import com.app.mvc.applicationServices.ResourceService;
import com.app.mvc.controllers.FormClasses.SearchRequest;
import com.app.mvc.dataBaseDomainModel.ResourceColumns;
import com.app.mvc.dataBaseDomainModel.ThirdPartyResource;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Катерина on 27.03.2017.
 */
@Controller
public class HTTPController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private JenaDAO jenaDAO;

    @RequestMapping(value = "/http", method = RequestMethod.GET)
    public String SearchForm(Model model) {
        return "http";
    }

    @RequestMapping(value="/searchResult", method= RequestMethod.POST)
    public String sendGet(Model uiModel, @ModelAttribute final SearchRequest searchRequest)
            throws IOException {

        System.out.println(searchRequest.toString());

        ThirdPartyResource resource = resourceService.getResourceByName(searchRequest.getResource());

        //List<ThirdPartyResource> thirdPartyResources = resourceService.getAllResources();

        //choosing chemspider - get(0)
        List<ResourceColumns> resourceColumns = resourceService.getResourceColumns(resource.getId());

        String url = resource.getUrl();
        //String url =

        if (resource.getResourceName().contains("chemspider")) {
            String entity = jenaDAO.getInstanceProperty(searchRequest.getEntity(), "hasChemicalFormula");
            if (entity.isEmpty()) {
                uiModel.addAttribute("msg", "This instance does not have Chemical Formula");
                return "ErrorPage";
            }
            url = url.replace("{entity}", transformFormulaForQuery(entity));
        }
        else url = url.replace("{entity}", searchRequest.getEntity().toLowerCase());

        if (url.contains("{apikey}")) {
            url = url.replace("{apikey}", resource.getApikey());
        }

        System.out.println(url);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.addRequestProperty("Accept", resource.getAcceptData());
        con.addRequestProperty("Accept-Charset", resource.getAcceptCharset());
        //con.addRequestProperty("Accept", "application/json");
        //con.addRequestProperty("Accept-Charset", "UTF-8");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        String contentType = con.getContentType();
        System.out.println(contentType);

        if ((responseCode != 200)) { //|| (! contentType.equalsIgnoreCase(resource.getAcceptData()))) {
            //System.out.println(resource.getAcceptData());
            uiModel.addAttribute("msg", "Nothing was found at this resource");
            return "ErrorPage";
        }

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

        String infos = "";
        if (con.getContentType().contains("xml")) {
            Document xml = XMLLoader.loadXmlFromString(response.toString().replaceAll("'", ""));
            List<ChemSpiderInfo> chem_infos = ChemSpiderXMLHelper.getInfoListFromAtomXML(xml);

            ObjectMapper jsonDataMapper = new ObjectMapper();
            infos = jsonDataMapper.writeValueAsString(chem_infos);
        }
        if (con.getContentType().contains("json")) {
            infos = response.toString().replaceAll("'", "");
        }

        ObjectMapper jsonDataMapper = new ObjectMapper();
        String columns = jsonDataMapper.writeValueAsString(resourceColumns);

        uiModel.addAttribute("infos", infos);
        uiModel.addAttribute("columns", resourceColumns);
        uiModel.addAttribute("jsoncolumns", columns);
        uiModel.addAttribute("tpresource", resource);
        uiModel.addAttribute("entity", searchRequest.getEntity());
        uiModel.addAttribute("resource", searchRequest.getResource());
        return "SearchResult";
    }

    public static String transformFormulaForQuery(String formula){
        formula = formula.toUpperCase();
        Pattern p = Pattern.compile("[a-zA-Z]+[0-9]+");
        Matcher m = p.matcher(formula);
        StringBuilder res = new StringBuilder();
        String str = "";
        while (m.find()){
            str = m.group();

            Pattern nums = Pattern.compile("[0-9]+");
            Matcher mnums = nums.matcher(str);
            if (mnums.find()){
                System.out.println("true");
                String numbers = mnums.group();
                String letters = str.substring(0, str.indexOf(numbers));
                String part = letters + "_" + "{" + numbers + "}";
                res.append(part);
            }
        }
        String tail = formula.substring(formula.indexOf(str) + str.length());
        res.append(tail);
        return res.toString();
    }
}

