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
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class RequestGetAllTagsTest {
    private Supervisor s;
    private Set<Tag> tagSet;

    @Before
    public void setUp() throws Exception {
        tagSet = new HashSet<>();
        tagSet.add(new Tag("jeej"));
        s = new Supervisor(42424, 4242);
        InetAddress a = InetAddress.getByName("48.28.25.2");
        Node node = s.getNodeFlyweightFactory().getNode(a, 85);
        new RequestAddTagsToNode(node.getId(), tagSet).execute(s);
    }

    @Test
    public void executeGetAllTag() throws Exception {
        Set<Tag> tags = (Set<Tag>) new RequestGetAllTags().execute(s);
        assertEquals(tags, tagSet);
    }

    @Test
    public void executeGetAllTagFalse() throws Exception {
        Set<Tag> tags = (Set<Tag>) new RequestGetAllTags().execute(s);
        assertNotEquals(tags, new HashSet<>());
    }

}