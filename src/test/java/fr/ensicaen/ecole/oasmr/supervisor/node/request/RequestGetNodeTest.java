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

package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.exception.ExceptionNodeNotFound;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * more test in NodeFlyweightFactoryTest
 */
public class RequestGetNodeTest {
    private Supervisor s;
    private Node node1;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);

    }

    @Test
    public void executeGetNode() throws Exception {
        NodeData n = (NodeData) new RequestGetNode(node1.getId()).execute(s);
        assertEquals(node1.getData(), n);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void executeGetNodeNotFound() throws Exception {
        NodeData n = (NodeData) new RequestGetNode(5647474).execute(s);
    }

}