import fr.axione.dbcompare.analyse.ReportItem;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jls on 20/01/2014.
 */


public class TestCompareColumn  extends CreateDb {


    @Before
    public void createDbs() {
        initColumns();
    }

    @Test
    public void compareDifferentColumnsDb() {
        Assert.assertFalse ( schemaRight.equals(schemaLeft) );

        for (ReportItem item : schemaRight.getErrors()) {
            System.out.println(item.getDescription());
        }
    }

}
