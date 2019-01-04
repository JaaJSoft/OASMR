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

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.IOException;
import java.net.InetAddress;
import java.time.LocalDate;

public class ServerRunnableHeartBeatsHandler extends ServerRunnable {
    private final Supervisor supervisor;

    public ServerRunnableHeartBeatsHandler(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public void run() {
        System.out.println("[" + dateUtil.getFormattedDate() + "]-> New heartbeat from " + clientSocket.getInetAddress());
        try {
            InetAddress address = clientSocket.getInetAddress();
            int port = (int) util.receiveSerializable(clientSocket);
            Node n = supervisor.getNodeFlyweightFactory().getNode(address, port);
            n.setLastHeartBeat(LocalDate.now());
            util.sendSerializable(clientSocket, n.getId());
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
