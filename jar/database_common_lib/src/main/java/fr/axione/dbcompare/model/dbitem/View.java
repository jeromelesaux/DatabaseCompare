package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.analyse.ReportItemDBType;

import java.io.File;
import java.util.HashMap;

/**
 * Created by jlesaux on 20/01/14.
 */
public class View extends Report {
    String name;
    String codeSql;
    Schema schema;
    String objectId;
    String seqName;
    String xmlFilePath;
    HashMap<String,Column> columns;

    public View() {
        columns = new HashMap<String, Column>();

    }
    public View(Schema schema) {
        this();
        this.schema = schema;
    }
    public View(String name, Schema schema) {
        this(schema);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeSql() {
        return codeSql;
    }

    public void setCodeSql(String codeSql) {
        this.codeSql = codeSql;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
                    xmlFilePath = "view" + File.separator + seqName + File.separator + objectId + ".xml";
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

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object obj) {
        String objType = "Schema " + this.schema.getName() + " View " + this.name;
        Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    null,
                    this.name,
                    ReportItemDBType.View,
                    Direction.plus,
                    this.name,
                    objType + " : right table is absent."));
            return false;
        }

        View rightView = ((View)obj);
        if (this.name != null && ! this.name.equals(rightView.getName())) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    rightView.getName(),
                    this.name,
                    ReportItemDBType.View,
                    Direction.plus,
                    this.name,
                    objType + " : has a different name attribut (" + this.name + "," + rightView.getName() + ")."));
            areEquals = false;
        }

        for (String columnName : this.columns.keySet()) {
            Column leftColumn = this.columns.get(columnName);
            if (rightView.getColumns().containsKey(columnName)) {
                if (leftColumn.equals(rightView.getColumns().get(columnName))) {
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
        for (String columnName : rightView.getColumns().keySet()) {
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

        return areEquals;
    }
}
