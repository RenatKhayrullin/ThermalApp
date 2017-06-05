package com.app.mvc.RuleProcesser;

import com.clarkparsia.pellet.PelletOptions;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.model.parameters.Imports;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.SimpleConfiguration;
import org.semanticweb.owlapi.util.*;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

public class ReasonerRuleProcesser {
    public static final String THERMALONT_PREFIX = "http://www.ThermalDb.ru#";

    private static org.slf4j.Logger log = LoggerFactory.getLogger(ReasonerRuleProcesser.class);

    private OWLOntology fullOntology;
    private OWLOntologyManager fullOntologyManager;
    private OWLOntology rulesOntology;
    private OWLOntologyManager rulesOntologyManager;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private OWLDataFactory fullOntologyDataFactory;

    public void printRules() {
        try {
            Set<SWRLRule> rules = rulesOntology.getAxioms(AxiomType.SWRL_RULE);
            Iterator<SWRLRule> it = rules.iterator();
            while (it.hasNext()) {
                SWRLRule rule = it.next();
                System.out.println(rule.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadOntology(String ontologyPath) throws OWLOntologyCreationException {
        fullOntologyManager = OWLManager.createOWLOntologyManager();
        fullOntologyDataFactory = fullOntologyManager.getOWLDataFactory();

        File fin = new File(ontologyPath);
        fullOntology = fullOntologyManager.loadOntologyFromOntologyDocument(IRI.create(fin));
        reasonerFactory = PelletReasonerFactory.getInstance();
        reasoner = reasonerFactory.createReasoner(fullOntology, new SimpleConfiguration());
    }

    public void loadRules(String rulesPath) throws OWLOntologyCreationException {
        SWRLCalculation.registerBuiltin();
        rulesOntologyManager = OWLManager.createOWLOntologyManager();
        File fin = new File(rulesPath);
        rulesOntology = rulesOntologyManager.loadOntologyFromOntologyDocument(IRI.create(fin));
    }

    public void processRules() {
        if (rulesOntology == null) {
            return;
        }

        Set<SWRLRule> rules = this.rulesOntology.getAxioms(AxiomType.SWRL_RULE);
        Iterator<SWRLRule> it = rules.iterator();
        ArrayList<OWLOntologyChange> ruleChanges = new ArrayList<OWLOntologyChange>();
        log.debug("adding changes");
        while (it.hasNext()) {
            SWRLRule rule = it.next();

            ruleChanges.add(new AddAxiom(fullOntology, rule));
        }

        log.debug("changes added");
        fullOntologyManager.applyChanges(ruleChanges);
        log.debug("changes applied");
    }

    public void flushToFile(String path) throws OWLOntologyCreationException {
        log.debug("flush start");
        reasoner.flush();
        log.debug("flush ended");
        InferredOntologyGenerator gen = new InferredOntologyGenerator(reasoner);
        OWLOntology infOnt = fullOntologyManager.createOntology();
        gen.fillOntology(fullOntologyDataFactory, infOnt);
        log.debug("ont filled");
        try {
            fullOntologyManager.saveOntology(infOnt, new FileOutputStream(new File(path)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetCheckInformation() {
        PrefixManager pm = new DefaultPrefixManager(null, null, THERMALONT_PREFIX);
        OWLClass pom = fullOntologyDataFactory.getOWLClass(":PointOfMeasure", pm);
        OWLDataProperty dp = fullOntologyDataFactory.getOWLDataProperty(":hasPassedCheck", pm);

        Set<OWLDataPropertyAssertionAxiom> axioms = fullOntology.getAxioms(AxiomType.DATA_PROPERTY_ASSERTION);

        Iterator<OWLDataPropertyAssertionAxiom> it = axioms.iterator();
        ArrayList<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
        while (it.hasNext()) {
            OWLDataPropertyAssertionAxiom dpAxiom = it.next();
            if (dpAxiom.getProperty().asOWLDataProperty().getIRI() == dp.getIRI()) {
                changes.add(new RemoveAxiom(fullOntology, dpAxiom));
            }
        }

        fullOntologyManager.applyChanges(changes);
    }

}