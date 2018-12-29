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
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RequestGetAllTags extends Request {
    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Set<Node> nodes = supervisor.getNodeFlyweightFactory().getNodes();
        Set<Tag> tags = new HashSet<>();
        for (Node n : nodes) {
            tags.addAll(n.getTags());
        }
        return (Serializable) tags;
    }

    @Override
    public String toString() {
        return "Get tags";
    }
}
