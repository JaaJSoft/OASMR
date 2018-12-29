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
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestAddTagsToNodeTest {
    private Node n;
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        n = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("127.02.20.5"), 5869);
    }

    @Test
    public void executeAddTagsToNode() throws Exception {
        Set<Tag> old = new HashSet<>(n.getTags());
        Set<Tag> newTagToAdd = new HashSet<>();
        newTagToAdd.add(new Tag("jeej"));
        newTagToAdd.add(new Tag("test"));
        new RequestAddTagsToNode(n.getId(), newTagToAdd).execute(s);
        Set<Tag> newTag = n.getTags();
        assertEquals(old.size() + 2, newTag.size());
    }
}