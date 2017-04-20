package com.app.mvc.Utility;


import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class XMLLoader {
	public static Document loadXmlFromString(String str) throws IllegalArgumentException, DOMException
    {
		InputStream  inputStream = null;
	    Document doc = null;
		try {
			inputStream = new ByteArrayInputStream(str.getBytes("UTF-8"));
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
		    doc = builder.parse(inputStream);
		    inputStream.close();
		} 
		catch (IOException e) {
		//	log.info(e);
			throw new DOMException((short)1, "Error when parsing the xml document");
		}
	    
		catch (ParserConfigurationException e) {
			//log.info(e);
			throw new DOMException((short)1, "Error when parsing the xml document");
		}
		
		catch (SAXException e) {
			//log.info(e);
			throw new DOMException((short)1, "Error when parsing the xml document");
		}
		
		
		finally{
			
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw new DOMException((short)1, "Error when parsing the xml document");
			}
		}

	    return doc;
    }
	
	public static Document loadXMLFromFile(String url) throws IllegalArgumentException, DOMException
    {
		    
			InputStream  inputStream = null;
		    Document doc = null;
			try {
				inputStream = new FileInputStream(url);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
			    doc = builder.parse(inputStream);
			    inputStream.close();
			} 
			catch (IOException e) {
			//	log.info(e);
				throw new DOMException((short)1, "Error when parsing the xml document");
			}
		    
			catch (ParserConfigurationException e) {
				//log.info(e);
				throw new DOMException((short)1, "Error when parsing the xml document");
			}
			
			catch (SAXException e) {
				//log.info(e);
				throw new DOMException((short)1, "Error when parsing the xml document");
			}
			
			
			finally{
				
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					throw new DOMException((short)1, "Error when parsing the xml document");
				}
			}

		    return doc;
		}

}
