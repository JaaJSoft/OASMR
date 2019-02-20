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

package fr.ensicaen.ecole.oasmr.supervisor.node.command;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.event.EventHeartBeat;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;

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
            node.setLastHeartBeat(LocalDate.now());
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
