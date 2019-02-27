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

package fr.ensicaen.ecole.oasmr.supervisor.node.command.event;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.Event;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDateTime;

public class EventNewNode extends Event {
    private InetAddress address;
    private int port;
    private String login;
    private int ssh_port;

    public EventNewNode(InetAddress address, int port, String login, int ssh_port) {
        this.address = address;
        this.port = port;
        this.login = login;
        this.ssh_port = ssh_port;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Node n = supervisor.getNodeFlyweightFactory().getNode(address, port);
        n.setLastHeartBeat(LocalDateTime.now());
        n.setSSHLogin(login);
        n.setSSHPort(ssh_port);
        return n.getData();
    }

    @Override
    public String toString() {
        return "New node " + address + ":" + port;
    }
}
