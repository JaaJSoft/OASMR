package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;

public abstract class Node implements Comparable {
    private Integer id;

    private String name;
    private LocalDate lastHeartBeat;
    protected InetAddress nodeAddress;
    protected int port;

    public Node(int id, InetAddress nodeAddress, int port) {
        this.id = id;
        this.nodeAddress = nodeAddress;
        this.port = port;
        name = nodeAddress.toString() + ":" + port;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof Node) {
            return id.compareTo(((Node) o).getId());
        }
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    public abstract Serializable executeCommand(Command c) throws Exception;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getLastHeartBeat() {
        return lastHeartBeat;
    }

    public InetAddress getNodeAddress() {
        return nodeAddress;
    }

    public int getPort() {
        return port;
    }

    public void setLastHeartBeat(LocalDate lastHeartBeat) {
        this.lastHeartBeat = lastHeartBeat;
    }
}
