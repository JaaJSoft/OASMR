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

package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.supervisor.node.exception.ExceptionNodeNotFound;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class NodeFlyweightFactoryTest {
    private NodeFlyweightFactory factory;

    @Before
    public void setUp() {
        factory = new NodeFlyweightFactory();
    }

    @Test
    public void getNode() throws UnknownHostException {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        assertSame(n, n2);
    }

    @Test
    public void getNodeNotTheSame() throws UnknownHostException {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(InetAddress.getByName("192.168.125.3"), 25641);
        assertNotSame(n, n2);
    }

    @Test
    public void getNodeWithID() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(n.getId());
        assertSame(n, n2);
    }

    @Test
    public void getNodeWithIDNotTheSame() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n3 = factory.getNode(InetAddress.getByName("192.168.125.3"), 25641);
        Node n2 = factory.getNode(n.getId());
        assertNotSame(n3, n2);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void getNodeWithIDNotTheSameError() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(42);
    }

    @Test
    public void removeNode() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.4"), 25641);
        factory.removeNode(n.getId());
        Node ne = factory.getNode(InetAddress.getByName("192.168.125.4"), 25641);
        assertNotSame(n, ne);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void removeNodeNotFound() throws ExceptionNodeNotFound {
        factory.removeNode(85);
    }

    @Test
    public void getNodes() throws UnknownHostException {
        factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        assertEquals(1, factory.getNodes().size());
    }
}