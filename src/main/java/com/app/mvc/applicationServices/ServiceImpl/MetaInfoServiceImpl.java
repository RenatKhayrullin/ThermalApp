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

import java.util.ArrayList;
import java.util.List;
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
        List<Object[]> result = jpa.getMetaInfo(substance, state, quantity);
        JSONObject outerObject = new JSONObject();
        JSONArray outerArray = new JSONArray();

        for (Object[] obj: result) {
            JSONObject mediumObject = new JSONObject();
            JSONArray mediumArray = new JSONArray();

            String quantities = obj[2].toString();
            String[] quantityDimension = quantities.split(",");
            for (String st:quantityDimension) {
                JSONObject innerObject = new JSONObject();
                JSONArray innerArray = new JSONArray();

                String[] singleQuantity = st.split(" ");
                //System.out.println(singleQuantity[0] + "-" + singleQuantity[1]);
                String dimensions = jpa.getDimensions(Integer.parseInt(singleQuantity[0]));
                //System.out.println("dimensions    " + dimensions);
                if (dimensions != null) {
                    String[] dimArray = dimensions.split(",");
                    for (String d : dimArray) {
                        innerArray.put(d);
                    }
                } else innerArray.put("none");
                innerObject.put("quantity", singleQuantity[1]);
                innerObject.put("dimensions", innerArray);
                mediumArray.put(innerObject);
            }
            mediumObject.put("substance", obj[0]);
            mediumObject.put("state", obj[1]);
            mediumObject.put("quantities", mediumArray);
            outerArray.put(mediumObject);
        }
        outerObject.put("data", outerArray);
        return outerObject.toString();
    }

    @Override
    public List<NumericData> getNumericData(String metaData) {
        String[] data = metaData.split(",");
        String substance = data[0];
        String state = data[1];
        //System.out.println(substance);
        //System.out.println(state);
        ArrayList<NumericData> numericDataList = new ArrayList<>();

        for (int i = 2; i < data.length; ++i) {
            String[] quantityDimension = data[i].split(" ");
            String quantity = quantityDimension[0];
            String dimension = quantityDimension[1];
            //System.out.println(quantity);
            //System.out.println(dimension);

            List<Object[]> result = jpa.getNumericDataList(substance, state, quantity, dimension);
            //System.out.println("resultSize  " + result.size());
            for (Object[] resultLine: result) {
                NumericData numericData = new NumericData(resultLine[0], resultLine[1], resultLine[2], resultLine[3],
                        resultLine[4], resultLine[5], resultLine[6], resultLine[7], resultLine[8], resultLine[9]);
                //System.out.println(numericData.getReference());
                numericDataList.add(numericData);
            }
        }

        //System.out.println(numericDataList.get(0).getQuantityValue());
        //System.out.println(numericDataList.size());
        return numericDataList;
    }

    @Override
    public List getAllDataSources() {
        return dataSourceDao.findAll();
    }

    @Override
    public List getAllMeasurementUncertainties() {
        return measurementUncertaintyDao.findAll();
    }
}
