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

package fr.ensicaen.ecole.oasmr.lib.network;

import fr.ensicaen.ecole.oasmr.lib.crypto.SupervisorSecurity;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;

public abstract class ServerRunnable implements Runnable, Cloneable {

    protected Socket clientSocket;
    private SupervisorSecurity security;

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void sendMessage(Serializable s) throws IOException {
        util.sendSerializable(clientSocket, security.encrypt( util.serialize(s)));
    }

    public Serializable receiveMessage() throws IOException, ClassNotFoundException {
        return (Serializable) util.deserialize(security.decrypt((byte[]) util.receiveSerializable(clientSocket)));
    }

    public void setSecurity(SupervisorSecurity s) {
        this.security = s;
    }
}
