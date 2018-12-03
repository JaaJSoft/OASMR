package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;
import fr.ensicaen.ecole.oasmr.lib.command.Heart;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.Node;
import fr.ensicaen.ecole.oasmr.supervisor.NodeReal;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeService {

    public static void main(String[] args) throws IOException, InterruptedException, ExceptionPortInvalid {

        initHeartBeat();
        //initCommandReceiver();
        NodeReal localNode = new NodeReal("jeej",InetAddress.getLocalHost(),56789,InetAddress.getByName("127.0.0.1"),6969);
        localNode.start();
    }

    private static void initHeartBeat() throws UnknownHostException, InterruptedException, ExceptionPortInvalid {

        //h.stop();
    }

}
