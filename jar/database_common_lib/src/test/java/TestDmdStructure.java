import fr.axione.dbcompare.model.dmditem.Schema;
import fr.axione.dbcompare.parser.dmd.DmdStructure;
import org.junit.Test;

import java.net.URL;

/**
 * Created by jlesaux on 23/01/14.
 */

public class TestDmdStructure {
    DmdStructure dmdStructure;

    @Test
    public void loadDmdFile() throws Exception {
        URL xmlPath = this.getClass().getResource("/testDmd.xml");
        dmdStructure = new DmdStructure();
        Schema schema = dmdStructure.getSchema(xmlPath.getFile());

    }
}
