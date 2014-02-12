package fr.axione.dbcompare.model.sqlparser;

import java.util.List;

/**
 * Created by jlesaux on 12/02/14.
 */
public class SqlProcedureFunction {
    String name;
    List<SqlArgument> arguments;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SqlArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<SqlArgument> arguments) {
        this.arguments = arguments;
    }
}
