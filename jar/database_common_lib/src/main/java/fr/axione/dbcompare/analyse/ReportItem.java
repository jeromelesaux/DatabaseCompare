package fr.axione.dbcompare.analyse;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * Created by jlesaux on 20/01/14.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportItem implements Serializable {
    String dbItemLeft;
    String dbItemRight;
    Direction direction;
    String name;
    String description;
    ReportItemDBType dbType;

    public ReportItem() {

    }


    /**
     *
     * @return pivot object
     */
    public String getDbItemLeft() {
        return dbItemLeft;
    }

    public void setDbItemLeft(String dbItemLeft) {
        this.dbItemLeft = dbItemLeft;
    }

    public String getDbItemRight() {
        return dbItemRight;
    }

    public void setDbItemRight(String dbItemRight) {
        this.dbItemRight = dbItemRight;
    }

    public Direction getDirection(Direction plus) {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        String directionString = (direction == Direction.plus ? "+" : "-");
        return  directionString + " = " + description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportItem fillWithInformations(String objDbType,
                                           String rightDbItem,
                                           String leftDbItem,
                                           ReportItemDBType dbType,
                                           Direction directionError,
                                           String name,
                                           String description) {
        this.dbItemRight = rightDbItem;
        this.dbItemLeft = leftDbItem;
        this.dbType = dbType;
        this.direction = directionError;
        this.name = name;
        this.description = description;


        return this;
    }
}
