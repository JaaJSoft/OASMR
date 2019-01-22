package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

import java.io.File;

public class CommandPipHashTest {
    @Test
    public void execute() throws Exception {
        File f = new File("test.txt");
        f.createNewFile();
        CommandPipHash c = new CommandPipHash("test.txt");
        assert (c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception {
        CommandPipHash c = new CommandPipHash("nonexistentPackage");
        c.execute();
    }
}
