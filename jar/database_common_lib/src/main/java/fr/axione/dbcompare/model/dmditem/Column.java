package fr.axione.dbcompare.model.dmditem;

import fr.axione.dbcompare.model.common.ColumnType;

/**
 * Created by jlesaux on 22/01/14.
 */
public class Column {
    String name;
    Boolean nullable;
    Integer size;
    Boolean isPrimaryKey;
    Boolean isForeignKey;
    ColumnType type;
    Table table;
    String objectId;


    public Column() {
        nullable = false;
        isForeignKey= false;
        isPrimaryKey = false;
    }

    public Column(Table table) {
        this();
        this.table = table;
    }

    public Column(String columnName, Table table) {
        this(table);
        this.name = columnName;
    }

    public Boolean getIsForeignKey() {
        return isForeignKey;
    }

    public void setIsForeignKey(Boolean isForeignKey) {

        if (isForeignKey == true && table.columns.containsKey(name) ){
            table.foreignKeys.put(name, this);
        }
        else if (isForeignKey ==false && table.foreignKeys.containsKey(name)){
            table.foreignKeys.remove(name);
        }
        this.isForeignKey = isForeignKey;
    }

    public String getName() {
        return  name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }


    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        if (isPrimaryKey == true && table.columns.containsKey(name) ){
            table.primariesKeys.put(name,this);
        }
        else if (isPrimaryKey ==false && table.primariesKeys.containsKey(name)){
            table.primariesKeys.remove(name);
        }
        this.isPrimaryKey = isPrimaryKey;
    }

    public ColumnType getType() {
        return type;
    }

    public void setType(ColumnType type) {
        this.type = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
