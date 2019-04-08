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

package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.packagemanagment.apt.CommandAptAutoRemove;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetCommandsTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(45757);
        s.getCommandFinder().addCommand(CommandEchoString.class);
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
        s.getCommandFinder().addCommand(CommandEchoString.class);
        Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) new RequestGetCommands().execute(s);
        Set<Class<? extends Command>> commands2 = new HashSet<>();
        commands2.add(CommandEchoString.class);
        assertEquals(commands, commands2);
    }

    @Test
    public void executeFalse() throws Exception {
        s.getCommandFinder().addCommand(CommandAptAutoRemove.class);
        Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) new RequestGetCommands().execute(s);
        Set<Class<? extends Command>> commands2 = new HashSet<>();
        commands2.add(CommandEchoString.class);
        assertNotEquals(commands, commands2);
    }
}