package com.app.mvc;

import com.app.mvc.RuleProcesser.ReasonerRuleProcesser;
import com.app.mvc.db2ont.Db2OntMain;
import org.junit.Test;

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
}
