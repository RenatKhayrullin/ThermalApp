package com.app.mvc.applicationServices.ServiceImpl;

import com.app.mvc.applicationServices.MetaInfoService;
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
}
