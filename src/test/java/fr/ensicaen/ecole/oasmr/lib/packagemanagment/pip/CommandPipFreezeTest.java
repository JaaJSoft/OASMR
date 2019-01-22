package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

public class CommandPipFreezeTest {
    @Test
    public void execute() throws Exception{
        CommandPipFreeze c = new CommandPipFreeze();
        assert(c.execute() instanceof String);
    }

    @Test
    public void executeWithOptions() throws Exception{
        CommandPipFreeze c = new CommandPipFreeze("-l");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipFreeze c = new CommandPipFreeze("--nonexistentOption");
        c.execute();
    }
}
