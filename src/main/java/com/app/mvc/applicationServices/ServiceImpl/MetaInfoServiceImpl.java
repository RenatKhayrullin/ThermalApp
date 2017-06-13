package com.app.mvc.applicationServices.ServiceImpl;

import com.app.mvc.applicationServices.MetaInfoService;
import com.app.mvc.controllers.FormClasses.NumericData;
import com.app.mvc.dataBaseDomainModel.*;
import com.app.mvc.dataBaseDomainModelDao.GenericDao;
import com.app.mvc.dataBaseDomainModelDao.JpaQueryProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Катерина on 22.05.2017.
 */
@Service
public class MetaInfoServiceImpl implements MetaInfoService{

    private GenericDao<DataSource> dataSourceDao;
    private GenericDao<UncertaintyType> measurementUncertaintyDao;

    @Autowired
    public void setDataSourceDao(GenericDao<DataSource> dataSourceDao) {
        this.dataSourceDao = dataSourceDao;
        this.dataSourceDao.SetDaoClass(DataSource.class);
    }

    @Autowired
    public void setMeasurementUncertaintyDao(GenericDao<UncertaintyType> measurementUncertaintyDao) {
        this.measurementUncertaintyDao = measurementUncertaintyDao;
        this.measurementUncertaintyDao.SetDaoClass(UncertaintyType.class);
    }

    @Autowired
    private JpaQueryProvider jpa;

    @Override
    public String getMetaInfo(List<Long> substance, List<Long> state, List<Long> quantity) throws JSONException {

    /*   The resultArray has a structure:
     *   [substanceInStateObject1, substanceInStateObject2, ...]
     *
     *   One substanceInStateObject contains information about one pair "substance-state"
     *   and has a structure:
     *   {
     *       substance: substanceName,
     *       state: stateName,
     *       columns: columnsArray,
     *       quantityName1: {
     *           name: quantityName1,
     *           dimensions: dimensionsArray
     *       },
     *       quantityName2: {
     *           name: quantityName2,
     *           dimensions: dimensionsArray
     *       },
     *       ...
     *   }
     *
     *   One dimensionsArray represents information about dimensions for one quantity:
     *   [dimension1, dimension2, ...]
     *
     *   The columnsArray is an array for further constructing of jQuery DataTable columns:
     *   [
     *       {'mData': 'substance', 'sTitle': 'Substance'},
     *       {'mData': 'state', 'sTitle': 'State'},
     *       {'mData': quantityName1, 'sTitle': quantityName1},
     *       {'mData': quantityName2, 'sTitle': quantityName2},
     *       ...
     *   ]
     *
     *   EXAMPLE:
     *   [
     *       {
     *           substance: Neon,
     *           state: liquid-solid,
     *           Pressure: {
     *               name: Pressure,
     *               dimensions: [atm] },
     *           PressureOfMelting: {
     *               name: PressureOfMelting,
     *               dimensions: [bar] },
     *           Temperature: {
     *               name: Temperature,
     *               dimensions: [K] },
     *           columns: [
     *               {'mData': 'substance', 'sTitle': 'Substance'},
     *               {'mData': 'state', 'sTitle': 'State'},
     *               {'mData': Pressure, 'sTitle': Pressure},
     *               {'mData': PressureOfMelting, 'sTitle': PressureOfMelting},
     *               {'mData': Temperature, 'sTitle': Temperature} ]
     *       }
     *   ]
     */

        JSONArray resultArray = new JSONArray();
        List<Object[]> result = jpa.getMetaInfo(substance, state, quantity);

        for (Object[] obj: result) {
            JSONObject substanceInStateObject = new JSONObject();
            JSONArray columnsArray = new JSONArray();

            columnsArray.put(new JSONObject("{mData: substance, sTitle: Substance}"));
            columnsArray.put(new JSONObject("{mData: state, sTitle: State}"));

            /*  Each Object obj has a structure:
             *  [substanceName, stateName, quantities]
             *
             *  The 'quantities' string is:
             *  "quantityId1 quantityName1, quantityId2 quantityName2, ..."
             *
             *  EXAMPLE:
             *  [Neon, liquid-solid, 1 Temperature, 2 Pressure, 3 PressureOfMelting]
             */

            String substanceName = obj[0].toString();
            String stateName = obj[1].toString();
            String quantities = obj[2].toString();

            String[] quantityArray = quantities.split(","); // [1 Temperature, 2 Pressure, 3 PressureOfMelting]

            for (String singleQuantity: quantityArray) { // "1 Temperature"
                JSONArray dimensionsArray = new JSONArray();
                String[] idAndName = singleQuantity.split(" "); // [1, Temperature]

                String quantityId = idAndName[0]; // 1
                String quantityName = idAndName[1]; // Temperature

                String dimensions = jpa.getDimensions(Integer.parseInt(quantityId)); // [K, C, ...]
                if (dimensions != null) {
                    String[] dimArray = dimensions.split(",");
                    for (String d : dimArray) { // K
                        dimensionsArray.put(d);
                    }
                } else dimensionsArray.put("none");

                //Create new JSONObject for current quantity: {name: quantityName, dimensions: dimensionsArray}
                //and put it in substanceInStateObject
                substanceInStateObject.put(quantityName, new JSONObject("{" +
                        "name: "+quantityName+", " +
                        "dimensions: "+dimensionsArray.toString()+
                "}"));

                //Create new JSONObject for column: {mData: quantityName, sTitle: quantityName}
                //and put it in columnsArray
                columnsArray.put(new JSONObject("{mData: "+quantityName+", sTitle: "+quantityName+"}"));
            }

            substanceInStateObject.put("substance", substanceName);
            substanceInStateObject.put("state", stateName);
            substanceInStateObject.put("columns", columnsArray);
            resultArray.put(substanceInStateObject);
        }
        return resultArray.toString();
    }

    @Override
    public JSONObject getNumericData(String metaData) throws JSONException {
        /*
         *  metaData string is "Neon,liquid-solid,Temperature K,Pressure atm,PressureOfMelting Bar"
         */
        String[] data = metaData.split(",");
        /*
         *  data is [Neon, liquid-solid, Temperature K, Pressure atm, PressureOfMelting Bar]
         */
        String substance = data[0];
        String state = data[1];
        JSONObject result = new JSONObject();
        /*
         *  The result object will have a structure:
         *  {
         *      substance: substanceName,
         *      state: stateName,
         *      numericData: numericDataArray,
         *      dataSources: dataSourcesArray,
         *      uncertainties: uncertaintiesArray,
         *      headers: headersArray
         *  }
         *
         *  numericDataArray represents data grouped in objects by pair dataSet-rowNumber
         *  and is ready to be displayed in NumericDataPage.jsp
         *  [   {
         *          Pmelt (Bar): 68.5,
         *          Pmelt-Unc-1: 10,
         *          T (K): 300,
         *          datasource: 1
         *      },
         *      {
         *          Pmelt (Bar): 30.45,
         *          Pmelt-Unc-1: 10,
         *          T (K): 280,
         *          datasource: 10
         *      },
         *      ...     ]
         *
         *  dataSourceArray contains information about required data sources:
         *  [   {
         *          id: 1,
         *          bibliographicReference: reference1
         *      },
         *      ...     ]
         *
         *  uncertaintiesArray contains information about required uncertainties:
         *  [   {
         *          id: 1,
         *          uncertaintyName: Standard,
         *          uncertaintyValue: 10
         *      },
         *      ...     ]
         *
         *  headersArray represents headers for further numericData displaying in jQuery DataTable:
         *  [
         *      {mData: Pmelt (Bar), sTitle: Pmelt (Bar)},
         *      {mData: Pmelt-Unc-1, sTitle: Pmelt-Unc-1},
         *      {mData: datasource, sTitle: datasource}
         *  ]
         */
        JSONArray numericData = new JSONArray();
        ArrayList<Integer> requiredDataSources = new ArrayList<>();
        ArrayList<Integer> requiredUncertainties = new ArrayList<>();
        boolean multipleUncertainties = false;
        JSONObject uncertaintyValues = new JSONObject();
        /*
         *  multipleUncertainties and uncertaintyValues object will be used to
         *  determine the type (template) of data. Structure of uncertaintyValues:
         *  {
         *      Unc-1: 10,
         *      Unc-2: 15,
         *      Unc-3: 1, ...
         *  }
         */
        Map<String, JSONObject> hashmap = new HashMap<String, JSONObject>();
        /*
         *  hashmap will contain the key: pair dataSet-rowNumber and value: object like in numericDataArray
         */
        for (int i = 2; i < data.length; ++i) {
            String[] quantityDimension = data[i].split(" ");
            /*
             *  quantityDimension is [Temperature, K]
             */
            String quantity = quantityDimension[0];
            String dimension = quantityDimension[1];

            //Get Numeric Data for current substance, state, quantity and dimension
            List<Object[]> dataList = jpa.getNumericDataList(substance, state, quantity, dimension);

            for (Object[] resultLine: dataList) {
                /*
                 *  Each resultLine is:
                 *  [
                 *      dataSetId,                  0
                 *      rowNumberId,                1
                 *      substanceName,              2
                 *      stateName,                  3
                 *      quantityDesignation,        4
                 *      dimensionDesignation,       5
                 *      dataSourceId,               6
                 *      quantityValue,              7
                 *      uncertaintyTypeId,          8
                 *      uncertaintyValue            9
                 *  ]
                 */

                //Constructing hashmap
                String key = resultLine[0] + " " + resultLine[1];                           //dataSet rowNumber
                JSONObject value = new JSONObject();
                if (hashmap.containsKey(key)) value = hashmap.get(key);
                String uncertainty = "Unc-" + resultLine[8];                                //Unc-1
                String uncertaintyHeader = resultLine[4] + "-"+ uncertainty;                //Pmelt-Unc-1
                value.put(resultLine[4].toString()+" ("+resultLine[5]+")", resultLine[7]);  //Pmelt (Bar): 168
                value.put(uncertaintyHeader, resultLine[9]);                                //Pmelt-Unc-1: 10
                value.put("datasource", resultLine[6]);                                     //datasource: 1
                hashmap.put(key, value);

                //Constructing list of required dataSources
                Integer dataSourceId = new Integer(resultLine[6].toString());
                if (! requiredDataSources.contains(dataSourceId))
                    requiredDataSources.add(dataSourceId);

                //Constructing list of required uncertainties
                Integer uncertaintyId = new Integer(resultLine[8].toString());
                if (! requiredUncertainties.contains(uncertaintyId))
                    requiredUncertainties.add(uncertaintyId);

                //Constructing uncertaintyValues
                //If there are different values for one uncertainty, type is not less then 2
                if (uncertaintyValues.has(uncertainty) && (uncertaintyValues.get(uncertainty) != resultLine[9]))
                    multipleUncertainties = true;
                else uncertaintyValues.put(uncertainty, resultLine[9]);
                //Assuming if there are two or more uncertainties
                if (uncertaintyValues.length() > 1) multipleUncertainties = true;
            }
        }

        for (String key: hashmap.keySet()) numericData.put(hashmap.get(key));

        int type = 1;
        if (multipleUncertainties) type = 2;
        if (requiredDataSources.size() > 1) type = 3;

        JSONArray dataSources = new JSONArray();
        List<Object[]> dataSourcesList = jpa.getDataSources(requiredDataSources);
        for (Object[] source: dataSourcesList) {
            JSONObject object = new JSONObject();
            object.put("id", source[0]);
            object.put("bibliographicReference", source[1]);
            dataSources.put(object);
        }

        JSONArray uncertainties = new JSONArray();
        List<Object[]> uncertaintiesList = jpa.getUncertainties(requiredUncertainties);
        for (Object[] unc: uncertaintiesList) {
            String uncertaintyValue = "";
            if (type == 1) uncertaintyValue = unc[2].toString();
            JSONObject object = new JSONObject();
            object.put("id", unc[0]);
            object.put("uncertaintyName", unc[1]);
            object.put("uncertaintyValue", uncertaintyValue);
            uncertainties.put(object);
        }

        JSONArray headers = getHeaders(numericData.getJSONObject(0), type);

        result.put("substance", substance);
        result.put("state", state);
        result.put("numericData", numericData);
        result.put("headers", headers);
        result.put("dataSources", dataSources);
        result.put("uncertainties", uncertainties);
        //System.out.println(numericDataList.get(0).getQuantityValue());
        //System.out.println(numericDataList.size());
        return result;
    }


    private JSONArray getHeaders(JSONObject data, int type) throws JSONException {
        JSONArray resultJson = new JSONArray();
        ArrayList<JSONObject> result = new ArrayList<>();
        Iterator<String> keys = data.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.contains("-Unc-") && type == 1) continue;
            if (key.contains("datasource") && type != 3) continue;
            result.add(new JSONObject("{" +
                    "mData: "+key+", " +
                    "sTitle: "+key+
            "}"));
        }

        result.sort(new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                try {
                    if (o1.get("sTitle").toString().contains("datasource")) return 1;
                    if (o2.get("sTitle").toString().contains("datasource")) return -1;
                    if (o1.get("sTitle").toString().compareToIgnoreCase(o2.get("sTitle").toString()) > 0)
                        return 1;
                    else return -1;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });

        result.forEach(new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                resultJson.put(jsonObject);
            }
        });

        return resultJson;
    }
}
