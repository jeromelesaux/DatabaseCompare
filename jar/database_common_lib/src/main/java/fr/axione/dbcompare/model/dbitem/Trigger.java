package fr.axione.dbcompare.model.dbitem;

import fr.axione.dbcompare.analyse.Report;

import java.io.Serializable;

/**
 * Created by jlesaux on 20/01/14.
 */
public class Trigger extends Report implements Serializable {
    String name;
    String codeSql;


    public Trigger() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCodeSql() {
        return codeSql;
    }

    public void setCodeSql(String codeSql) {
        this.codeSql = codeSql;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
