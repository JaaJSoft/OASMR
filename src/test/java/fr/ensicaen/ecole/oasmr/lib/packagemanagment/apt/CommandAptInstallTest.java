package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptInstallFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptInstallTest {
    @Test
    public void execute() throws Exception{
        CommandAptInstall c = new CommandAptInstall("apcalc");
        assertEquals(""/*To complete*/, c.execute());
    }

    @Test
    public void executeAlreadyInstalledPackage() throws Exception{
        CommandAptInstall c = new CommandAptInstall("gcc");
        assertEquals("", c.execute());
    }

    @Test(expected = ExceptionAptInstallFailure.class)
    public void executeFailure() throws Exception{
        CommandAptInstall c = new CommandAptInstall("eanfifjeaucbnaoebuifsq");
    }
}
