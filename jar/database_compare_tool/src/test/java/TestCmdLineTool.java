
import fr.axione.dbcompare.Tool.CmdLineTool;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.net.URL;

/**
 * Created by jlesaux on 29/01/14.
 */
public class TestCmdLineTool {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    public URL xmlPath = this.getClass().getResource("/testDmd.dmd");

   
    @Test
    public void testCmdLineDmdFileDmdFile() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(), "-T", "dmd", "-f", xmlPath.getPath()});

    }

    @Test
    public void testCmdLineDmdFileDmdFileXmlOutput() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(), "-T", "dmd", "-f", xmlPath.getPath(),"-o","xml"});

    }

    @Test()
    public void testCmdLineConnectionDBConnectionDBWithoutUser() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(1);
        testCmdLineTool.main(new String[]{"-T", "conn", "-u", "user", "-p",  "-c", "jdbc:oracle:thin:@127.0.0.1:1521:User","-T", "conn", "-u", "User", "-p", "pass", "-c", "jdbc:oracle:thin:@127.0.0.1:1521:user"});

    }
    @Test()
    public void testCmdLineTooManyArgs() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(2);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath()});

    }



}
