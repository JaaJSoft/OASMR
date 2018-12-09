package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.network.Client;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeService {

    public static void main(String[] args) throws IOException, InterruptedException, ExceptionPortInvalid {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        int port = 6969;
        InetAddress localhost = InetAddress.getLocalHost();
        int commandPort = 56789;

        int id = initHeartBeat(address, port, commandPort);
        //initCommandReceiver();
        NodeReal localNode = new NodeReal(id, localhost, commandPort, address, port);
        localNode.start();
    }

    private static int initHeartBeat(InetAddress supervisorAddress, int port, int commandPort) throws UnknownHostException, InterruptedException, ExceptionPortInvalid {
        //Client c = new Client()
        return 0;
    }

}
