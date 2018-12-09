package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptRemoveInexistantPackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptRemoveTest {
    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("apcalc");
        assertEquals(""/*To complete*/, c.execute());
    }


    @Test(expected = ExceptionAptRemoveInexistantPackage.class)
    public void executeFailure() throws ExceptionAptRemoveInexistantPackage, Exception{
        CommandAptShow c = new CommandAptShow("fuufeaofjeej");
    }

}
