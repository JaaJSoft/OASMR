package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;
import fr.ensicaen.ecole.oasmr.lib.command.Heart;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeService {

    public static void main(String[] args) throws UnknownHostException, InterruptedException, ExceptionPortInvalid {

        initHeartBeat();
        //initCommandReceiver();
    }

    private static void initHeartBeat() throws UnknownHostException, InterruptedException, ExceptionPortInvalid {

        //h.stop();
    }

}
