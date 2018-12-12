package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptUpgradeTest {
    @Test
    public void execute() throws Exception{
        CommandAptUpgrade c = new CommandAptUpgrade();
        assertEquals("".getClass().getName(), c.execute().getClass().getName());
    }
    /*How provocate an exception??*/
}
