import fr.axione.dbcompare.model.dbitem.Schema;
import fr.axione.dbcompare.parser.database.DatabaseStructure;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jlesaux on 21/01/14.
 */

public class TestMysqlDBStructure {
    Connection mysqlConnection;

    @Before
    public void createConnection() throws SQLException, ClassNotFoundException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/mydb";
        String username = "javauser";
        String password = "password";
        Class.forName(driver);
        mysqlConnection = DriverManager.getConnection(url,username,password);
    }


    @Test
    public void getStructure() throws SQLException {
        DatabaseStructure ds = new DatabaseStructure();
        Schema schema = ds.getSchema(mysqlConnection);
    }
}
