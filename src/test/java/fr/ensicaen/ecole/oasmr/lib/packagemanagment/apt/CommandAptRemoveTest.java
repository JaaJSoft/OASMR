package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptRemoveInexistantPackage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandAptRemoveTest {
    @Test
    public void execute() throws Exception{
        CommandAptRemove c = new CommandAptRemove("apcalc");
        assertTrue(c.execute() instanceof String);
    }


    @Test(expected = ExceptionAptRemoveInexistantPackage.class)
    public void executeFailure() throws Exception {
        CommandAptRemove c = new CommandAptRemove("fuufeaofjeej");
        c.execute();
    }
}
