package fr.axione.dbcompare.model.dmditem;

import java.util.HashMap;

/**
 * Created by jlesaux on 22/01/14.
 */
public class Schema {

    HashMap<String,Table> tables;
    HashMap<String,Index> indexes;
    HashMap<String,View> views;
    String name;


    public Schema() {
        tables = new HashMap<String, Table>();
        indexes = new HashMap<String, Index>();
        views = new HashMap<String, View>();
    }
    public Schema(String name) {
        this();
        this.name = name;
    }

    public HashMap<String, Table> getTables() {
        return tables;
    }

    public void setTables(HashMap<String, Table> tables) {
        this.tables = tables;
    }

    public HashMap<String, Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(HashMap<String, Index> indexes) {
        this.indexes = indexes;
    }

    public HashMap<String, View> getViews() {
        return views;
    }

    public void setViews(HashMap<String, View> views) {
        this.views = views;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
