package com.app.mvc.Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ChemSpiderXMLHelper {
	
    public static List<ChemSpiderInfo> getInfoListFromAtomXML(Document doc){
		List<ChemSpiderInfo> result = new ArrayList<>();
		if (!isAtom(doc))
			return result;
		
		NodeList entries = doc.getElementsByTagName("entry");
		System.out.println("entries.getLength() " +entries.getLength());
		for (int i = 0; i < entries.getLength(); i++){
			Element entry = (Element)entries.item(i);
			result.add(getInfoFromAtomEntry(entry));
		}
		return result;
	}
	
	private static ChemSpiderInfo getInfoFromAtomEntry(Element entryTag){
		ChemSpiderInfo info = new ChemSpiderInfo();
		if (!"entry".equals(entryTag.getNodeName()))
			throw new RuntimeException(" is not entry tag!");
		
		NodeList links = entryTag.getElementsByTagName("link");
		for (int i = 0; i < links.getLength(); i++){
			Node l = links.item(i);
		    if ("self".equals(l.getAttributes().getNamedItem("rel").getTextContent())){
		    	info.setChemSpiderUri(l.getAttributes().getNamedItem("href").getTextContent());
		    }
		    if ("alternate".equals(l.getAttributes().getNamedItem("rel").getTextContent())){
		    	info.setChemSpiderURL(l.getAttributes().getNamedItem("href").getTextContent());
		    }
		}
		
		NodeList ps = entryTag.getElementsByTagName("p");
		for (int i = 0; i < ps.getLength(); i++){
			Element p = (Element) ps.item(i);
		    NodeList imgTags = p.getElementsByTagName("img");
		    //info.setPictureURL(pictureURL)
		    if (imgTags.getLength() > 0){
		    	info.setPictureURL(imgTags.item(0).getAttributes().getNamedItem("src").getTextContent());
		    }
		    else {
		    	Element b = (Element) p.getElementsByTagName("b").item(0);
		    	Element span = (Element)p.getElementsByTagName("span").item(0);
		    	
		    	if ("ChemSpider ID:".equals(b.getTextContent())){
		    		Long l = Long.parseLong(span.getTextContent());
		    		info.setChemSpiderId(BigInteger.valueOf(l));
		    	} else if ("Systematic Name:".equals(b.getTextContent())){
		    		info.setSystematicName(span.getTextContent());
		    	} else if ("Molecular Formula:".equals(b.getTextContent())){
		    		info.setFormula(span.getTextContent());
		    	}
		    	
		    	
		    }
		}
		
	    return info;
	}
	
	static boolean isAtom(Document d){
		NodeList nl = d.getElementsByTagName("feed");
		if (nl.getLength() > 0) return true;
		return false;
	}
	static boolean isRDF(Document d){
		NodeList nl = d.getElementsByTagName("rdf:RDF");
		if (nl.getLength() > 0) 
			return true;
		return false;
	}



}
