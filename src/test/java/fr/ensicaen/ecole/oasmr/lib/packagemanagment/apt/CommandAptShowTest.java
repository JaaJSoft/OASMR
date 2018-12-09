package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptPackageNotFound;
import org.junit.Test;
import static org.junit.Assert.*;

/*ToDo: Complete this test: how check efficiently if we can pass the test?? */

public class CommandAptShowTest {

    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("gcc");
        assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }

    @Test(expected = ExceptionAptPackageNotFound.class)
    public void executeFailure() throws Exception{
        CommandAptShow c = new CommandAptShow("jaaj");
        c.execute();
    }
}
