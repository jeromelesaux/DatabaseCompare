package fr.axione.dbcompare.model.dmditem;

import java.io.File;
import java.util.HashMap;

/**
 * Created by jlesaux on 22/01/14.
 */
public class Table {
    String objectId;
    String name;
    String seqName;
    String xmlFilePath;
    HashMap<String,Column> columns;
    HashMap<String,Column> primariesKeys;
    HashMap<String,Index>  indexes;
    HashMap<String,Column> foreignKeys;

    Schema schema;


    public Table() {
        columns = new HashMap<String, Column>();
        primariesKeys = new HashMap<String, Column>();
        indexes = new HashMap<String, Index>();
        foreignKeys = new HashMap<String, Column>();
        xmlFilePath = null;
    }
    public Table(Schema schema){
        this();
        this.schema = schema;
    }

    public Table(String name, Schema schema){
        this(schema);
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                    xmlFilePath = "Table" + File.separator + seqName + File.separator + objectId + ".xml";
                }
            }
        }
        return xmlFilePath;
    }

    public void setXmlFilePath(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
    }

    public HashMap<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String, Column> columns) {
        this.columns = columns;
    }

    public HashMap<String, Column> getPrimariesKeys() {
        return primariesKeys;
    }

    public void setPrimariesKeys(HashMap<String, Column> primariesKeys) {
        this.primariesKeys = primariesKeys;
    }

    public HashMap<String, Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(HashMap<String, Index> indexes) {
        this.indexes = indexes;
    }

    public HashMap<String, Column> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(HashMap<String, Column> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }
}
