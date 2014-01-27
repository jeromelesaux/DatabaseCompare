package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.model.common.ColumnType;

import static fr.axione.dbcompare.model.StringUtils.*;

/**
 * Created by jlesaux on 17/01/14.
 */
public class Column extends Report{
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

    @Override
    public boolean equals(Object obj) {
        String objType = "Schema " + table.getSchema().getName() +  " Table " + table.getName() + " Column " + this.name;
        Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem();
            getErrors().add(report.fillWithInformations(objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType + " : right table is absent."));
            return false;
        }

        String rightName = ((Column)obj).getName();
        if (this.name != null && ! this.name.equals(rightName)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different name attribut (" + this.name +","+ rightName + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }
        Boolean rightNullable = ((Column)obj).getNullable();
        if (this.nullable != null && ! this.nullable.equals(rightNullable)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different nullable attribut (" + stringValueForBoolean(this.nullable) + ","+ stringValueForBoolean(rightNullable) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }

       Boolean rightIsPrimaryKey = ((Column) obj).getIsPrimaryKey();
        if (this.isPrimaryKey != null && ! this.isPrimaryKey.equals(rightIsPrimaryKey)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different primary key attribut ("+ stringValueForBoolean(this.isPrimaryKey)  +","+ stringValueForBoolean(rightIsPrimaryKey) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }

        Boolean rightIsForeignKey = ((Column)obj).getIsForeignKey();
        if (this.isForeignKey != null && ! this.isForeignKey.equals(rightIsForeignKey)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different primary key attribut (" + stringValueForBoolean(this.isForeignKey)+ ","+ stringValueForBoolean(rightIsForeignKey) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }

        ColumnType rightColumnType  = ((Column)obj).getType();
        if (this.type != null && ! this.type.equals(rightColumnType)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different type attribut ("+ String.valueOf(this.type) +","+ String.valueOf(rightColumnType) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }

        Integer rightSize = ((Column)obj).getSize();
        if (this.size != null && ! this.size.equals(rightSize)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.minus,
                    this.getClass().getName(),
                    objType + " : has a different type size  ("+ String.valueOf(this.size) +","+ String.valueOf(rightSize) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }


        return areEquals;
    }
}
