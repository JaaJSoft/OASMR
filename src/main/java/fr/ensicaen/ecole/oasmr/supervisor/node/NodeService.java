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

import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.event.EventNewNode;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;

import java.io.IOException;
import java.net.InetAddress;

public class NodeService {

    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 40404;
        InetAddress localhost = InetAddress.getLocalHost();
        int commandPort = 56780;

        NodeReal localNode = initNode(address, port, localhost, commandPort);
        localNode.start();
    }

    private static NodeReal initNode(InetAddress supervisorAddress, int port, InetAddress localAddress, int commandPort) throws Exception {
        RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, port);
        NodeData data = (NodeData) r.sendRequest(new EventNewNode(localAddress, commandPort));
        return new NodeReal(data, supervisorAddress, port);
    }

}
