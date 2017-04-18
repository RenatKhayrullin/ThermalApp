package com.app.mvc.OntologyModelDao;

import com.app.mvc.TreeModel.CourtBranch;
import com.app.mvc.TreeModel.OntList;
import com.app.mvc.TreeModel.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.complexible.stardog.ext.spring.SnarlTemplate;
import com.app.mvc.TreeModel.ElProperty;

import java.util.ArrayList;
import java.util.List;

@Repository
@Component
public class StardogDao {
    private static final Logger logger = LoggerFactory.getLogger(StardogDao.class);

    @Autowired
    private SnarlTemplate snarlTemplate;

    public StardogDao() {}

    private String createNewSubject(String name) {
        return Constants.WWW + "#" + name;
    }

    //Получить иерархию классов
    public List<OntList> getOntList() {

        String query = "select distinct ?type ?supertype WHERE { { ?type a <" + Constants.CLASS.toString() + "> . } UNION { ?ind a ?type . }. " +
                "OPTIONAL { ?supertype <" + Constants.SUBCLASS.toString() + "> ?type } . " +
                "} ORDER BY ?type ?supertype";

        logger.debug("Class Hierarchy: " + query);

        List<OntList> results = snarlTemplate.query(query, bindingSet -> {
            OntList branch = new OntList();
            branch.setName(bindingSet.getValue("type").toString()
                    .substring(bindingSet.getValue("type").toString().indexOf("#")+1,bindingSet.getValue("type").toString().length()));

            if (bindingSet.getValue("supertype") != null) {
                branch.setParent(bindingSet.getValue("supertype").toString()
                        .substring(bindingSet.getValue("supertype").toString().indexOf("#")+1, bindingSet.getValue("supertype").toString().length()));
            } else {
                branch.setParent("0");
            }

            return branch;
        });
        return results;
    }

    //Обработка результата иерархии классов
    public List<CourtBranch> makeOntTree() {
        List<OntList> results = getOntList();
        List<CourtBranch> elements = new ArrayList<CourtBranch>();

        String Base = "Thing";
        int i = 0; //счетчик
        int b = 1; //идентификатор базового класса
        boolean is = true;

        elements.add(new CourtBranch(++i, Base, 0));

        List<OntList> clear = new ArrayList<>();

        for (final OntList result : results) {
            if(!result.getParent().equals("Thing") && !result.getParent().equals("Nothing") && !result.getParent().equals(result.getName())) {
                clear.add(new OntList(result.getName(), result.getParent()));
            }
        }

        while (!clear.isEmpty()) {

            //algoritm
            List<OntList> baseList = new ArrayList<OntList>(); //base class
            List<OntList> childList = new ArrayList<OntList>(); //classes with children


            //разбиение на 2 циакла - цикл с базовым классом и все остальное
            for (final OntList result : clear) {
                if (result.getName().equals(Base)) {
                    baseList.add(new OntList(result.getName(), result.getParent()));
                } else {
                    childList.add(new OntList(result.getName(), result.getParent()));
                }
            }

            //цикл по base class
            //заполняем elements
            for (final OntList base : baseList) {
                for (final OntList child : childList) {
                    if (base.getParent().equals(child.getParent())) {
                        is = false;
                    }
                }
                if (is == true) {
                    elements.add(new CourtBranch(++i, base.getParent(), b));
                }
                is = true;
            }
            if (!childList.isEmpty()) {
                Base = childList.get(childList.size() - 1).getName();

                //узнаем индекс базового элемента
                for (final CourtBranch element : elements) {
                    if (element.getName().equals(Base)) {
                        b = element.getId();
                        break;
                    }
                }
            }
            clear = childList;
        }

        return elements;
    }

    //получить экземпляры
    public List<Elements> getInstances(String name) {
        Elements elements = new Elements();
        String nameUri = createNewSubject(name);
        String query = "SELECT ?s { ?s <" + Constants.ELEMENT + "> <"+nameUri+">}";
        List<Elements> results = snarlTemplate.query(query, bindingSet -> {
            Elements el = new Elements();
            el.setElement(bindingSet.getValue("s").toString().substring((bindingSet.getValue("s").toString().indexOf("#")+1),bindingSet.getValue("s").toString().length()));
            return el;
        });

        return results;
    }

    //по названию класса доставать objectproperty
    public List<String> getObjProperty(String name) {
        String nameUri = createNewSubject(name);

        String query = "select ?objprop WHERE " +
                "{?objprop a <" + Constants.OWL + "#ObjectProperty> ." +
                "?objprop <" + Constants.RDFS + "#domain> <"+nameUri+"> .}";

        System.out.println("SparqlQuery For ObjProp: "+query);

        List<String> results = snarlTemplate.query(query, bindingSet -> {
            String branch = "";

            if(bindingSet.getValue("objprop") != null
                    && !bindingSet.getValue("objprop").toString()
                    .equals("http://www.w3.org/2002/07/owl#bottomObjectProperty"))
            {
                branch = bindingSet.getValue("objprop").toString()
                        .substring((bindingSet.getValue("objprop").toString().indexOf("#")+1),bindingSet.getValue("objprop").toString().length());
            }
            return branch;
        }
        );
        System.out.println("OBJ PROPS :");
        results.forEach(System.out::println);

        return results;
    }

    //по названию класса доставать objectproperty
    public List<String> getDataProperty(String name) {
        String nameUri = createNewSubject(name);

        String query = "select ?dataprop WHERE " +
                "{?dataprop a <" + Constants.OWL + "#DatatypeProperty> ." +
                "?dataprop <" + Constants.RDFS + "#domain> <" +nameUri+"> .}";

        System.out.println("SparqlQuery : "+query);

        List<String> results = snarlTemplate.query(query, bindingSet -> {
            String branch = "";

            if(bindingSet.getValue("dataprop") != null
                    && !bindingSet.getValue("dataprop").toString()
                    .equals("http://www.w3.org/2002/07/owl#bottomDataProperty"))
            {
                branch = bindingSet.getValue("dataprop").toString()
                        .substring((bindingSet.getValue("dataprop").toString().indexOf("#") + 1), bindingSet.getValue("dataprop").toString().length());
            }
            return branch;
        }
        );
        System.out.println("DATA PROPS :");
        results.forEach(System.out::println);

        return results;
    }

    //по названию экземпляра доставать objectproperty и dataproperty и их значения
    public List<ElProperty> getElProperty(String name) {
        String nameUri = createNewSubject(name);

        String query = "select ?objprop ?dataprop ?value1 ?value2 WHERE {" +
                " {?objprop a <" + Constants.OWL + "#ObjectProperty> ." +
                "<" +nameUri+"> ?objprop ?value1 .}" +
                "union" +
                "{?dataprop a <" + Constants.OWL + "#DatatypeProperty> ." +
                "<" +nameUri+"> ?dataprop ?value2 .}" +
                "}";

        List<ElProperty> results = snarlTemplate.query(query, bindingSet -> {
            ElProperty branch = new ElProperty();

            if(bindingSet.getValue("objprop") != null) {
                branch.setObjectProperty(bindingSet.getValue("objprop").toString().substring((bindingSet.getValue("objprop").toString().indexOf("#")+1),bindingSet.getValue("objprop").toString().length()));
                branch.setValueOfObjectProperty(bindingSet.getValue("value1").toString().substring((bindingSet.getValue("value1").toString().indexOf("#")+1),bindingSet.getValue("value1").toString().length()));
            }
            if(bindingSet.getValue("dataprop") != null) {
                branch.setDataProperty(bindingSet.getValue("dataprop").toString().substring((bindingSet.getValue("dataprop").toString().indexOf("#") + 1), bindingSet.getValue("dataprop").toString().length()));
                branch.setValueOfDataProperty(bindingSet.getValue("value2").toString());
            }

            return branch;
        });

        return results;
    }

}
