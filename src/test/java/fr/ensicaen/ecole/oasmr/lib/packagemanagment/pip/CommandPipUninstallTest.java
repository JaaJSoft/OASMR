package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipUninstallTest {
    @Test
    public void execute() throws Exception{
        CommandPipUninstall c = new CommandPipUninstall("numpy");
        assert(c.execute() instanceof String);
    }

    @Test
    public void executeFailure() throws Exception{
        CommandPipUninstall c = new CommandPipUninstall("nonexistentPackage");
        assert(c.execute() instanceof String);
    }
}
