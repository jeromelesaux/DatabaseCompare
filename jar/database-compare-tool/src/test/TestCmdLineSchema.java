import fr.axione.dbcompare.Tool.CmdLineTool;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.net.URL;

/**
 * Created by jlesaux on 30/01/14.
 */
public class TestCmdLineSchema {


    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    public URL xmlPath = this.getClass().getResource("/testDmd.dmd");


    @Test
    public void testCmdLineDmdFileDmdFile() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(0);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(), "-T", "dmd", "-f", xmlPath.getPath()});

    }

    @Test()
    public void testCmdLineConnectionDBConnectionDBWithoutUserU() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(1);
        testCmdLineTool.main(new String[]{"-T", "conn", "-u", "userdb", "-p",  "-c", "jdbc:oracle:thin:@127.0.0.11521:USERDB","-T", "conn", "-u", "userDB", "-p", "USERDB", "-c", "jdbc:oracle:thin:@127.0.0.1:1521:UserDB"});

    }
    @Test()
    public void testCmdLineTooManyArgs() {
        CmdLineTool testCmdLineTool = new CmdLineTool();
        exit.expectSystemExitWithStatus(2);
        testCmdLineTool.main(new String[]{"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath(),"-T", "dmd", "-f", xmlPath.getPath()});

    }





}
