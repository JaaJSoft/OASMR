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

import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;

import static org.junit.Assert.*;

public class GroupedCommandTest {
    private Supervisor supervisor;

    @Before
    public void setUp() throws Exception {
        supervisor = new Supervisor(45757);
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