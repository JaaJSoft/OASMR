package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandPipSearchTest {
    @Test
    public void execute() throws Exception {
        CommandPipSearch c = new CommandPipSearch("numpy");
        c.execute();
    }

    @Test(expected = PipException.class)
    public void executeUnknownTerm() throws Exception {
        CommandPipSearch c = new CommandPipSearch("nonexistentPackage");
        assert (c.execute() instanceof String);
    }
}
