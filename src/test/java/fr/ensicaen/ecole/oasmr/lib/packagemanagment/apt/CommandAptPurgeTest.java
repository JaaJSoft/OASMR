package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptPurgeTest {
    @Test
    public void execute() throws Exception{
        CommandAptPurge c = new CommandAptPurge();
        assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }
}
