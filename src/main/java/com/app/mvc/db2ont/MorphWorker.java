package com.app.mvc.db2ont;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class MorphWorker {
    public static final String morphCmd = "java -cp .:morph.jar:lib/* es.upm.fi.dia.oeg.morph.r2rml.rdb.engine.MorphRDBRunner db2ont_individuals db2ont_individuals.morph.properties";
    public static final String morphDir = "morph";
    public static final String morphOutput = "ont-result.nt";
    public static final String morphOutputFormat = "N-TRIPLE";

    //internal function just to generate triples with morph-rdb
    public static boolean runMorphDRB() {
        try {
            Process pr = Runtime.getRuntime().exec(morphCmd, null, new File(morphDir));
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //internal function to get OntModel from generated rdf file
    public static OntModel getMorphedModel() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream is = FileManager.get().open(morphDir + "/" + morphOutput);
            model.read(is, null, morphOutputFormat);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return model;
    }

    //all-in-one function, run it to get new individuals in OntModel
    public static OntModel processMorph() {
        if (!runMorphDRB()) {
            return null;
        }
        return getMorphedModel();
    }
}
