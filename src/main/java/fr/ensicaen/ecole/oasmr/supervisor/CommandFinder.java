package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.util.HashSet;
import java.util.Set;

public class CommandFinder {

    private final Set<Class<? extends Command>> commands = new HashSet<>();
    private final String directory;

    public CommandFinder(String directory) {
        this.directory = directory;
    }

    public void start() {

    }

    public Set<Class<? extends Command>> getCommands() {
        return commands;
    }

    public void addCommand(Class<? extends Command> c) {
        commands.add(c);
    }
}
