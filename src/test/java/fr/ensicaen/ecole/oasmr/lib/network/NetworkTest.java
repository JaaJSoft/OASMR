/*
 *  Copyright (c) 2018. CCC-Development-Team
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

package fr.ensicaen.ecole.oasmr.lib.network;


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;

import static org.junit.Assert.*;

public class NetworkTest {
    private Server server;
    private Client c;

    @Before
    public void setUp() throws IOException, ExceptionPortInvalid, ExceptionConnectionFailure, InterruptedException {
        int port = 54324;
        Thread t = new Thread(() -> {
            try {
                Server s = new Server(port, new ServerRunnableEcho());
                s.start();
            } catch (IOException | ExceptionPortInvalid e) {
                e.printStackTrace();
            }
        });
        c = new Client(InetAddress.getByName("127.0.0.1"), port);
        t.start();
        Thread.sleep(1000);
        System.out.println("Connexion");
        c.connect();
    }

    @After
    public void tearDown() throws ExceptionCannotDisconnect {
        c.disconnect();
        //s.stop();
    }

    @Test
    public void sendStringTest() throws Exception {
        String test = "test";
        util.sendSerializable(c.getSocket(), test);
        String result = (String) util.receiveSerializable(c.getSocket());
        assertEquals(test, result);
    }

}
