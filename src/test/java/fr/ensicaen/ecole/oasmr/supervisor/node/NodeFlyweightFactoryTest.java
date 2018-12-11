package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.supervisor.exception.ExceptionNodeNotFound;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.Assert.*;

public class NodeFlyweightFactoryTest {
    private NodeFlyweightFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new NodeFlyweightFactory();
    }

    @Test
    public void getNode() throws UnknownHostException {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        assertSame(n, n2);
    }

    @Test
    public void getNodeNotTheSame() throws UnknownHostException {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(InetAddress.getByName("192.168.125.3"), 25641);
        assertNotSame(n, n2);
    }

    @Test
    public void getNodeWithID() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(n.getId());
        assertSame(n, n2);
    }

    @Test
    public void getNodeWithIDNotTheSame() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n3 = factory.getNode(InetAddress.getByName("192.168.125.3"), 25641);
        Node n2 = factory.getNode(n.getId());
        assertNotSame(n3, n2);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void getNodeWithIDNotTheSameError() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        Node n2 = factory.getNode(42);
    }

    @Test
    public void removeNode() throws UnknownHostException, ExceptionNodeNotFound {
        Node n = factory.getNode(InetAddress.getByName("192.168.125.4"), 25641);
        factory.removeNode(n.getId());
        Node ne = factory.getNode(InetAddress.getByName("192.168.125.4"), 25641);
        assertNotSame(n, ne);
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void removeNodeNotFound() throws UnknownHostException, ExceptionNodeNotFound {
        factory.removeNode(85);
    }

    @Test
    public void getNodes() throws UnknownHostException {
        factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        factory.getNode(InetAddress.getByName("192.168.125.2"), 25641);
        assertEquals(1, factory.getNodes().size());
    }
}