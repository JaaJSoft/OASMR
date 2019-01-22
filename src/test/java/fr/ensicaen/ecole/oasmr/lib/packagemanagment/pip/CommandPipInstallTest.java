package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipInstallTest {
    @Test
    public void execute() throws Exception{
        CommandPipInstall c = new CommandPipInstall("numpy");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipInstall c = new CommandPipInstall("nonexistentPackage");
        c.execute();
    }
}
