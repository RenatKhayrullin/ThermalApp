package com.app.mvc.RuleProcesser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class MathMLService {
    public List<String> getVarsInFormula(String formula) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(formula));
        Document document = builder.parse(is);
        Element root = document.getDocumentElement();

        List<String> vars = new ArrayList<String>();

        NodeList ciElems = root.getElementsByTagName("ci");
        for (int i = 0; i < ciElems.getLength(); i++) {
            String varName = ciElems.item(i).getTextContent();

            if (!vars.contains(varName)) {
                vars.add(varName);
            }
        }

        return vars;
    }

    private void pasteMathMLArguments(NodeList nodeList, Map<String, Double> vars)
    {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            String childNodeVar = childNode.getTextContent();

            if (vars.get(childNodeVar) != null) {
                childNode.setTextContent(String.valueOf(vars.get(childNodeVar)));
            }

            NodeList children = childNode.getChildNodes();
            if (children != null)
            {
                pasteMathMLArguments(children, vars);
            }
        }
    }

    public String pasteMathMLArguments(String formula, Map<String, Double> vars) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(formula));
        Document document = builder.parse(is);
        Element root = document.getDocumentElement();

        pasteMathMLArguments(root.getChildNodes(), vars);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
        String output = writer.getBuffer().toString().replaceAll("\n|\r", "");

        return output;
    }

    public abstract boolean checkExpression(String formula, Map<String, Double> vars) throws Exception;
    public abstract double calculateExpression(String formula, Map<String, Double> vars) throws Exception;
}
