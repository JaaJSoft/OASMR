package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandPipListTest {
    @Test
    public void execute() throws Exception{
        CommandPipList c = new CommandPipList();
        assert(c.execute() instanceof String);
    }

    @Test
    public void executeWithOptions() throws Exception{
        CommandPipList c = new CommandPipList("--not-required");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipList c = new CommandPipList("--nonexistentOption");
        c.execute();
    }
}
