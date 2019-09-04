/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.supervisor;

import dev.jaaj.oasmr.lib.ComparatorClass;
import dev.jaaj.oasmr.lib.command.Command;
import dev.jaaj.oasmr.supervisor.request.Request;

import java.io.*;
import java.nio.file.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class CommandFinder extends Thread {

    private final Set<Class<? extends Command>> commands = new TreeSet<>(new ComparatorClass());
    private final Set<Class<? extends Request>> requests = new TreeSet<>(new ComparatorClass());
    private final String directory;
    private final WatchService watchService;

    public CommandFinder(String directory) throws IOException {
        this.directory = directory;
        watchService = FileSystems.getDefault().newWatchService();
        scanDirectory(new File(directory));
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
                key.pollEvents();
                scan();
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void scan() {
        commands.clear();
        requests.clear();
        scanDirectory(new File(directory));
        //System.out.println(commands);
    }

    private void scanDirectory(File f) {
        File[] files = f.listFiles();
        if (files != null) {
            for (File subFile : files) {
                if (subFile.getName().endsWith(".jar")) {
                    scanJar(subFile);
                }
                scanDirectory(subFile);
            }
        }
    }

    void scanJar(File jar) {
        try {
            FileInputStream fileInputStream = new FileInputStream(jar);
            JarInputStream jarInputStream = new JarInputStream(fileInputStream);

            JarEntry jarfile;
            do {
                jarfile = jarInputStream.getNextJarEntry();
                if (jarfile != null) {
                    if (jarfile.getName().endsWith(".class")) {
                        String classname = jarfile.getName().replace('/', '.').substring(0, jarfile.getName().length() - 6);
                        try {
                            Class c = Class.forName(classname);
                            if (Request.class.isAssignableFrom(c)) {
                                requests.add(c);
                            } else if (Command.class.isAssignableFrom(c)) {
                                commands.add(c);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            System.out.println("WARNING: failed to instantiate " + classname + " from " + jarfile.getName());
                        }
                    }
                }
            } while (jarfile != null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Set<Class<? extends Command>> getCommands() {
        return commands;
    }

    public Set<Class<? extends Request>> getRequests() {
        return requests;
    }

    public void addCommand(Class<? extends Command> c) {
        commands.add(c);
    }
}
