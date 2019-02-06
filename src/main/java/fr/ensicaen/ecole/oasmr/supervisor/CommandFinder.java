package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class CommandFinder extends Thread {

    private final Set<Class<? extends Command>> commands = new HashSet<>();
    private final String directory;
    private final WatchService watchService;

    public CommandFinder(String directory) throws IOException {
        this.directory = directory;
        watchService = FileSystems.getDefault().newWatchService();
    }

    @Override
    public void run() {
        Path p = Paths.get(directory);
        try {
            p.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey key;
            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    /*System.out.println("Event kind:"
                            + event.kind()
                            + ". File affected: "
                            + event.context()
                            + ".");*/
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Set<Class<? extends Command>> getCommands() {
        return commands;
    }

    public void addCommand(Class<? extends Command> c) {
        commands.add(c);
    }
}
