package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptRemoveInexistantPackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandAptRemoveTest {
    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("apcalc");
        assertTrue(c.execute() instanceof String);
    }


    @Test(expected = ExceptionAptRemoveInexistantPackage.class)
    public void executeFailure() throws Exception {
        CommandAptShow c = new CommandAptShow("fuufeaofjeej");
        c.execute();
    }
}
