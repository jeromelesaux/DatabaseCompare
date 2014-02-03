package fr.axione.dbcompare.analyse;

import fr.axione.dbcompare.model.dbitem.Schema;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jlesaux on 20/01/14.
 */


@XmlRootElement(name="Report")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report implements Serializable {

    @XmlElement(name="ReportItem")
    List<ReportItem> errors;
    @XmlTransient
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
