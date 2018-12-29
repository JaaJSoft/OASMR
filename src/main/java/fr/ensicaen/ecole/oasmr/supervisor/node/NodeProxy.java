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

package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class NodeProxy extends Node {
    NodeProxy(int id, InetAddress address, int port) {
        super(id, address, port);
    }

    @Override
    public Serializable executeCommand(Command c) throws ExceptionPortInvalid, ExceptionConnectionFailure, ExceptionCannotDisconnect, IOException, ClassNotFoundException {
        Client client = new Client(nodeAddress, port);
        client.connect();
        util.sendSerializable(client.getSocket(), c);
        Serializable s = util.receiveSerializable(client.getSocket());
        client.disconnect();
        return s;
    }
}
