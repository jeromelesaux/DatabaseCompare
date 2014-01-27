package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.model.common.ConstraintType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fr.axione.dbcompare.model.StringUtils.stringValueForBoolean;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Index extends Report {
    String name;
    Set<Column> columns;
    List<ConstraintType> types;
    Schema schema;

    public Index() {
      columns = new HashSet<Column>();
        types = new ArrayList<ConstraintType>();
    }
    public Index(Schema schema){
        this();
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

    public List<ConstraintType> getTypes() {
        return types;
    }

    public void setTypes(List<ConstraintType> types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object obj) {
        String objType = "Index";
        Boolean areEquals = true;
        if (obj == null ) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType + " : right index is absent."
            );
            getErrors().add(report);
            return false;
        }

        List<ConstraintType> types  = ((Index)obj).getTypes();
        for (ConstraintType type : types) {
            if (!this.types.contains(type)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType +" : has no constraint type attribut (" + String.valueOf(type) +",null)."
            );
            getErrors().add(report);
            areEquals = false;
        }
        }
        return areEquals;
    }
}
