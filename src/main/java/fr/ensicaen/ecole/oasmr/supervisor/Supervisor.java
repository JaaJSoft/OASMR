package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;

import java.io.IOException;
import java.util.TreeSet;

public class Supervisor {
    //private TreeSet<NodeReal> nodes = new TreeSet<>();
    private Server serverHeartBeatsHandler;
    private Server serverRequestHandler;

    public Supervisor(int portheartBeats, int portRequests) throws IOException, ExceptionPortInvalid {
        serverHeartBeatsHandler = new Server(portheartBeats, new ServerRunnableCommandHandler("HeartBeat", this));
        serverRequestHandler = new Server(portRequests, new ServerRunnableCommandHandler("Request", this));
    }

    public void start() throws InterruptedException, CloneNotSupportedException, IOException {
        Thread t = new Thread(() -> {
            try {
                serverHeartBeatsHandler.start();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
        serverRequestHandler.start();
        t.join();
    }


}
