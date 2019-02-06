/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
                    event.context();
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
