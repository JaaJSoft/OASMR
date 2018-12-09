package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.dateUtil;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeFlyweightFactory;
import fr.ensicaen.ecole.oasmr.supervisor.node.ServerRunnableHeartBeatsHandler;

import java.io.IOException;

public class Supervisor {
    private NodeFlyweightFactory nodeFlyweightFactory;
    private Server serverHeartBeatsHandler;
    private Server serverRequestHandler;

    public Supervisor(int portHeartBeats, int portRequests) throws IOException, ExceptionPortInvalid {
        serverHeartBeatsHandler = new Server(portHeartBeats, new ServerRunnableHeartBeatsHandler(this));
        serverRequestHandler = new Server(portRequests, new ServerRunnableCommandHandler("Request", this));
        this.nodeFlyweightFactory = new NodeFlyweightFactory();
    }

    public void start() throws InterruptedException, CloneNotSupportedException, IOException {
        Thread ThreadServerHeartBeatsHandler = new Thread(() -> {
            try {
                serverHeartBeatsHandler.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread ThreadServerRequestHandler = new Thread(() -> {
            try {
                serverRequestHandler.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> HeartBeatHandler loading... ");
        ThreadServerHeartBeatsHandler.start();
        System.out.println("Done !");
        System.out.print("[" + dateUtil.getFormattedDate() + "]-> RequestHandler loading... ");
        ThreadServerRequestHandler.start();
        System.out.println("Done !");
        ThreadServerHeartBeatsHandler.join();
        ThreadServerRequestHandler.join();
    }

    public NodeFlyweightFactory getNodeFlyweightFactory() {
        return nodeFlyweightFactory;
    }
}
