package fr.axione.dbcompare.model.sqlparser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 12/02/14.
 */
public class SqlPackage {
    String name;
    String catalogue;
    List<SqlProcedureFunction> procedures;
    List<SqlProcedureFunction> functions;

    public SqlPackage() {
        procedures = new ArrayList<SqlProcedureFunction>();
        functions = new ArrayList<SqlProcedureFunction>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalogue() {
        return catalogue;
    }

    public void setCatalogue(String catalogue) {
        this.catalogue = catalogue;
    }

    public List<SqlProcedureFunction> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<SqlProcedureFunction> procedures) {
        this.procedures = procedures;
    }

    public List<SqlProcedureFunction> getFunctions() {
        return functions;
    }

    public void setFunctions(List<SqlProcedureFunction> functions) {
        this.functions = functions;
    }
}
