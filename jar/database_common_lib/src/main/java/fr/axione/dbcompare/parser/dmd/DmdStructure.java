package fr.axione.dbcompare.parser.dmd;


import fr.axione.dbcompare.model.StringUtils;
import fr.axione.dbcompare.model.common.ConstraintType;
import fr.axione.dbcompare.model.dbitem.*;
import fr.axione.dbcompare.model.dmditem.ColumnDmdTypeMapper;
import fr.axione.dbcompare.model.dmditem.DmdProjectConstants;
import fr.axione.dbcompare.model.sqlparser.SqlPackageParser;
import fr.axione.dbcompare.parser.DatabaseFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
    DatabaseFilter filter;


    public Schema getSchema(String xmlRootFilePath)  throws Exception {
        return getSchema(xmlRootFilePath,new DatabaseFilter());

    }

    public Schema getSchema(String xmlRootFilePath, DatabaseFilter filter) throws Exception {
        dmdConstants = new DmdProjectConstants(xmlRootFilePath);
        this.filter = filter;

        schema = new Schema();

        schema = parseObjectsLocalFile(schema);
        schema = parsePhysObjectsLocalFile(schema);

        return schema;
    }

    public Schema getSchemaName(Schema schema) throws IOException, SAXException, ParserConfigurationException {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(dmdConstants.getSchemaObjectFilePath());
        document.getDocumentElement().normalize();

        Element rootElement = document.getDocumentElement();
        NodeList childNodes= rootElement.getChildNodes();

        for (int i=0; i<childNodes.getLength(); i++){
            Element element = null;
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                element = (Element)childNodes.item(i);
                if ( element.getNodeName().equals("importConnectionStamps")) {
                    NodeList connectionStampsChild = element.getChildNodes();
                    for (int j = 0 ; j < connectionStampsChild.getLength(); j++) {
                        if ( connectionStampsChild.item(j).getNodeType() == Node.ELEMENT_NODE ) {
                            if (connectionStampsChild.item(j).getNodeName().equals("importConnectionStamp")) {
                                NodeList connectionStampChild = connectionStampsChild.item(j).getChildNodes();
                                for (int k = 0 ; k < connectionStampChild.getLength();k++) {
                                    if ( connectionStampChild.item(k).getNodeType() == Node.ELEMENT_NODE ) {
                                        if (connectionStampChild.item(k).getNodeName().equals("connUser")) {
                                            String schemaName = connectionStampChild.item(k).getFirstChild().getTextContent();
                                            schema.setName(schemaName);
                                            break;
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

    public Schema parseObjectsLocalFile(Schema schema)
            throws IOException, SAXException, ParserConfigurationException {
        schema = getSchemaName(schema);

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

                    if (objectType.equals("Table") && filter.tableMatchesRegex(name)) {
                        Table table = new Table(name,schema);
                        table.setObjectId(objectID);
                        table.setSeqName(seqName);
                        schema.getTables().put(table.getName(),table);
                    }
                    else if (objectType.equals("View") && filter.viewMatchesRegex(name)) {
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
                        index.getTypes().add(ConstraintType.FOREIGN_KEY);
                        schema.getIndexes().put(index.getName(),index);
                    }
//                    System.out.println(element.getNodeName() + " " + objectType + " " + objectID + " " + name + " " + seqName);
                }
            }
        }


        schema = getTables(schema);
        schema = getViews(schema);
//        schema = getForeignKey(schema);

        return schema;
    }


    protected Schema getForeignKey(Schema schema) throws ParserConfigurationException, IOException, SAXException {
             for (String key : schema.getIndexes().keySet()){
                 Index index = schema.getIndexes().get(key);
                 if (index.getTypes().contains(ConstraintType.FOREIGN_KEY)) {
                     String filePath = dmdConstants.getRelDirectoryPath() + File.separator + index.getXmlFilePath();
                     Document document = null;
                     DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                     DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                     document = documentBuilder.parse(filePath);
                     document.getDocumentElement().normalize();
                     Element rootElement = document.getDocumentElement();
                     NodeList childNodes= rootElement.getChildNodes();
                     for (int i=0; i<childNodes.getLength(); i++){
                         if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                             Element element = (Element)childNodes.item(i);
                             String tableObjectId = null;
                             if (element.getNodeName().equals("containerWithKeyObject")) {
                                 tableObjectId = element.getFirstChild().getTextContent();
                                 Table table = schema.getTableByObjectId(tableObjectId);
                                 System.out.println(tableObjectId);
                             }
                             else if (element.getNodeName().equals("keyObject"))  {
                                 String indexPk = element.getFirstChild().getTextContent();
                                 Index pkIndex = schema.getIndexByObjectId(indexPk);
                                 if (pkIndex != null) {

                                 }
                             }
                             else if (element.getNodeName().equals("localFKIndex")) {
                                 String indexFk = element.getFirstChild().getTextContent();
                                 Index fkIndex = schema.getIndexByObjectId(indexFk);
                                 if (fkIndex != null) {

                                 }
                             }
                         }
                     }
                 }
             }
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
                // parsing of the columns
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
                                if (filter.columnMatchesRegex(name)) {
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
                                            else if ( item.getNodeName().equals("dataTypePrecision")) {
                                                String size = item.getFirstChild().getTextContent();
                                                column.setSize(Integer.valueOf(size));
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
                }
                else if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals("indexes")) {
                    // parsing of the indexes
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

                                            // to debug
                                            //System.out.println("table : " + tableName + "."   + name + " got index type " + String.valueOf(type));

                                            index.getTypes().add(type != null ? type : ConstraintType.UNKNOWN);
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
                                                                index.getTypes().add(ConstraintType.PRIMARY_CONSTRAINT);
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

    protected Schema getViews(Schema schema) throws ParserConfigurationException, IOException, SAXException {

        for (String viewName : schema.getViews().keySet()){
            View view = schema.getViews().get(viewName);
            String filePath = dmdConstants.getRelDirectoryPath() + File.separator + view.getXmlFilePath();
            Document document = null;
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(filePath);
            document.getDocumentElement().normalize();
            Element rootElement = document.getDocumentElement();
            NodeList childNodes= rootElement.getChildNodes();

            for (int i=0; i<childNodes.getLength(); i++){
                Element element = null;
                // parsing of the columns
                if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE && childNodes.item(i).getNodeName().equals("viewElements")) {
                    NodeList columnsElements = childNodes.item(i).getChildNodes();
                    for (int j=0;j<columnsElements.getLength();j++){
                        Element columnElement = null;
                        if (columnsElements.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            columnElement = (Element)columnsElements.item(j);
                            if (columnElement.getNodeName().equals("viewElement")) {
                                String objectID = columnElement.getAttribute("objectId");
                                String name = columnElement.getAttribute("name");
                                Column column = new Column();
                                column.setName(name);
                                column.setObjectId(objectID);
                                if (filter.columnMatchesRegex(name)) {
                                    NodeList columnsAttribute = columnElement.getChildNodes();
                                    for (int h=0; h<columnsAttribute.getLength(); h++) {
                                        Node item = columnsAttribute.item(h);

                                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                                            if ( item.getNodeName().equals("dataType")) {
                                                String sqlType = item.getFirstChild().getTextContent();
                                                int size = 1;
                                                if (sqlType.contains("(")) {
                                                    String sizeValue = sqlType.replaceFirst(".*?\\((\\d+)(,\\s?\\d+)?(\\s?BYTE)?\\)$", "$1");
                                                    size = Integer.valueOf(sizeValue);
                                                }
                                                column.setType(ColumnDmdTypeMapper.getColumnType(sqlType));
                                                column.setSize(size);
                                            }
                                            else if ( item.getNodeName().equals("nullsAllowed")) {
                                                String isNullable = item.getFirstChild().getTextContent();
                                                column.setNullable(Boolean.valueOf(isNullable));
                                            }

                                        }
                                    }
                                    view.getColumns().put(column.getName(),column);
                                }
                            }
                        }
                    }
                }
            }
        }
        return schema;
    }


    protected Schema parsePhysObjectsLocalFile(Schema schema)
            throws IOException, SAXException, ParserConfigurationException  {
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(dmdConstants.getObjectsLocalPhysFilepath());
        document.getDocumentElement().normalize();

        Element rootElement = document.getDocumentElement();
        NodeList childNodes= rootElement.getChildNodes();

        for (int i=0; i<childNodes.getLength(); i++){
            Element element = null;
            // parsing of the columns
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE )  {
                element = (Element)childNodes.item(i);
                if (element.getNodeName().equals("object")) {
                    String objectType = element.getAttribute("objectType");
                    String objectID = element.getAttribute("objectID");
                    String name = element.getAttribute("name");
                    String seqName = element.getAttribute("seqName");
                    if (objectType.equals("Package")) {
                        Procedure procedure =getProcedure(seqName,objectID,objectType);
                        procedure.setName(name);
                        schema.getStoredProcedures().put(procedure.getName(),procedure);
                    }
                }
            }
        }



        return analyseSql(schema);
    }

    protected Schema  analyseSql(Schema schema) {
        for (String key : schema.getStoredProcedures().keySet()) {
            String sql = schema.getStoredProcedures().get(key).getSqlCode();
            SqlPackageParser parser = new SqlPackageParser(sql);
            parser.parse();
        }

        return schema;
    }

    protected Procedure getProcedure(String seqName, String filename, String type)
            throws ParserConfigurationException, IOException, SAXException {

        Procedure procedure = new Procedure(schema);
        String sourceCode = null;
        Document document = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(dmdConstants.getPhysDirectoryPath() + File.separator + type + File.separator + seqName + File.separator + filename + ".xml" );
        document.getDocumentElement().normalize();

        Element rootElement = document.getDocumentElement();
        NodeList childNodes= rootElement.getChildNodes();
        for (int i=0; i<childNodes.getLength(); i++){
            Element element = null;
            // parsing of the columns
            if (childNodes.item(i).getNodeType() == Node.ELEMENT_NODE )  {
                element = (Element)childNodes.item(i);
                if (element.getNodeName().equals("source")) {
                    sourceCode = element.getFirstChild().getTextContent();
                    sourceCode = StringUtils.unzerializedHtml(sourceCode);
                    procedure.setSqlCode(sourceCode);
                    procedure.setRemark(type);
                    break;
                }
            }
        }

        return procedure;
    }
}
