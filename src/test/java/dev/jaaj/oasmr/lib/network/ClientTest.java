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

package dev.jaaj.oasmr.lib.network;

import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class ClientTest {


    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidNegative() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.1.1"), -2);
    }

    @Test(expected = ExceptionPortInvalid.class)
    public void portTestInvalidSup() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.1.1"), 1000000);
    }

    @Test(expected = UnknownHostException.class)
    public void addressInvalid() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.1.11.1.1"), 6585);
    }

    @Test
    public void portValid() throws UnknownHostException, ExceptionPortInvalid {
        Client c1 = new Client(InetAddress.getByName("1.11.1.1"), 6585);
        assertEquals(6585, c1.getPort());
    }

    @Test
    public void addressValid() throws UnknownHostException, ExceptionPortInvalid {
        InetAddress ip = InetAddress.getByName("1.11.1.1");
        Client c1 = new Client(ip, 6585);
        assertEquals(ip.toString(), c1.getIp().toString());
    }

}