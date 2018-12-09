package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionCannotDisconnect;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.network.util;

import java.io.IOException;
import java.net.InetAddress;

public class NodeService {

    public static void main(String[] args) throws IOException, InterruptedException, ExceptionPortInvalid, ClassNotFoundException, ExceptionCannotDisconnect, ExceptionConnectionFailure {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 6969;
        InetAddress localhost = InetAddress.getLocalHost();
        int commandPort = 56789;

        NodeReal localNode = initNode(address, port, localhost, commandPort);
        localNode.start();
    }

    private static NodeReal initNode(InetAddress supervisorAddress, int port, InetAddress localAddress, int commandPort) throws IOException, InterruptedException, ExceptionPortInvalid, ExceptionConnectionFailure, ExceptionCannotDisconnect, ClassNotFoundException {
        Client c = new Client(supervisorAddress, port);
        c.connect();
        util.sendSerializable(c.getSocket(), commandPort);
        int id = (int) util.receiveSerializable(c.getSocket());
        c.disconnect();
        return new NodeReal(id, localAddress, commandPort, supervisorAddress, port);
    }

}
