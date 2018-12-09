package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.ServerRunnable;
import fr.ensicaen.ecole.oasmr.lib.network.util;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.IOException;
import java.net.InetAddress;

public class ServerRunnableHeartBeatsHandler extends ServerRunnable {
    private Supervisor supervisor;

    public ServerRunnableHeartBeatsHandler(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public void run() {
        System.out.println("[" + dateUtil.getFormattedDate() + "]-> New heartbeat from " + clientSocket.getInetAddress());
        try {
            InetAddress address = clientSocket.getInetAddress();
            int port = (int) util.receiveSerializable(clientSocket);
            //TODO get command port !
            Node n = supervisor.getNodeFlyweightFactory().getNode(address, port);

            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
