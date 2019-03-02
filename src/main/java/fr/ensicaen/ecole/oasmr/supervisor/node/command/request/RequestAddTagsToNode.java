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
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.Set;

public class RequestAddTagsToNode extends Request {
    private final Integer id;
    private final Set<Tag> t;

    public RequestAddTagsToNode(Integer id, Set<Tag> tags) {
        this.id = id;
        t = tags;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Node n = supervisor.getNodeFlyweightFactory().getNode(id);
        n.addTags(t);
        return 0;
    }

    @Override
    public String toString() {
        return "add tags to " + id;
    }
}