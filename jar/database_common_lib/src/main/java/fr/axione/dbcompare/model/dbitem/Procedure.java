package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.model.common.ProcedureType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jlesaux on 31/01/14.
 */
public class Procedure extends Report implements Serializable {
    String name;
    Schema schema;
    String catalogue;
    String remark;
    String sqlCode;
    ProcedureType procedureType;
    HashMap<String,ProcedureColumn> columns;


    public Procedure() {
        columns = new HashMap<String, ProcedureColumn>();
    }

    public Procedure(Schema schema) {
        this();
        this.schema = schema;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public HashMap<String, ProcedureColumn> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String, ProcedureColumn> columns) {
        this.columns = columns;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ProcedureType getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }

    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure that = (Procedure) o;

        if (catalogue != null ? !catalogue.equals(that.catalogue) : that.catalogue != null) return false;
        if (columns != null ? !columns.equals(that.columns) : that.columns != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (schema != null ? !schema.equals(that.schema) : that.schema != null) return false;

        return true;
    }


}
