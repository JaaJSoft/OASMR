package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

public class CommandPipHashTest {
    @Test
    public void execute() throws Exception{
        CommandPipHash c = new CommandPipHash("d:\\coursensi\\oasmr\\oasmr-pip\\numpy-1.15.4-cp37-none-win32.whl");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipHash c = new CommandPipHash("nonexistentPackage");
        c.execute();
    }
}
