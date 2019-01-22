package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipShowTest {
    @Test
    public void execute() throws Exception{
        CommandPipShow c = new CommandPipShow("pip");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipShow c = new CommandPipShow("nonexistentPackage");
        c.execute();
    }
}
