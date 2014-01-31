import fr.axione.dbcompare.Tool.CmdLineTool;
import org.junit.Test;

/**
 * Created by jlesaux on 31/01/14.
 */
public class TestEmptyArgs {

    @Test
    public void testEmptyArgs() {
        CmdLineTool cmd = new CmdLineTool();
        cmd.main(new String[]{});
    }
}
