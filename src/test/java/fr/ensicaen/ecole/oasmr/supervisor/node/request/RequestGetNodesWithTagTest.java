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
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetNodesWithTagTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        Node node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);
        Node node2 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Node node3 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.4"), 56674);
        Tag tag1 = new Tag("jeej");
        Tag tag2 = new Tag("batE");
        Tag tag3 = new Tag("batA");
        node1.addTag(tag1);
        node1.addTag(tag2);
        node2.addTag(tag2);
        node3.addTag(tag3);
    }

    @Test
    public void executeTestTag1() throws Exception {
        NodeBean[] nodes = (NodeBean[]) new RequestGetNodesWithTag(new Tag("jeej")).execute(s);
        assertEquals(1, nodes.length);
    }

    @Test
    public void executeTestTag2() throws Exception {
        NodeBean[] nodes = (NodeBean[]) new RequestGetNodesWithTag(new Tag("batE")).execute(s);
        assertEquals(2, nodes.length);
    }

    @Test
    public void executeTestTag3() throws Exception {
        NodeBean[] nodes = (NodeBean[]) new RequestGetNodesWithTag(new Tag("batA")).execute(s);
        assertEquals(1, nodes.length);
    }

    @Test
    public void executeTestTag2AndTag1() throws Exception {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("batA"));
        tags.add(new Tag("jeej"));
        NodeBean[] nodes = (NodeBean[]) new RequestGetNodesWithTag(new Tag("batA")).execute(s);
        assertEquals(1, nodes.length);

    }
}