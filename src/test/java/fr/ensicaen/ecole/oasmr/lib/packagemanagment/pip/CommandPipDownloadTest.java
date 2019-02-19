package fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.pip.exceptions.PipException;
import org.junit.Test;

public class CommandPipDownloadTest {
    @Test
    public void execute() throws Exception{
        CommandPipDownload c = new CommandPipDownload("numpy");
        assert(c.execute() instanceof String);
    }

    @Test(expected = PipException.class)
    public void executeFailure() throws Exception{
        CommandPipDownload c = new CommandPipDownload("nonexistentPackage");
        c.execute();
    }
}
