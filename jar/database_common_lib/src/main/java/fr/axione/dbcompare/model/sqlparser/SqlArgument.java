package fr.axione.dbcompare.model.sqlparser;

import fr.axione.dbcompare.model.common.ColumnType;
import fr.axione.dbcompare.model.common.ProcedureColumnType;

/**
 * Created by jlesaux on 12/02/14.
 */
public class SqlArgument {
    String name;
    ColumnType type;
    ProcedureColumnType argType;
    Integer size;
    String columnPointer;    // when %ROWTYPE keyword found.

    public SqlArgument(String name) {
        this.name = name;
    }

    public SqlArgument(String name, ColumnType type) {
        this(name);
        this.type = type;
    }

    public SqlArgument(String name, ColumnType type, Integer size) {
        this(name,type);
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getColumnPointer() {
        return columnPointer;
    }

    public void setColumnPointer(String columnPointer) {
        this.columnPointer = columnPointer;
    }

    public ProcedureColumnType getArgType() {
        return argType;
    }

    public void setArgType(ProcedureColumnType argType) {
        this.argType = argType;
    }
}
