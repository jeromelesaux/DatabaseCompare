package fr.axione.dbcompare.Tool;

import fr.axione.dbcompare.model.dbitem.Schema;
import fr.axione.dbcompare.parser.database.DatabaseStructure;
import fr.axione.dbcompare.parser.dmd.DmdStructure;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by jlesaux on 30/01/14.
 */
public class CmdLineSchema {

    Schema schema;
    String dmdFilepath;
    String url;
    String user;
    String password;
    DBTYPE dbtype;
    CMDLINETYPE cmdlinetype;



    public CmdLineSchema() {

    }


    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public String getDmdFilepath() {
        return dmdFilepath;
    }

    public void setDmdFilepath(String dmdFilepath) {
        this.dmdFilepath = dmdFilepath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DBTYPE getDbtype() {
        return dbtype;
    }

    public void setDbtype(DBTYPE dbtype) {
        this.dbtype = dbtype;
    }

    public CMDLINETYPE getCmdlinetype() {
        return cmdlinetype;
    }

    public void setCmdlinetype(CMDLINETYPE cmdlinetype) {
        this.cmdlinetype = cmdlinetype;
    }


    public void parseSchema() throws Exception {
        schema = new Schema();
        if (cmdlinetype == CMDLINETYPE.DMDFILE ) {
            DmdStructure dmdStructure = new DmdStructure();
            schema = dmdStructure.getSchema(dmdFilepath);
        }
        else {
            DatabaseStructure databaseStructure = new DatabaseStructure();
            String driver;
            Connection connection;
            if (url.toUpperCase().contains("ORACLE")) {
                dbtype = DBTYPE.ORACLE;
                driver = "oracle.jdbc.driver.OracleDriver";
            }
            else if (url.toUpperCase().contains("MYSQL")) {
                dbtype = DBTYPE.MYSQL;
                driver = "com.mysql.jdbc.Driver";
            }
            else {
                throw new Exception("Not yet implemented for driver " + url);
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
            schema = databaseStructure.getSchema(connection);
        }
    }


    @Override
    public String toString() {
        return "schema:"+(schema.getName()==null?"unknown":schema.getName())+",type:"+String.valueOf(cmdlinetype);
    }
}
