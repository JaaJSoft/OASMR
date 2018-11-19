package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.util.TreeSet;

public class Supervisor {
    private TreeSet<NodeReal> nodes = new TreeSet<>();
    private Server server;

    public Supervisor(int port) throws IOException, ExceptionPortInvalid {
        server = new Server(port, new HeartBeatsHandler());
    }

    public void start() throws InterruptedException, CloneNotSupportedException, IOException {
        server.start();
    }


}
