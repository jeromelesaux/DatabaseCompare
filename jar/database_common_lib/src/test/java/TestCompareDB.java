import fr.axione.dbcompare.analyse.ReportItem;
import fr.axione.dbcompare.common.dbitem.Schema;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jls on 20/01/2014.
 */


public class TestCompareDB  extends CreateDb {


    @Before
    public void createDbs() {
        initSchema();
    }

    @Test
    public void compareSameDb() {
        Assert.assertTrue ( schemaRight.equals(schemaLeft) );
    }

    @Test
    public void compareDifferentDb() {

        Assert.assertFalse(schemaRight.equals(schemaDiff) );

        for (ReportItem item : schemaRight.getErrors()) {
            System.out.println(item.getDescription());
        }
    }
}
