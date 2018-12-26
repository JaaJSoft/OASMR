package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestGetNodesWithTagTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        Node node1 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.2"), 56674);
        Node node2 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.3"), 56674);
        Node node3 = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("192.169.9.4"), 56674);
        Tag tag1 = new Tag("jeej");
        Tag tag2 = new Tag("batE");
        Tag tag3 = new Tag("batA");
        node1.addTag(tag1);
        node1.addTag(tag2);
        node2.addTag(tag2);
        node3.addTag(tag3);
    }

    @Test
    public void executeTestTag1() throws Exception {
        Set<Node> nodes = (Set<Node>) new RequestGetNodesWithTag(new Tag("jeej")).execute(s);
        assertEquals(1, nodes.size());
    }

    @Test
    public void executeTestTag2() throws Exception {
        Set<Node> nodes = (Set<Node>) new RequestGetNodesWithTag(new Tag("batE")).execute(s);
        assertEquals(2, nodes.size());
    }

    @Test
    public void executeTestTag3() throws Exception {
        Set<Node> nodes = (Set<Node>) new RequestGetNodesWithTag(new Tag("batA")).execute(s);
        assertEquals(1, nodes.size());
    }

    @Test
    public void executeTestTag2AndTag1() throws Exception {
        Set<Tag> tags = new HashSet<>();
        tags.add(new Tag("batA"));
        tags.add(new Tag("jeej"));
        Set<Node> nodes = (Set<Node>) new RequestGetNodesWithTag(new Tag("batA")).execute(s);
        assertEquals(1, nodes.size());

    }
}