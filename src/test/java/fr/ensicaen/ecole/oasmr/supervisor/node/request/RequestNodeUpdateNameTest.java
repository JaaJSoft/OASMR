package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.exception.ExceptionNodeNotFound;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;

import static org.junit.Assert.*;

public class RequestNodeUpdateNameTest {
    private Supervisor s;
    private Node node1;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);

    }

    @Test
    public void executeUpdateName() throws Exception {
        new RequestNodeUpdateName(node1.getId(), "jeej").execute(s);
        assertEquals("jeej", node1.getName());
    }

    @Test(expected = ExceptionNodeNotFound.class)
    public void executeGetNodeNotFound() throws Exception {
        new RequestNodeUpdateName(5647474, "heeh").execute(s);
    }
}