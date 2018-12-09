package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptSearchFailure;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptSearchTest {
    @Test
    public void execute() throws Exception{
        CommandAptShow c = new CommandAptShow("gcc");
        assert(c.execute() instanceof String);
    }

    @Test(expected = ExceptionAptSearchFailure.class)
    public void executeFailure1() throws Exception{
        CommandAptShow c = new CommandAptShow("eanfifjeaucbnaoebuifsq");
        c.execute();
    }

    @Test(expected = ExceptionAptSearchFailure.class)
    public void executeFailure2() throws Exception{
        CommandAptShow c = new CommandAptShow("");
        c.execute();
    }
}
