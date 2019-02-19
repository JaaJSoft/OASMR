package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipCheckTest {
    @Test
    public void execute() throws Exception{
        CommandPipCheck c = new CommandPipCheck();
        assert(c.execute() instanceof String);
    }
}
