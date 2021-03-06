package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.analyse.ReportItemDBType;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jlesaux on 17/01/14.
 */
public class Table extends Report implements Serializable{
    String name;
    Schema schema;
    String objectId;
    String seqName;
    String xmlFilePath;
    HashMap<String,Column> columns;
    HashMap<String,Column> primariesKeys;
    HashMap<String,Index>  indexes;
    HashMap<String,Column> foreignKeys;



    public Table() {
        columns = new HashMap<String, Column>();
        primariesKeys = new HashMap<String, Column>();
        indexes = new HashMap<String, Index>();
        foreignKeys = new HashMap<String, Column>();
        xmlFilePath = null;
    }

    public Table(String tableName, Schema schema) {
        this(schema);
        this.name = tableName;
    }

    public Table(Schema schema) {
        this();
        this.schema = schema;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,Column> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String,Column> columns) {
        this.columns = columns;
    }

    public HashMap<String,Column> getPrimariesKeys() {
        return primariesKeys;
    }

    public void setPrimariesKeys(HashMap<String,Column> primariesKeys) {
        this.primariesKeys = primariesKeys;
    }

    public HashMap<String,Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(HashMap<String,Index> indexes) {
        this.indexes = indexes;
    }

    public HashMap<String,Column> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(HashMap<String,Column> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }


    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public String getXmlFilePath() {
        if (xmlFilePath == null) {
            if (objectId != null) {
                if (seqName != null ) {
                    xmlFilePath = "table" + File.separator + seqName + File.separator + objectId + ".xml";
                }
            }
        }
        return xmlFilePath;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public Column getColumnByObjectId(String columnObjectId){
        for (String columnName : this.columns.keySet()) {
            if ( this.columns.get(columnName).getObjectId().equals(columnObjectId) ) {
                return this.columns.get(columnName);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
       String objType = "Schema " + this.schema.getName() + " Table " + this.name;
       Boolean areEquals = true;

        if (obj == null ) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    null,
                    this.name,
                    ReportItemDBType.Table,
                    Direction.plus,
                    this.name,
                    objType + " : right table is absent."));
            return false;
        }

        Table rightTable = ((Table)obj);
        if (this.name != null && ! this.name.equals(rightTable.getName())) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    ((Table) obj).getName(),
                    this.name,
                    ReportItemDBType.Table,
                    Direction.plus,
                    this.name,
                    objType + " : has a different name attribut (" + this.name + "," + rightTable.getName() + ")."));
            areEquals = false;
        }

        for (String columnName : this.columns.keySet()) {
            Column leftColumn = this.columns.get(columnName);
            if (rightTable.getColumns().containsKey(columnName)) {
                if (leftColumn.equals(rightTable.getColumns().get(columnName))) {
                  // nothing to do just compare and store into reports object
                }
            }
            else {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        null,
                        columnName,
                        ReportItemDBType.Column,
                        Direction.plus,
                        this.name,
                        objType + " : right table as no foreignColumn (" + columnName+",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getColumns().keySet()) {
            if ( ! this.columns.containsKey(columnName) ) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        columnName,
                        null,
                        ReportItemDBType.Column,
                        Direction.minus,
                        this.name,
                        objType + " : left table as no foreignColumn (null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


        for (String columnName : this.primariesKeys.keySet()) {
            if (! rightTable.getPrimariesKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        null,
                        columnName,
                        ReportItemDBType.Primary_Key,
                        Direction.plus,
                        this.name,
                        objType + " : right foreignColumn is not a primary key  (" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }

        for (String columnName : rightTable.getPrimariesKeys().keySet()) {
            if (! this.primariesKeys.containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        columnName,
                        null,
                        ReportItemDBType.Primary_Key,
                        Direction.minus,
                        this.name,
                        objType + " : left foreignColumn is not a primary key  (null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }

        for (String columnName : this.indexes.keySet()) {
            if (! rightTable.getIndexes().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        null,
                        columnName,
                        ReportItemDBType.Index,
                        Direction.plus,
                        this.name,
                        objType + " : right foreignColumn is not in the index  "+ this.indexes.get(columnName).getName() +"(" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getIndexes().keySet()) {
            if (! this.getIndexes().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        columnName,
                        null,
                        ReportItemDBType.Index,
                        Direction.minus,
                        this.name,
                        objType + " : left foreignColumn is not in the index  "+ rightTable.getIndexes().get(columnName).getName() +"(null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


        for (String columnName : this.foreignKeys.keySet()) {
            if (! rightTable.getForeignKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        null,
                        columnName,
                        ReportItemDBType.Foreign_Key,
                        Direction.plus,
                        this.name,
                        objType + " : right foreignColumn is not a foreign key  "+ this.foreignKeys.get(columnName).getName() +"(" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getForeignKeys().keySet()) {
            if (! this.getForeignKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        columnName,
                        null,
                        ReportItemDBType.Foreign_Key,
                        Direction.minus,
                        this.name,
                        objType + " : left foreignColumn is not a foreign key  "+ rightTable.getForeignKeys().get(columnName).getName() +"(null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


//        for (String columnName : this.constraints.keySet()) {
//            if (! rightTable.getConstraints().containsKey(columnName)) {
//                ReportItem report = new ReportItem().fillWithInformations(
//                        objType,
//                        obj,
//                        this,
//                        Direction.plus,
//                        this.getClass().getName(),
//                        objType + " : right foreignColumn is not in a constraint  "+ this.constraints.get(columnName).getName() +"(" + columnName +",null)."
//                );
//                getErrors().add(report);
//                areEquals = false;
//            }
//        }
//        for (String columnName : rightTable.getConstraints().keySet()) {
//            if (! this.getConstraints().containsKey(columnName)) {
//                ReportItem report = new ReportItem().fillWithInformations(
//                        objType,
//                        obj,
//                        this,
//                        Direction.minus,
//                        this.getClass().getName(),
//                        objType + " : left foreignColumn is not a foreign key  "+ rightTable.getConstraints().get(columnName).getName() +"(null," + columnName +")."
//                );
//                getErrors().add(report);
//                areEquals = false;
//            }
//        }

        return areEquals;
    }


}
