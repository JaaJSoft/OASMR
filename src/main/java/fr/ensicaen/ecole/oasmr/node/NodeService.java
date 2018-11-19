package fr.ensicaen.ecole.oasmr.node;

import fr.ensicaen.ecole.oasmr.lib.heartbeat.Heart;
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
        Heart h = new Heart(new HeartbeatNodeAlive(InetAddress.getByName("127.0.0.1")), 1);
        h.start();
        //h.stop();
    }

    private static void initCommandReceiver() {
        try {
            Server server = new Server(11197, new CommandReceiver());
            server.start();
        } catch (IOException | ExceptionPortInvalid | InterruptedException | CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}
