package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptUpgradeTest {
    @Test
    public void execute() throws Exception{
        CommandAptUpdate c = new CommandAptUpdate();
        assertEquals(""/*To complete*/, c.execute());
    }
    /*How provocate an exception??*/
}
