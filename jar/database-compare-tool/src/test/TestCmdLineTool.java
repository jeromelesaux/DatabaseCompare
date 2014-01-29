import com.martiansoftware.jsap.JSAPException;
import fr.axione.dbcompare.Tool.CmdLineTool;
import junit.framework.Assert;
import org.junit.Test;

/**
 * Created by jlesaux on 29/01/14.
 */
public class TestCmdLineTool {
    @Test
    public void testCmdLine() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        try {
             testCmdLineTool.main(new String[]{"-T", "dmd", "-f", "toot", "-T", "conn", "-u", "userdb", "-p", "password", "-c", "connectionUrl"});
        } catch (JSAPException e) {
            e.printStackTrace();
        }
    }
}
