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

    @Test()
    public void testCmdLineDmdFileConnectionDB() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(), "-T", "conn", "-u", "AXIONEUSER_FACTU", "-p", "AXIONEUSER_FACTU", "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE"});

    }
    @Test
    public void testCmdLineDmdFileDmdFile() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(), "-T", "dmd", "-f", xmlPath.getPath()});

    }
    @Test()
    public void testCmdLineConnectionDBDmdFile() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "conn", "-u", "AXIONEUSER_FACTU", "-p", "AXIONEUSER_FACTU", "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE","-T", "dmd", "-f", xmlPath.getPath()});

    }
    @Test()
    public void testCmdLineConnectionDBConnectionDB() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "conn", "-u", "AXIONEUSER_FACTU", "-p", "AXIONEUSER_FACTU", "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE","-T", "conn", "-u", "AXIONEUSER_FACTU", "-p", "AXIONEUSER_FACTU", "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE"});

    }
    @Test()
    public void testCmdLineConnectionDBConnectionDBWithoutAXIONEUSER_FACTU() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(1);
        testCmdLineTool.main(new String[]{"-T", "conn", "-u", "AXIONEUSER_FACTU", "-p",  "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE","-T", "conn", "-u", "AXIONEUSER_FACTU", "-p", "AXIONEUSER_FACTU", "-c", "jdbc:oracle:thin:@10.1.88.57:1521:AXIONE"});

    }
    @Test()
    public void testCmdLineTooManyArgs() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(2);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath()});

    }



}
