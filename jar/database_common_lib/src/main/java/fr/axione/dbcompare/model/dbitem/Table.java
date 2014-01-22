package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;

import java.util.HashMap;

/**
 * Created by jlesaux on 17/01/14.
 */
public class Table extends Report{
    String name;
    Schema schema;
    HashMap<String,Column> columns;
    HashMap<String,Column> primariesKeys;
    HashMap<String,Index>  indexes;
    HashMap<String,Column> foreignKeys;
   // HashMap<String,Constraint> constraints;


    public Table() {
        columns = new HashMap<String, Column>();
        primariesKeys = new HashMap<String, Column>();
        indexes = new HashMap<String, Index>();
        foreignKeys = new HashMap<String, Column>();
//        constraints = new HashMap<String, Constraint>();
    }

    public Table(String tableName, Schema schema) {
        this(schema);
        this.name = tableName;
    }

    public Table(Schema schema) {
        this();
        this.schema = schema;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String,Column> getColumns() {
        return columns;
    }

    public void setColumns(HashMap<String,Column> columns) {
        this.columns = columns;
    }

    public HashMap<String,Column> getPrimariesKeys() {
        return primariesKeys;
    }

    public void setPrimariesKeys(HashMap<String,Column> primariesKeys) {
        this.primariesKeys = primariesKeys;
    }

    public HashMap<String,Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(HashMap<String,Index> indexes) {
        this.indexes = indexes;
    }

    public HashMap<String,Column> getForeignKeys() {
        return foreignKeys;
    }

    public void setForeignKeys(HashMap<String,Column> foreignKeys) {
        this.foreignKeys = foreignKeys;
    }

//    public HashMap<String,Constraint> getConstraints() {
//        return constraints;
//    }
//
//    public void setConstraints(HashMap<String,Constraint> constraints) {
//        this.constraints = constraints;
//    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    @Override
    public boolean equals(Object obj) {
       String objType = "Schema " + this.schema.getName() + " Table " + this.name;
       Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : right table is absent."));
            return false;
        }

        Table rightTable = ((Table)obj);
        if (this.name != null && ! this.name.equals(rightTable.getName())) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different name attribut (" + this.name + "," + rightTable.getName() + ")."));
            areEquals = false;
        }

        for (String columnName : this.columns.keySet()) {
            Column leftColumn = this.columns.get(columnName);
            if (rightTable.getColumns().containsKey(columnName)) {
                if (leftColumn.equals(rightTable.getColumns().get(columnName))) {
                  // nothing to do just compare and store into reports object
                }
            }
            else {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.plus,
                        this.getClass().getName(),
                        objType + " : right table as no foreignColumn (" + columnName+",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getColumns().keySet()) {
            if ( ! this.columns.containsKey(columnName) ) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.minus,
                        this.getClass().getName(),
                        objType + " : left table as no foreignColumn (null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


        for (String columnName : this.primariesKeys.keySet()) {
            if (! rightTable.getPrimariesKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.plus,
                        this.getClass().getName(),
                        objType + " : right foreignColumn is not a primary key  (" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }

        for (String columnName : rightTable.getPrimariesKeys().keySet()) {
            if (! this.primariesKeys.containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.minus,
                        this.getClass().getName(),
                        objType + " : left foreignColumn is not a primary key  (null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }

        for (String columnName : this.indexes.keySet()) {
            if (! rightTable.getIndexes().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.plus,
                        this.getClass().getName(),
                        objType + " : right foreignColumn is not in the index  "+ this.indexes.get(columnName).getName() +"(" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getIndexes().keySet()) {
            if (! this.getIndexes().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.minus,
                        this.getClass().getName(),
                        objType + " : left foreignColumn is not in the index  "+ rightTable.getIndexes().get(columnName).getName() +"(null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


        for (String columnName : this.foreignKeys.keySet()) {
            if (! rightTable.getForeignKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.plus,
                        this.getClass().getName(),
                        objType + " : right foreignColumn is not a foreign key  "+ this.foreignKeys.get(columnName).getName() +"(" + columnName +",null)."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }
        for (String columnName : rightTable.getForeignKeys().keySet()) {
            if (! this.getForeignKeys().containsKey(columnName)) {
                ReportItem report = new ReportItem().fillWithInformations(
                        objType,
                        obj,
                        this,
                        Direction.minus,
                        this.getClass().getName(),
                        objType + " : left foreignColumn is not a foreign key  "+ rightTable.getForeignKeys().get(columnName).getName() +"(null," + columnName +")."
                );
                getErrors().add(report);
                areEquals = false;
            }
        }


//        for (String columnName : this.constraints.keySet()) {
//            if (! rightTable.getConstraints().containsKey(columnName)) {
//                ReportItem report = new ReportItem().fillWithInformations(
//                        objType,
//                        obj,
//                        this,
//                        Direction.plus,
//                        this.getClass().getName(),
//                        objType + " : right foreignColumn is not in a constraint  "+ this.constraints.get(columnName).getName() +"(" + columnName +",null)."
//                );
//                getErrors().add(report);
//                areEquals = false;
//            }
//        }
//        for (String columnName : rightTable.getConstraints().keySet()) {
//            if (! this.getConstraints().containsKey(columnName)) {
//                ReportItem report = new ReportItem().fillWithInformations(
//                        objType,
//                        obj,
//                        this,
//                        Direction.minus,
//                        this.getClass().getName(),
//                        objType + " : left foreignColumn is not a foreign key  "+ rightTable.getConstraints().get(columnName).getName() +"(null," + columnName +")."
//                );
//                getErrors().add(report);
//                areEquals = false;
//            }
//        }

        return areEquals;
    }


}
