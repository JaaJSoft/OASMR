package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.command.Heart;
import fr.ensicaen.ecole.oasmr.lib.command.ServerRunnableCommandHandler;
import fr.ensicaen.ecole.oasmr.lib.network.Server;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.node.HeartbeatNodeAlive;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

public class NodeReal extends Node {
    private Server server;
    private Heart heart;
    private InetAddress supervisorAddress;
    private int supervisorPort;

    public NodeReal(String name, InetAddress nodeAddress, int nodePort, InetAddress supervisorAddress, int supervisorPort) throws IOException, ExceptionPortInvalid {
        super(name, nodeAddress, nodePort);
        this.supervisorAddress = supervisorAddress;
        this.supervisorPort = supervisorPort;
        server = new Server(nodePort, new ServerRunnableCommandHandler("command", this));
        heart = new Heart(new HeartbeatNodeAlive(supervisorAddress, supervisorPort), 1);
    }

    public void start() throws InterruptedException, IOException {
        Thread t = new Thread(() -> heart.start());
        t.start();
        server.start();
    }

    @Override
    public Serializable executeCommand(Command c) throws Exception {
        return c.execute(this);
    }
}
