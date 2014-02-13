package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.ReportItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 13/02/14.
 */
public class Package extends ReportItem {
    String name;
    String sqlCode;
    Schema schema;
    List<Procedure> procedures;

    public Package() {
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
}
