package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/*ToDo: Complete this test: how check efficiently if we can pass the test?? */

public class CommandAptShowTest {
    private CommandAptShow c;

    @Before
    public  void setUp(){
        c = new CommandAptShow("gcc");
    }

    @Test
    public void execute() throws Exception{
        assertEquals(""/*To complete*/, c.execute());
    }

    @Test
    public void executeFailure() throws Exception{
        assertNotEquals(""/*To complete*/, c.execute());
    }
}
