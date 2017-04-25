package com.app.mvc.OntologyModelDao;

import java.net.URI;
import java.net.URISyntaxException;

public enum Constants {
    CLASS("http://www.w3.org/2002/07/owl#Class"),
    SUBCLASS("http://www.w3.org/2000/01/rdf-schema#subClassOf"),
    ELEMENT("http://www.w3.org/1999/02/22-rdf-syntax-ns#type"),
    OBJECTPROPERTY("http://www.w3.org/2002/07/owl#ObjectProperty"),
    PROPERTY("http://www.ThermalDb.ru"),
    RDFS("http://www.w3.org/2000/01/rdf-schema"),
    OWL("http://www.w3.org/2002/07/owl"),
    HOME("C:\\Users\\Катерина\\Github\\src\\main\\resources\\"),
    WWW("http://www.ThermalDb.ru");


    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public URI asURI() {
        URI uri = null;
        try {
            uri =  new URI(value);
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return uri;
    }

}
