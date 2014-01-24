import fr.axione.dbcompare.model.dbitem.Schema;
import fr.axione.dbcompare.parser.database.DatabaseStructure;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by jlesaux on 24/01/14.
 */
public class TestOracleDBStructure {
    Connection oracleConnection;

    @Before
    public void createConnection() throws SQLException, ClassNotFoundException {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE";
        String username = "AXIONEUSER_FACTU";
        String password = "AXIONEUSER_FACTU";
        Class.forName(driver);
        oracleConnection = DriverManager.getConnection(url, username, password);
    }


    @Test
    public void getStructure() throws SQLException {
        DatabaseStructure ds = new DatabaseStructure();
        Schema schema = ds.getSchema(oracleConnection);
    }
}
