package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptInstallFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptInstallTest {
    @Test
    public void execute() throws Exception{
        CommandAptInstall c = new CommandAptInstall("apcalc");
        assertEquals("".getClass().getName(), c.execute().getClass().getName());
    }

    @Test
    public void executeAlreadyInstalledPackage() throws Exception{
        CommandAptInstall c = new CommandAptInstall("gcc");
        assertEquals("".getClass().getName(), c.execute().getClass().getName());
    }

    @Test(expected = ExceptionAptInstallFailure.class)
    public void executeFailure() throws Exception{
        CommandAptInstall c = new CommandAptInstall("eanfifjeaucbnaoebuifsq");
        c.execute();
    }
}
