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

import fr.ensicaen.ecole.oasmr.supervisor.node.command.event.EventNewNode;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;

import java.net.DatagramSocket;

import java.net.InetAddress;

public class NodeService {

    public static void main(String[] args) throws Exception {

        InetAddress address;
        int port;
        int commandPort;
        if (args.length == 3) {
            address = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
            commandPort = Integer.parseInt(args[2]);
        } else {
            address = InetAddress.getByName("127.0.0.1");
            port = 40404;
            commandPort = 56780;
        }

        String ip;
        try (final DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
            ip = socket.getLocalAddress().getHostAddress();
            NodeReal localNode = initNode(address, port, InetAddress.getByName(ip), commandPort);
            localNode.start();
        }
    }

    private static NodeReal initNode(InetAddress supervisorAddress, int port, InetAddress localAddress, int commandPort) throws Exception {
        RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, port);
        NodeData data = (NodeData) r.sendRequest(new EventNewNode(localAddress, commandPort));
        NodeReal nodeReal = new NodeReal(data, supervisorAddress, port);
        nodeReal.setSSHLogin(System.getProperty("user.name"));
        nodeReal.setSSHPort(22);
        nodeReal.setName(InetAddress.getLocalHost().getHostName());
        return nodeReal;
    }

}
