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

package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//TODO OR TAG
public class RequestGetNodesWithTag extends Request {
    private final Set<Tag> tags;

    public RequestGetNodesWithTag(Set<Tag> tags) {
        this.tags = tags;
    }

    public RequestGetNodesWithTag(Tag t) {
        tags = new HashSet<>();
        tags.add(t);
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return supervisor.getNodeFlyweightFactory().getNodes().parallelStream().map(Node::getData)
                .filter(n -> n.getTags().containsAll(tags)).toArray(NodeBean[]::new);
    }

    @Override
    public String toString() {
        return "get nodes with tags " + tags;
    }
}
