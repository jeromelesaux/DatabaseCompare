package fr.axione.dbcompare.analyse;

import fr.axione.dbcompare.model.dbitem.Schema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Report {
    List<ReportItem> errors;
    Schema schema;

    /**
     *
     */
    public Report() {
        errors = new ArrayList<ReportItem>();
    }

    /**
     *
     * @return collection of Report Items
     */
    public List<ReportItem> getErrors() {
        return errors;
    }

    /**
     *
     * @param errors collection of Report Items
     */
    public void setErrors(List<ReportItem> errors) {
        this.errors = errors;
    }
}
