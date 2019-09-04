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

import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.supervisor.node.command.event.EventHeartBeat;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import dev.jaaj.oasmr.supervisor.node.Node;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class HeartbeatNodeAlive extends CommandNode {
    private final InetAddress supervisorAddress;
    private final int port;

    public HeartbeatNodeAlive(InetAddress supervisorAddress, int port) {
        this.supervisorAddress = supervisorAddress;
        this.port = port;
    }

    @Override
    public Serializable execute(Node node) throws Exception {
        try {
            RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, port);
            r.sendRequest(new EventHeartBeat(node.getId()));
            node.setLastHeartBeat(LocalDateTime.now());
        } catch (ExceptionPortInvalid e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return "HeartBeat";
    }
}
