package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.model.common.ConstraintType;

import java.util.Set;

import static fr.axione.dbcompare.model.StringUtils.stringValueForBoolean;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Index extends Report {
    String name;
    Set<Column> columns;
    ConstraintType  type;
    Schema schema;

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

        ConstraintType rightIsUnique = ((Index)obj).getType();
        if (this.type != null && ! this.type.equals(rightIsUnique)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType +" : has a different constraint type attribut (" + String.valueOf(this.type) +","+ String.valueOf(rightIsUnique) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }
        return areEquals;
    }
}
