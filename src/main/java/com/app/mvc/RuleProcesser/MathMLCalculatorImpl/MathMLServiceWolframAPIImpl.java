package com.app.mvc.RuleProcesser.MathMLCalculatorImpl;

import com.app.mvc.RuleProcesser.MathMLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class MathMLServiceWolframAPIImpl extends MathMLService {
    private static final String API_URL = "http://api.wolframalpha.com/v2/query?";
    private static final String APP_ID = "6Y3H4W-PRLHXUPQAA";
    private static final String USER_AGENT = "Mozilla/5.0";

    private static Logger log = LoggerFactory.getLogger(MathMLServiceWolframAPIImpl.class);

    private String sendGetRequest(String input) throws Exception {
        String url = API_URL+"input=" + URLEncoder.encode(input, "UTF-8") + "&appid="+APP_ID +
                "&includepodid=Result&format=plaintext";

        log.debug("request to "+url);

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        log.debug("got response from "+url);

        return response.toString();
    }

    private String extractResult(String response) throws Exception {
        String result = "";

        System.out.println(response);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(response));
        Document document = builder.parse(is);

        Element root = document.getDocumentElement();

        NodeList pods = root.getElementsByTagName("pod");
        for (int i = 0; i < pods.getLength(); i++) {
            Element elem = (Element) pods.item(i);
            if (elem.getAttribute("id").equals("Result")) {
                Element resultSubpod = (Element) elem.getElementsByTagName("subpod").item(0);
                Element resultPlainText = (Element) resultSubpod.getElementsByTagName("plaintext").item(0);

                result = resultPlainText.getTextContent();
                break;
            }

        }

        result = result.replace("…", "");

        return result;
    }

    private String getStringResult(String formula, Map<String, Double> vars) throws Exception {
        String pastedFormula = pasteMathMLArguments(formula, vars);
        String response = sendGetRequest(pastedFormula);
        String result = extractResult(response);

        return result;
    }

    @Override
    public boolean checkExpression(String formula, Map<String, Double> vars) throws Exception {
        String result = getStringResult(formula, vars);

        return Boolean.parseBoolean(result.toLowerCase());
    }

    @Override
    public double calculateExpression(String formula, Map<String, Double> vars) throws Exception {
        String result = getStringResult(formula, vars);

        return Double.parseDouble(result);
    }
}
