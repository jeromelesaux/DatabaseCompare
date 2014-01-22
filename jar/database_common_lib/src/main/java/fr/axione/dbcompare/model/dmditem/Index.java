package fr.axione.dbcompare.model.dmditem;

import fr.axione.dbcompare.model.common.ConstraintType;

import java.util.Set;

/**
 * Created by jlesaux on 22/01/14.
 */
public class Index {
    String name;
    Set<Column> columns;
    ConstraintType type;
    Schema schema;
    String id;


    public Index() {

    }
    public Index(Schema schema){
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public void setColumns(Set<Column> columns) {
        this.columns = columns;
    }

    public ConstraintType getType() {
        return type;
    }

    public void setType(ConstraintType type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
