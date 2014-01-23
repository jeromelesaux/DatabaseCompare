package fr.axione.dbcompare.parser.dmd;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import fr.axione.dbcompare.model.dmditem.DmdProjectConstants;
import fr.axione.dbcompare.model.dmditem.Schema;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by jlesaux on 21/01/14.
 */
public class DmdStructure {

    DmdProjectConstants dmdConstants;
    Schema schema;

    public Schema getSchema(String xmlRootFilePath) throws Exception {
        dmdConstants = new DmdProjectConstants(xmlRootFilePath);

        schema = new Schema();

        schema = parseObjectsLocalFile(schema);

        return schema;
    }


    public Schema parseObjectsLocalFile(Schema schema)
            throws IOException, SAXException, ParserConfigurationException {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(dmdConstants.getObjectsLocalFilePath());
        document.getDocumentElement().normalize();


        Element rootElement = document.getDocumentElement();
        System.out.println(rootElement.getNodeName());

        NodeList childNodes= rootElement.getChildNodes();

        for (int i=0; i<childNodes.getLength(); i++){

            Element element = null;
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                element = (Element)childNodes.item(i);
                String objectType = element.getAttribute("objectType");
                String objectID = element.getAttribute("objectID");
                String name = element.getAttribute("name");
                String seqName = element.getAttribute("seqName");
                System.out.println(element.getNodeName() + " " + objectType + " " + objectID + " " + name + " " + seqName);
            }
        }



        return schema;
    }


}
