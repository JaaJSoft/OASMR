package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.exception.ExceptionNodeNotFound;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

/**
 * more test in NodeFlyweightFactoryTest
 */
public class RequestGetNodeTest {
    private Supervisor s;
    private Node node1;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);

    }

    @Test
    public void executeGetNode() throws Exception {
        Node n = (Node) new RequestGetNode(node1.getId()).execute(s);
        assertEquals(node1, n);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void executeGetNodeNotFound() throws Exception {
        Node n = (Node) new RequestGetNode(5647474).execute(s);
    }

}