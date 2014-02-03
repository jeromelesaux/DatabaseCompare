package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.model.common.ConstraintType;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by jlesaux on 20/01/14.
 */
public class Index extends Report implements Serializable{
    String name;
    Set<Column> columns;
    List<ConstraintType> types;
    Schema schema;
    String objectId;
    String seqName;
    String xmlFilePath;

    public Index() {
      columns = new HashSet<Column>();
        types = new ArrayList<ConstraintType>();
    }
    public Index(Schema schema){
        this();
        this.schema = schema;
    }

    public Index(String name,Schema schema) {
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

    public List<ConstraintType> getTypes() {
        return types;
    }

    public void setTypes(List<ConstraintType> types) {
        this.types = types;
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

    @Override
    public boolean equals(Object obj) {
        String objType = "Index";
        Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType + " : right index is absent."
            );
            getErrors().add(report);
            return false;
        }

        List<ConstraintType> types  = ((Index)obj).getTypes();
        for (ConstraintType type : types) {
            if (!this.types.contains(type)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType +" : has no constraint type attribut (" + String.valueOf(type) +",null)."
            );
            getErrors().add(report);
            areEquals = false;
        }
        }
        return areEquals;
    }
}
