package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptSearchFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptSearchTest {
    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("gcc");
        assertEquals(""/*To complete*/, c.execute());
    }

    @Test
    public void executeWithoutResult() throws Exception{
        CommandAptShow c = new CommandAptShow("eanfifjeaucbnaoebuifsq");
        assertEquals("", c.execute());
    }

    @Test(expected = ExceptionAptSearchFailure.class)
    public void executeFailure() throws Exception{
        CommandAptShow c = new CommandAptShow("");
    }
}
