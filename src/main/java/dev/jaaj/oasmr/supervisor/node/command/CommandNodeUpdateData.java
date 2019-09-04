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

package dev.jaaj.oasmr.supervisor.node.command;

import dev.jaaj.oasmr.supervisor.node.Node;
import dev.jaaj.oasmr.supervisor.node.NodeData;

import java.io.Serializable;

public class CommandNodeUpdateData extends CommandNode {
    private final NodeData newData;

    public CommandNodeUpdateData(NodeData newData) {
        this.newData = newData;
    }

    @Override
    public Serializable execute(Node node) throws Exception {
        node.setData(newData);
        return 0;
    }

    @Override
    public String toString() {
        return "update nodeData";
    }
}
