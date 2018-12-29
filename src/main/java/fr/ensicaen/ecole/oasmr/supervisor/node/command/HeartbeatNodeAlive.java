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

package fr.ensicaen.ecole.oasmr.supervisor.node.command;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.CommandNode;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;

public class HeartbeatNodeAlive extends CommandNode {
    private Client c;
    private int commandPort;

    public HeartbeatNodeAlive(InetAddress supervisorAddress, int port, int commandPort) throws ExceptionPortInvalid {
        c = new Client(supervisorAddress, port);
        this.commandPort = commandPort;
    }


    @Override
    public Serializable execute(Node node) {
        try {
            System.out.println("[" + dateUtil.getFormattedDate() + "]-> New heartbeat to " + c.getIp());
            c.connect();
            util.sendSerializable(c.getSocket(), commandPort);
            int id = (int) util.receiveSerializable(c.getSocket());
            c.disconnect();
            node.setLastHeartBeat(LocalDate.now());
        } catch (ExceptionConnectionFailure | ExceptionCannotDisconnect | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
