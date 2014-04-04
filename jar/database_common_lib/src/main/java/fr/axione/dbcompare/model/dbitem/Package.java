package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.analyse.ReportItemDBType;
import fr.axione.dbcompare.model.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 13/02/14.
 */
public class Package extends Report implements Serializable {
    String name;
    String sqlCode;
    Schema schema;
    List<Procedure> procedures;
    Boolean sqlCodeLoaded;

    public Package() {
        sqlCodeLoaded = true;
        procedures = new ArrayList<Procedure>();
    }
    public Package(Schema schema){
        this();
        this.schema= schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public Boolean getSqlCodeLoaded() {
        return sqlCodeLoaded;
    }

    public void setSqlCodeLoaded(Boolean sqlCodeLoaded) {
        this.sqlCodeLoaded = sqlCodeLoaded;
    }

    @Override
    public boolean equals(Object o) {
        String objType = "Package " + this.name;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Package that = (Package)o;
        if (sqlCodeLoaded && that.getSqlCodeLoaded() == true) {
            String rightCode = StringUtils.cleanString(that.getSqlCode());
            rightCode = rightCode.replaceAll(that.getSchema().getName().toUpperCase() + ".","");
            String leftCode = StringUtils.cleanString(this.sqlCode);
            leftCode = leftCode.replaceAll(this.getSchema().getName().toUpperCase() + ".","");
            if (! leftCode.equals(rightCode)) {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        null,
                        null,
                        ReportItemDBType.Package,
                        Direction.minus,
                        this.name,
                        objType + " : left sql code is different from right."));
            }
        }

        return true;
    }
}
