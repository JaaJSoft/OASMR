package fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.exceptions.ExceptionAptFailGettingList;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandAptListTest {
    @Test(expected = ExceptionAptFailGettingList.class)
    public void execute() throws Exception {
        Command c = new CommandAptList();
        c.execute();
        //assertEquals("".getClass().getTypeName(), c.execute().getClass().getTypeName());
    }
}
