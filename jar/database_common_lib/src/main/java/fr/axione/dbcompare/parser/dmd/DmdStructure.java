package fr.axione.dbcompare.parser.dmd;


import fr.axione.dbcompare.model.common.ConstraintType;
import fr.axione.dbcompare.model.dmditem.*;
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
        NodeList childNodes= rootElement.getChildNodes();

        for (int i=0; i<childNodes.getLength(); i++){

            Element element = null;
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                element = (Element)childNodes.item(i);
                if ( element.getNodeName().equals("object")) {
                    String objectType = element.getAttribute("objectType");
                    String objectID = element.getAttribute("objectID");
                    String name = element.getAttribute("name");
                    String seqName = element.getAttribute("seqName");

                    if (objectType.equals("Table")) {
                        Table table = new Table(name,schema);
                        table.setObjectId(objectID);
                        table.setSeqName(seqName);
                        schema.getTables().put(table.getName(),table);
                    }
                    else if (objectType.equals("View")) {
                        View view = new View(name,schema);
                        view.setObjectId(objectID);
                        view.setSeqName(seqName);
                        schema.getViews().put(view.getName(),view);
                    }
                    else if (objectType.equals("FKIndexAssociation")) {
                        Index index = new Index(schema);
                        index.setName(name);
                        index.setObjectId(objectID);
                        index.setSeqName(seqName);
                        index.setType(ConstraintType.FOREIGN_KEY);
                        schema.getIndexes().put(index.getName(),index);
                    }
//                    System.out.println(element.getNodeName() + " " + objectType + " " + objectID + " " + name + " " + seqName);
                }
            }
        }


        schema = getTables(schema);
        schema = getIndex(schema);

        return schema;
    }


    protected Schema getIndex(Schema schema) {

        return schema;
    }

    protected Schema getTables(Schema schema) throws ParserConfigurationException, IOException, SAXException {

        for (String tableName : schema.getTables().keySet()){
            Table table = schema.getTables().get(tableName);
            String filePath = dmdConstants.getRelDirectoryPath() + File.separator + table.getXmlFilePath();
            Document document = null;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(filePath);
            document.getDocumentElement().normalize();
            Element rootElement = document.getDocumentElement();
            NodeList childNodes= rootElement.getChildNodes();

            for (int i=0; i<childNodes.getLength(); i++){

                Element element = null;
                if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals("columns")) {
                    NodeList columnsElements = childNodes.item(i).getChildNodes();
                    for (int j=0;j<columnsElements.getLength();j++){
                        Element columnElement = null;
                        if (columnsElements.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            columnElement = (Element)columnsElements.item(j);
                            if (columnElement.getNodeName().equals("Column")) {
                                String objectID = columnElement.getAttribute("objectId");
                                String name = columnElement.getAttribute("name");
                                Column column = new Column(name,table);
                                column.setObjectId(objectID);
                                NodeList columnsAttribute = columnElement.getChildNodes();
                                for (int h=0; h<columnsAttribute.getLength(); h++) {
                                    Node item = columnsAttribute.item(h);

                                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                                        if ( item.getNodeName().equals("logicalDatatype")) {
                                            String sqlType = item.getFirstChild().getTextContent();
                                            column.setType(ColumnDmdTypeMapper.getColumnType(sqlType));
                                        }
                                        else if ( item.getNodeName().equals("dataTypeSize")) {
                                            String size = item.getFirstChild().getTextContent();
                                            column.setSize(Integer.valueOf(size.replace(" BYTE","")));
                                        }
                                        else if ( item.getNodeName().equals("nullsAllowed")) {
                                            String isNullable = item.getFirstChild().getTextContent();
                                            column.setNullable(Boolean.valueOf(isNullable));
                                        }
                                        else if ( item.getNodeName().equals("pk")) {
                                            String isPrimaryKey = item.getFirstChild().getTextContent();
                                            column.setIsPrimaryKey(Boolean.valueOf(isPrimaryKey));
                                        }
                                    }
                                }
                                table.getColumns().put(column.getName(),column);
                            }
                        }
                    }
                }
                else if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals("indexes")) {
                    NodeList indexElement = childNodes.item(i).getChildNodes();
                    for (int j=0;j<indexElement.getLength();j++){
                        Element columnElement = null;
                        if (indexElement.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            columnElement = (Element)indexElement.item(j);
                            if (columnElement.getNodeName().equals("ind_PK_UK")) {
                                String name = columnElement.getAttribute("name");
                                String objectID = columnElement.getAttribute("objectId");
                                Index index;
                                if ( schema.getIndexes().containsKey(name)) {
                                    index = schema.getIndexes().get(name);
                                }
                                else {
                                    index = new Index(name,schema);
                                    index.setObjectId(objectID);
                                    schema.getIndexes().put(index.getName(),index);
                                }

                                NodeList columnsAttribute = columnElement.getChildNodes();
                                String isPrimaryKey=null;
                                for (int h=0; h<columnsAttribute.getLength(); h++) {
                                    Node item = columnsAttribute.item(h);
                                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                                        if (item.getNodeName().equals("pk")) {
                                             isPrimaryKey = item.getFirstChild().getTextContent();
                                        }
                                        else if (item.getNodeName().equals("indexState")) {
                                            String indexType =  item.getFirstChild().getTextContent();
                                           // System.out.println(table.getObjectId() + " " + table.getName() +" " +  index.getName() + " " + indexType);
                                            ConstraintType type = ConstraintType.valueOf(indexType.toUpperCase().replaceAll(" ","_"));
                                            index.setType(type != null ? type : ConstraintType.UNKNOWN);
                                        }
                                        else if (item.getNodeName().equals("indexColumnUsage")) {
                                            NodeList childIndexNodes = item.getChildNodes();
                                            for (int k =0 ; k < childIndexNodes.getLength(); k++) {
                                               Element indexColumnElement = null;
                                                if (childIndexNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                                    Element colUsageElement = (Element)childIndexNodes.item(k);
                                                    if (colUsageElement.getNodeName().equals("colUsage")) {
                                                        String columnObjectId = colUsageElement.getAttribute("columnID");
                                                        Column column = table.getColumnByObjectId(columnObjectId);
                                                        if ( column != null) {
                                                            if ( isPrimaryKey != null) {
                                                                column.setIsPrimaryKey(Boolean.valueOf(isPrimaryKey));
                                                                index.setType(ConstraintType.PRIMARY_CONSTRAINT);
                                                            }
                                                            if (!index.getColumns().contains(column)) {
                                                                index.getColumns().add(column);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
        return schema;
    }


}
