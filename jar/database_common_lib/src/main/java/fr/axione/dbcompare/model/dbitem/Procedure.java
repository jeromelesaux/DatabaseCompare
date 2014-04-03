package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.analyse.ReportItemDBType;
import fr.axione.dbcompare.model.common.PackageItemType;
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
    String bodySsqlCode;
    ProcedureType procedureType;
    PackageItemType packageItemType;
    HashMap<String,ProcedureColumn> columns;
    Boolean sqlCodeLoaded;


    public Procedure() {
        sqlCodeLoaded = false;
        columns = new HashMap<String, ProcedureColumn>();
    }

    public Procedure(Schema schema) {
        this();
        this.schema = schema;
    }

    public Boolean getSqlCodeLoaded() {
        return sqlCodeLoaded;
    }

    public void setSqlCodeLoaded(Boolean sqlCodeLoaded) {
        this.sqlCodeLoaded = sqlCodeLoaded;
    }

    public PackageItemType getPackageItemType() {
        return packageItemType;
    }

    public void setPackageItemType(PackageItemType packageItemType) {
        this.packageItemType = packageItemType;
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

    public String getBodySsqlCode() {
        return bodySsqlCode;
    }

    public void setBodySsqlCode(String bodySsqlCode) {
        this.bodySsqlCode = bodySsqlCode;
    }

    @Override
    public boolean equals(Object o) {
        String objType = "Procedure " + this.name;
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Procedure that = (Procedure) o;

        if (catalogue != null ? !catalogue.equals(that.catalogue) : that.catalogue != null) return false;
        if (columns != null ? !columns.equals(that.columns) : that.columns != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (schema != null ? !schema.equals(that.schema) : that.schema != null) return false;
        if (sqlCodeLoaded && that.getSqlCodeLoaded() == true) {
            if (! this.bodySsqlCode.equals(((Procedure) o).getBodySsqlCode())) {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        null,
                        null,
                        ReportItemDBType.Procedure,
                        Direction.minus,
                        this.name,
                        objType + " : left sql code is different from right."));
            }
        }

        return true;
    }


}
