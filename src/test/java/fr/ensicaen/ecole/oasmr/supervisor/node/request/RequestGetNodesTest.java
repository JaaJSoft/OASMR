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
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetNodesTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        Node node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);
        Node node2 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Node node3 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.4"), 56674);
    }

    @Test
    public void executeTestGetNodes() {
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(3, nodes.size());
    }

    @Test
    public void executeTestGetNodesWithAddExistingNode() throws UnknownHostException {
        s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(3, nodes.size());
    }
    @Test
    public void executeTestGetNodesWithAddNewNode() throws UnknownHostException {
        s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.5"), 56674);
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(4, nodes.size());
    }
}