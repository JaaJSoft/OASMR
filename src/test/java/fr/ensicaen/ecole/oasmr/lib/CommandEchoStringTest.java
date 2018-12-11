package fr.ensicaen.ecole.oasmr.lib;

import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommandEchoStringTest {
    private CommandEchoString c;

    @Before
    public void setUp() {
        c = new CommandEchoString("jeej");
    }

    @Test
    public void execute() throws Exception {
        assertEquals("jeej\n", c.execute());
    }

    @Test
    public void executeFailure() throws Exception {
        assertNotEquals("mmmmm", c.execute());
    }
}