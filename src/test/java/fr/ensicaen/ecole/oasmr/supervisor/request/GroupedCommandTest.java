package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import java.io.Serializable;

import static org.junit.Assert.*;

public class GroupedCommandTest {
    private Supervisor supervisor;

    @Before
    public void setUp() throws Exception {
        supervisor = new Supervisor(5696, 45757);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void execute() throws Exception {
        Serializable[] responses = (Serializable[]) new GroupedCommand(new CommandEchoString("jeej"), new CommandEchoString("AH")).execute(supervisor);
        String[] expected = {"jeej\n", "AH\n"};
        assertArrayEquals(expected, responses);
    }
}