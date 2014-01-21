package fr.axione.dbcompare.common.dbitem;

import fr.axione.dbcompare.analyse.Direction;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;

import java.util.Set;

import static fr.axione.dbcompare.common.StringUtils.stringValueForBoolean;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Index extends Report {
    String name;
    Set<Column> columns;
    Boolean unique;
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

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
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

        Boolean rightIsUnique = ((Index)obj).getUnique();
        if (this.unique != null && ! this.unique.equals(rightIsUnique)) {
            ReportItem report = new ReportItem().fillWithInformations(
                    objType,
                    obj,
                    this,
                    Direction.plus,
                    this.getClass().getName(),
                    objType +" : has a different unique attribut (" + stringValueForBoolean(this.unique) +","+ stringValueForBoolean(rightIsUnique) + ")."
            );
            getErrors().add(report);
            areEquals = false;
        }
        return areEquals;
    }
}
