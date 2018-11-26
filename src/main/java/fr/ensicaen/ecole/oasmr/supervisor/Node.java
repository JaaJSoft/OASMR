package fr.ensicaen.ecole.oasmr.supervisor;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;

public abstract class Node {
    private int id;

    private String name;
    private LocalDate lastHeartBeat;
    protected InetAddress nodeAddress;
    protected int port;

    public Node(String name, InetAddress nodeAddress, int port) {
        this.name = name;
        this.nodeAddress = nodeAddress;
        this.port = port;
    }

    public abstract Serializable executeCommand(Command c) throws Exception;

}
