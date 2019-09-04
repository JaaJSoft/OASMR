/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.supervisor.node.command.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.node.Node;
import dev.jaaj.oasmr.supervisor.node.Tag;

import java.io.Serializable;

public class RequestAddTagToNode extends Request {
    private final Integer id;
    private final Tag t;

    public RequestAddTagToNode(Integer id, Tag tag) {
        this.id = id;
        t = tag;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Node n = supervisor.getNodeFlyweightFactory().getNode(id);
        n.addTag(t);
        return 0;
    }

    @Override
    public String toString() {
        return "add tag " + t + "to " + id;
    }
}
