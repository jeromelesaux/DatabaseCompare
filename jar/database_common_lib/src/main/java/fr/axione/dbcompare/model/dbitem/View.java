package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Report;

import java.io.File;

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

    public View() {

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
