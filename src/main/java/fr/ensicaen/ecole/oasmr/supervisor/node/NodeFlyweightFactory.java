package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.supervisor.exception.ExceptionNodeNotFound;

import java.net.InetAddress;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class NodeFlyweightFactory {

    private int nbNode = 0;
    private Set<Node> nodes;

    public NodeFlyweightFactory() {
        nodes = new HashSet<>();
    }


    public Node getNode(InetAddress address, int port) {
        for (Node n : nodes) {
            if (address.equals(n.getNodeAddress()) && port == n.getPort()) {
                return n;
            }
        }
        NodeProxy newNode = new NodeProxy(nbNode++, address, port);
        nodes.add(newNode);
        return newNode;
    }

    public Node getNode(Integer id) throws ExceptionNodeNotFound {
        for (Node n : nodes) {
            if (id < n.getId()) {
                throw new ExceptionNodeNotFound(id.toString());
            } else if (id.equals(n.getId())) {
                return n;
            }
        }
        throw new ExceptionNodeNotFound(id.toString());
    }

    public void removeNode(Integer id) throws ExceptionNodeNotFound {
        for (Node n : nodes) {
            if (id < n.getId()) {
                throw new ExceptionNodeNotFound(id.toString());
            } else if (id.equals(n.getId())) {
                nodes.remove(n);
            }
        }
        throw new ExceptionNodeNotFound(id.toString());
    }

    public final Set<Node> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }
}
