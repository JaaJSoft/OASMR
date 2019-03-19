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

package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.command.Heart;
import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandExecutor;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.HeartbeatNodeAlive;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.event.EventNodeDataChange;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class NodeReal extends Node {
    private Server server;
    private Heart heart;
    private final InetAddress supervisorAddress;
    private final int supervisorPort;
    private final RequestManager requestManager;

    NodeReal(NodeData data, InetAddress supervisorAddress, int supervisorPort) throws IOException, ExceptionPortInvalid {
        super(data);
        requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, supervisorPort);
        this.supervisorAddress = supervisorAddress;
        this.supervisorPort = supervisorPort;
        server = new Server(data.getPort(), new ServerRunnableCommandExecutor(this));
        heart = new Heart(new HeartbeatNodeAlive(supervisorAddress, supervisorPort), data.getHeartbeatPeriod(), this);
    }

    public void start() throws IOException {
        Thread t = new Thread(() -> heart.start());
        t.start();
        server.start();
    }

    @Override
    protected Serializable execute(Command c) throws Exception {
        return c.executeCommand(this);
    }

    @Override
    public void syncData() {
        try {
            requestManager.sendRequest(new EventNodeDataChange(getId(),data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
