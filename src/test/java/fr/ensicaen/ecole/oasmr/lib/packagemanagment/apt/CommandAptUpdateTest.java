package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptUpdateFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptUpdateTest {
    @Test
    public void execute() throws Exception{
        CommandAptUpdate c = new CommandAptUpdate();
        assertEquals(""/*To complete*/, c.execute());
    }
    /*How provocate an exception??*/
}
