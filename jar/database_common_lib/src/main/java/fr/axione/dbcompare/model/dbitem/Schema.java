package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.analyse.ReportItemDBType;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Schema extends Report implements Serializable {

    String name;
    String catalog;
    HashMap<String, Table> tables;
    HashMap<String, Trigger> triggers;
    HashMap<String,Index> indexes;
    HashMap<String, View> views;
    HashMap<String, Procedure> storedProcedures;
    //HashMap<String, Constraint> constraints;

    public Schema() {
        tables = new HashMap<String, Table>();
        triggers = new HashMap<String, Trigger>();
        views = new HashMap<String, View>();
        indexes = new HashMap<String, Index>();
        storedProcedures = new HashMap<String, Procedure>();

       // constraints = new HashMap<String, Constraint>();

    }

    public Schema(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Table> getTables() {
        return tables;
    }

    public void setTables(HashMap<String, Table> tables) {
        this.tables = tables;
    }

    public HashMap<String, Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(HashMap<String, Trigger> triggers) {
        this.triggers = triggers;
    }

    public HashMap<String, View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, View> views) {
        this.views = views;
    }
    public HashMap<String, Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(HashMap<String, Index> indexes) {
        this.indexes = indexes;
    }


//    public HashMap<String, Constraint> getConstraints() {
//        return constraints;
//    }
//
//    public void setConstraints(HashMap<String, Constraint> constraints) {
//        this.constraints = constraints;
//    }


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }


    public HashMap<String, Procedure> getStoredProcedures() {
        return storedProcedures;
    }

    public void setStoredProcedures(HashMap<String, Procedure> storedProcedures) {
        this.storedProcedures = storedProcedures;
    }

    public Index getIndexByObjectId(String objectId) {
        for (String name : this.indexes.keySet()) {
            if ( indexes.get(name).getObjectId().equals(objectId)) {
                return indexes.get(name);
            }
        }
        return null;
    }


    public Table getTableByObjectId(String objectId) {
        for (String name : this.tables.keySet()) {
            if ( tables.get(name).getObjectId().equals(objectId)) {
                return tables.get(name);
            }
        }
        return null;
    }
    public Column getColumnByObjectId(String objectId) {
        Column column;
        for (String tableName : this.tables.keySet()) {
            column = this.tables.get(tableName).getColumnByObjectId(objectId);
            if (column != null) {
                return column;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return "Schema : " + name;
    }





    @Override
    public boolean equals(Object obj) {
        String objType = "Schema " + this.name;
        Boolean areEquals = true;

        if (obj == null ) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    null,
                    this.name,
                    ReportItemDBType.Schema,
                    Direction.plus,
                    this.name,
                    objType + " : right schema is absent."));
            return false;
        }

        Schema rightSchema = (Schema)obj;

        if (this.name != null && ! this.name.equals(rightSchema.getName())) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    rightSchema.getName(),
                    this.name,
                    ReportItemDBType.Schema,
                    Direction.plus,
                    this.name,
                    objType + " : right schema has a different name (" + this.name + ","+ rightSchema.getName()+")."));
            areEquals = false;
        }

        for (String key : this.getTables().keySet()) {
            if ( rightSchema.getTables().containsKey(key) ) {
                Table rightTable = rightSchema.getTables().get(key);
                Table leftTable = this.getTables().get(key);
                if (leftTable.equals(rightTable)) {
                    // just to compare
                }
            }
            else {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        null,
                        key,
                        ReportItemDBType.Table,
                        Direction.plus,
                        this.name,
                        objType + " : right schema as no table (" + key +",null)."));
                areEquals = false;
            }
        }

        for (String key : rightSchema.getTables().keySet()){
            if (!this.getTables().containsKey(key)) {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        key,
                        null,
                        ReportItemDBType.Table,
                        Direction.minus,
                        this.name,
                        objType + " : left schema as no table (null,"+key+")."));
                areEquals = false;
            }
        }


        for (String key : this.getViews().keySet()) {
            if ( rightSchema.getViews().containsKey(key) ) {
                View rightView = rightSchema.getViews().get(key);
                View leftView = this.getViews().get(key);
                if (leftView.equals(rightView)) {
                    // just to compare
                }
            }
            else {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        null,
                        key,
                        ReportItemDBType.View,
                        Direction.plus,
                        this.name,
                        objType + " : right schema as no view (" + key +",null)."));
                areEquals = false;
            }
        }

        for (String key : rightSchema.getViews().keySet()){
            if (!this.getViews().containsKey(key)) {
                ReportItem report = new ReportItem();
                getErrors().add(report.fillWithInformations(objType,
                        key,
                        null,
                        ReportItemDBType.Table,
                        Direction.minus,
                        this.name,
                        objType + " : left schema as no view (null,"+key+")."));
                areEquals = false;
            }
        }



        // gathering all errors from all objects

        for (String tableKey : this.getTables().keySet()) {
            Table table = this.getTables().get(tableKey);
            getErrors().addAll(table.getErrors());

            for (String columnKey : table.getColumns().keySet()) {
                Column column = table.getColumns().get(columnKey);
                getErrors().addAll(column.getErrors());
            }
        }

        for (String tableKey : rightSchema.getTables().keySet()) {
            Table table = rightSchema.getTables().get(tableKey);
            getErrors().addAll(table.getErrors());

            for (String columnKey : table.getColumns().keySet()) {
                Column column = table.getColumns().get(columnKey);
                getErrors().addAll(column.getErrors());
            }
        }



        return areEquals;

    }
}
