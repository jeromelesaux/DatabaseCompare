package fr.axione.dbcompare.analyse;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * Created by jlesaux on 20/01/14.
 */
@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportItem implements Serializable {
    @XmlTransient
    Object dbItemLeft;
    @XmlTransient
    Object dbItemRight;
    Direction direction;
    String name;
    String description;

    public ReportItem() {

    }


    /**
     *
     * @return pivot object
     */
    public Object getDbItemLeft() {
        return dbItemLeft;
    }

    public void setDbItemLeft(Object dbItemLeft) {
        this.dbItemLeft = dbItemLeft;
    }

    public Object getDbItemRight() {
        return dbItemRight;
    }

    public void setDbItemRight(Object dbItemRight) {
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
                                           Object rightDbItem,
                                           Object leftDbItem,
                                           Direction directionError,
                                           String name,
                                           String description) {
        this.dbItemRight = rightDbItem;
        this.dbItemLeft = leftDbItem;
        this.direction = directionError;
        this.name = name;
        this.description = description;


        return this;
    }
}
