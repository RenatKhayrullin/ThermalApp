package com.app.mvc;

import com.app.mvc.RuleProcesser.MathMLCalculatorImpl.MathMLServiceWolframAPIImpl;
import com.app.mvc.RuleProcesser.ReasonerRuleProcesser;
import com.app.mvc.db2ont.Db2OntMain;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RuleTests {
    @Test
    public void testDb2Ont() throws Exception {
        assert(Db2OntMain.processDB2Ont());
    }

    @Test
    public void testRuleCheck() throws Exception {
        ReasonerRuleProcesser processer = new ReasonerRuleProcesser();
        processer.loadOntology("src/main/resources/OntologyStorage.rdf");
        processer.loadRules("src/main/resources/SWRLRules.rdf");
        processer.printRules();
        processer.processRules();
        processer.flushToFile("src/main/resources/OntologyStorage.rdf");
    }

    @Test
    public void testCheckReset() throws Exception {
        ReasonerRuleProcesser processer = new ReasonerRuleProcesser();
        processer.loadOntology("src/main/resources/OntologyStorage.rdf");
        processer.resetCheckInformation();
        processer.flushToFile("src/main/resources/OntologyStorage.rdf");
    }

    @Test
    public void testWolfram() throws Exception {
        MathMLServiceWolframAPIImpl calc = new MathMLServiceWolframAPIImpl();
        String formula = "<math>\n" +
                         "    <apply>\n" +
                         "        <divide/>\n" +
                         "        <ci>x</ci>\n" +
                         "        <apply>\n" +
                         "            <divide/>\n" +
                         "            <cn>3.0</cn>\n" +
                         "            <apply>\n" +
                         "                <divide/>\n" +
                         "                <cn>2.0</cn>\n" +
                         "                <cn>3.0</cn>\n" +
                         "            </apply>\n" +
                         "        </apply>\n" +
                         "    </apply>\n" +
                         "</math>";
        Map<String, Double> vars = new HashMap<String, Double>();
        vars.put("x", 50.0);

        double res = calc.calculateExpression(formula, vars);

        System.out.println(res);
    }
}
