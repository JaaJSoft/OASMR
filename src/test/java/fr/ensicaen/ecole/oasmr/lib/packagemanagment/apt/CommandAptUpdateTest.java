package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptUpdateFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommandAptUpdateTest {
    @Test
    public void execute() throws Exception{
        CommandAptUpdate c = new CommandAptUpdate();
        assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }
    /*How provocate an exception??*/
    /*Check si la sortie n'est pas une demande d'authentification sudo qui est probablement aussi une string*/
}
