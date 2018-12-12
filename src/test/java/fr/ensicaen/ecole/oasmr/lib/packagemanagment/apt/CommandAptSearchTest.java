package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptSearchFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptSearchTest {
    @Test
    public void execute() throws Exception{
        CommandAptSearch c = new CommandAptSearch("apcalc");
        assert(c.execute() instanceof String);
    }

    @Test
    public void execute1() throws Exception{
        CommandAptSearch c = new CommandAptSearch("eanfifjeaucbnaoebuifsq");
        assert(c.execute() instanceof String);
    }
    //how have exception?
}
