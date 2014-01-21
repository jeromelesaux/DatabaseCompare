import fr.axione.dbcompare.analyse.ReportItem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jls on 20/01/2014.
 */
public class TestCompareTable extends CreateDb {

    @Before
    public void createTables() {
        initTables();
    }

    @Test
    public void compareTables() {
        Assert.assertFalse(schemaLeft.equals(schemaRight));

        for (ReportItem item : schemaLeft.getErrors()) {
            System.out.println(item.getDescription());
        }

    }

}
