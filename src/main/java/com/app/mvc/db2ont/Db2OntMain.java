package com.app.mvc.db2ont;

import com.app.mvc.dataBaseDomainModel.*;
import com.app.mvc.dataBaseDomainModelDao.*;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.util.FileManager;

public class Db2OntMain {
    public static final String OntologyStorageFilename = "src/main/resources/OntologyStorage.rdf";
    public static final String OntologyDefinitionFilename = "src/main/resources/OntologyDefinition.rdf";

    public static OntModel getCurrentOntology() {
        OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream is = FileManager.get().open(OntologyStorageFilename);
            if (is == null) {
                return model;
            }
            model.read(is, null);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return model;
    }

    public static void markDatasetsProcessed() {
        /*
        OntologyAbstractDao<DataSet> datasetsDao = new OntologyAbstractDaoImpl<DataSet>(DataSet.class);

        List<DataSet> datasets = datasetsDao.findAllEntity();
        Iterator<DataSet> it = datasets.iterator();
        while (it.hasNext()) {
            DataSet dataset = it.next();

            dataset.setIsUploadedToViewModel(true);
            datasetsDao.saveOrUpdate(dataset);
        }
        */
    }

    //all-in-one function, contains the whole db2ont process
    public static boolean processDB2Ont() {
        OntModel modelFromDb = MorphWorker.processMorph();
        if (null == modelFromDb) {
            return false;
        }
        OntModel modelClasses = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream is = FileManager.get().open(OntologyDefinitionFilename);
            modelClasses.read(is, null);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        modelClasses.add(modelFromDb);
        PointsOfMeasureLinkingProcesser.doPMLinking(modelClasses);
        //SubstanceInStatesClassDataGenerator.doGenerateSubstanceInStatesDataClasses(modelClasses);

        OntModel currentModel = getCurrentOntology();
        if (currentModel == null) {
            return false;
        }

        currentModel.add(modelClasses);
        try {
            OutputStream out = new FileOutputStream(OntologyStorageFilename);
            currentModel.write(out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        //markDatasetsProcessed();

        return true;
    }

}
