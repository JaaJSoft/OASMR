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


import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

public class Client implements Serializable {
    private final InetAddress ip;
    private final int port;
    private Socket socket;
    private String key;

    public Client(InetAddress ip, int port) throws ExceptionPortInvalid {
        this.ip = ip;
        if (port < 65536 && port > 0) {
            this.port = port;
        } else {
            throw new ExceptionPortInvalid();
        }
    }

    public void connect() throws ExceptionConnectionFailure {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            throw new ExceptionConnectionFailure();
        }
    }

    public void disconnect() throws ExceptionCannotDisconnect {
        try {
            socket.close();
        } catch (IOException e) {
            throw new ExceptionCannotDisconnect();
        }
    }

    public String getKey() {
        return key;
    }

    public Socket getSocket() {
        return socket;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return port == client.port && ip.equals(client.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
