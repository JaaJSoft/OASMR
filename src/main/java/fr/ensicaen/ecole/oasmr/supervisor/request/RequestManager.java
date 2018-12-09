package fr.ensicaen.ecole.oasmr.supervisor.request;


import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.request.exception.ExceptionRequestError;
import fr.ensicaen.ecole.oasmr.supervisor.request.exception.ExceptionRequestResponseNull;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

public class RequestManager {
    private final InetAddress address;
    private final int port;

    public RequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        this.address = address;
        this.port = port;
    }

    public Serializable sendRequest(Command r) throws Exception {
        try {
            Client client = new Client(address, port);
            client.connect();
            System.out.println("[" + dateUtil.getFormattedDate() + "]-> Command " + r + " to " + client.getSocket().getInetAddress() + ":" + client.getSocket().getPort());
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
