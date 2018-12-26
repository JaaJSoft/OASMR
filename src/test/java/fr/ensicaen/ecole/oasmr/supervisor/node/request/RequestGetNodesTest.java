package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetNodesTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        Node node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);
        Node node2 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Node node3 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.4"), 56674);
    }

    @Test
    public void executeTestGetNodes() {
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(3, nodes.size());
    }

    @Test
    public void executeTestGetNodesWithAddExistingNode() throws UnknownHostException {
        s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(3, nodes.size());
    }
    @Test
    public void executeTestGetNodesWithAddNewNode() throws UnknownHostException {
        s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.5"), 56674);
        Set<Node> nodes = (Set<Node>) new RequestGetNodes().execute(s);
        assertEquals(4, nodes.size());
    }
}