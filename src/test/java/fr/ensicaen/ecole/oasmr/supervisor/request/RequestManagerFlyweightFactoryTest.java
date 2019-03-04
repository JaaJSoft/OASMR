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

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class RequestManagerFlyweightFactoryTest {
    private RequestManagerFlyweightFactory factory;

    @Before
    public void setUp() {
        factory = RequestManagerFlyweightFactory.getInstance();
        factory.clear();
    }

    @Test
    public void getRequestManager() throws UnknownHostException, ExceptionPortInvalid {
        RequestManager r = factory.getRequestManager(InetAddress.getByName("192.168.1.1"), 55555);
        assertSame(r, factory.getRequestManager(InetAddress.getByName("192.168.1.1"), 55555));
    }

    @Test
    public void getRequestManagerSize() throws UnknownHostException, ExceptionPortInvalid {
        RequestManager r = factory.getRequestManager(InetAddress.getByName("192.168.1.2"), 55555);
        RequestManager r2 = factory.getRequestManager(InetAddress.getByName("192.168.1.2"), 55555);

        assertEquals(1, factory.getNbRequestManager());

    }

    @Test
    public void getRequestManagerSizeTwoInsertion() throws UnknownHostException, ExceptionPortInvalid {
        RequestManager r = factory.getRequestManager(InetAddress.getByName("192.168.1.1"), 55555);
        RequestManager r2 = factory.getRequestManager(InetAddress.getByName("192.168.1.2"), 55555);

        assertEquals(2, factory.getNbRequestManager());

    }

}