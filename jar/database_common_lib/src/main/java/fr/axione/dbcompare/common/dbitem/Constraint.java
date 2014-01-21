package fr.axione.dbcompare.common.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Constraint extends Report {
    String name;
    Column foreignColumn;
    Column primaryColumn;
    Schema schema;


    public Constraint() {

    }

    public Constraint(Schema schema){
        this.schema = schema;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Column getPrimaryColumn() {
        return primaryColumn;
    }

    public void setPrimaryColumn(Column primaryColumn) {
        this.primaryColumn = primaryColumn;
    }

    public Column getForeignColumn() {
        return foreignColumn;
    }

    public void setForeignColumn(Column foreignColumn) {
        this.foreignColumn = foreignColumn;
    }

    @Override
    public boolean equals(Object obj) {
        String objType = "Constraint:";
        Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType + " : right constraint is absent."
            );

            getErrors().add(report);
            return false;
        }



       return areEquals;
    }
}
