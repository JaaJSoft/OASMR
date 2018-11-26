package fr.ensicaen.ecole.oasmr.supervisor;

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
    public NodeProxy(String name, InetAddress address, int port) {
        super(name, address, port);
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
