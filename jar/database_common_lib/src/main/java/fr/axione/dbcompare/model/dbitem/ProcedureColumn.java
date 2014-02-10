package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.model.common.ProcedureColumnType;

import java.io.Serializable;

/**
 * Created by jlesaux on 10/02/14.
 */
public class ProcedureColumn extends Column implements Serializable {
    ProcedureColumnType procedureColumnType;
    String remark;


    public ProcedureColumnType getProcedureColumnType() {
        return procedureColumnType;
    }

    public void setProcedureColumnType(ProcedureColumnType procedureColumnType) {
        this.procedureColumnType = procedureColumnType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
