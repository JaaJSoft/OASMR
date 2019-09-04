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

package dev.jaaj.oasmr.supervisor.node;

import dev.jaaj.oasmr.lib.command.Command;
import dev.jaaj.oasmr.lib.network.Client;
import dev.jaaj.oasmr.lib.network.util;
import dev.jaaj.oasmr.supervisor.node.command.CommandNodeUpdateData;

import java.io.Serializable;

public class NodeProxy extends Node {
    NodeProxy(NodeData data) {
        super(data);
    }


    @Override
    protected Serializable execute(Command c) throws Exception {
        Client client = new Client(this.getNodeAddress(), this.getPort());
        client.connect();
        util.sendSerializable(client.getSocket(), c);
        Serializable s = util.receiveSerializable(client.getSocket());
        client.disconnect();
        return s;
    }

    @Override
    public void syncData() {
        try {
            executeCommand(new CommandNodeUpdateData(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
