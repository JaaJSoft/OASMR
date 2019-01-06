package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipSearchTest {
    @Test
    public void execute() throws Exception{
        CommandPipSearch c = new CommandPipSearch("numpy");
        c.execute();
    }

    @Test
    public void executeUnknownTerm() throws Exception{
        CommandPipSearch c = new CommandPipSearch("nonexistentPackage");
        assert(c.execute() instanceof String);
    }
}
