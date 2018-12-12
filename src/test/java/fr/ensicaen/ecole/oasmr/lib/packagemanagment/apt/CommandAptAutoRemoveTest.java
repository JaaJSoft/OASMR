package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptAutoRemoveTest {
    @Test
    public void execute() throws Exception{
        CommandAptAutoRemove c = new CommandAptAutoRemove();
        assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }
}
