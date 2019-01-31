package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.CommandAptAutoRemove;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetCommandsTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5696, 45757);
        s.addCommand(CommandEchoString.class);
    }

    @Test
    public void execute() throws Exception {
        Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) new RequestGetCommands().execute(s);
        Set<Class<? extends Command>> commands2 = new HashSet<>();
        commands2.add(CommandEchoString.class);
        assertEquals(commands, commands2);
    }

    @Test
    public void executeDuplicate() throws Exception {
        s.addCommand(CommandEchoString.class);
        Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) new RequestGetCommands().execute(s);
        Set<Class<? extends Command>> commands2 = new HashSet<>();
        commands2.add(CommandEchoString.class);
        assertEquals(commands, commands2);
    }

    @Test
    public void executeFalse() throws Exception {
        s.addCommand(CommandAptAutoRemove.class);
        Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) new RequestGetCommands().execute(s);
        Set<Class<? extends Command>> commands2 = new HashSet<>();
        commands2.add(CommandEchoString.class);
        assertNotEquals(commands, commands2);
    }
}