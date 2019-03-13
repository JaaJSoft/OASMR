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

package fr.ensicaen.ecole.oasmr.supervisor.request;


import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.request.exception.ExceptionRequestError;
import fr.ensicaen.ecole.oasmr.supervisor.request.exception.ExceptionRequestResponseNull;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RequestManager {
    private final InetAddress address;
    private final int port;
    private final ExecutorService executorService;

    public RequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        this.address = address;
        if (port < 65536 && port > 0) {
            this.port = port;
        } else {
            throw new ExceptionPortInvalid();
        }
        executorService = Executors.newCachedThreadPool();
    }

    public Serializable sendRequest(Command r) throws Exception {
        try {
            Client client = new Client(address, port);
            client.connect();
            System.out.println("[" + dateUtil.getFormattedDate() + "]-> Command " + r + " on " + client.getSocket().getInetAddress() + ":" + client.getSocket().getPort());
            util.sendSerializable(client.getSocket(), r);
            Serializable response = util.receiveSerializable(client.getSocket());
            if (response == null) {
                throw new ExceptionRequestResponseNull();
            }
            if (response instanceof Exception) {
                client.disconnect();
                throw (Exception) response;
            }
            client.disconnect();
            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ExceptionRequestError();
    }

    public Future<? extends Serializable> aSyncSendRequest(Command r) {
        return executorService.submit(() -> sendRequest(r));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestManager that = (RequestManager) o;
        return port == that.port && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, port);
    }
}
