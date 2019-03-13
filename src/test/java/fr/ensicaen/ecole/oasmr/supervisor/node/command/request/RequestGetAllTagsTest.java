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

package fr.ensicaen.ecole.oasmr.supervisor.node.command.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetAllTagsTest {
    private Supervisor s;
    private Set<Tag> tagSet;

    @Before
    public void setUp() throws Exception {
        tagSet = new HashSet<>();
        tagSet.add(new Tag("jeej"));
        tagSet.add(new Tag("test"));
        s = new Supervisor(4242);
        InetAddress a = InetAddress.getByName("48.28.25.2");
        Node node = s.getNodeFlyweightFactory().getNode(a, 85);
        new RequestAddTagsToNode(node.getId(), tagSet).execute(s);
        Node node2 = s.getNodeFlyweightFactory().getNode(a, 85);
        new RequestAddTagsToNode(node2.getId(), tagSet).execute(s);
    }

    @Test
    public void executeGetAllTag() throws Exception {
        Tag[] tags = (Tag[]) new RequestGetAllTags().execute(s);
        assertArrayEquals(tags, tagSet.toArray());
    }

}