package fr.axione.dbcompare.model.dmditem;

import fr.axione.dbcompare.model.common.ConstraintType;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jlesaux on 22/01/14.
 */
public class Index {
    String name;
    Set<Column> columns;
    ConstraintType type;
    Schema schema;
    String objectId;
    String seqName;
    String xmlFilePath;


    public Index() {
       columns = new HashSet<Column>();
        type = ConstraintType.UNKNOWN;
    }
    public Index(Schema schema){
        this();
        this.schema = schema;
    }
    public Index(String name, Schema schema) {
        this(schema);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
                    xmlFilePath = "foreignkey" + File.separator + seqName + File.separator + objectId + ".xml";
                }
            }
        }
        return xmlFilePath;
    }
}
