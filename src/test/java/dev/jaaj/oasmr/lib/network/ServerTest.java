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

package dev.jaaj.oasmr.lib.network;

import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidNegative() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(-2, new ServerRunnableEcho());
    }

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidSup() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(1000000, new ServerRunnableEcho());
    }

    @Test
    public void portValid() throws IOException, ExceptionPortInvalid {
        Server c1 = new Server(6585, new ServerRunnableEcho());
        assertEquals(6585, c1.getPort());
    }
}