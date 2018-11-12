package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.io.IOException;
import java.util.TreeSet;

public class Supervisor {
    private TreeSet<NodeReal> nodes = new TreeSet<>();

    public static void main(String[] args) throws IOException, ExceptionPortInvalid, CloneNotSupportedException, InterruptedException {
        Server s = new Server(55555, new HeartBeatsHandler());
        s.start();
    }

}
