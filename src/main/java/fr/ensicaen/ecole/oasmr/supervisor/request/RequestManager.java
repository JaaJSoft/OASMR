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

public class RequestManager { //TODO QUEUE
    private Client client;

    public RequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        this.client = new Client(address, port);
    }

    private void connect() throws ExceptionConnectionFailure {
        client.connect();
    }

    private void disconnect() throws ExceptionCannotDisconnect {
        client.disconnect();
    }

    public Serializable sendRequest(Command r) throws Exception {
        try {
            connect();
            System.out.println("[" + dateUtil.getFormattedDate() + "]-> Command " + r + " to " + client.getSocket().getInetAddress() + ":" + client.getSocket().getPort());
            util.sendSerializable(client.getSocket(), r);
            Serializable response = util.receiveSerializable(client.getSocket());
            if (response == null) {
                throw new ExceptionRequestResponseNull();
            }
            if (response instanceof Exception) {
                disconnect();
                throw (Exception) response;
            }
            disconnect();
            return response;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new ExceptionRequestError();
    }
}
