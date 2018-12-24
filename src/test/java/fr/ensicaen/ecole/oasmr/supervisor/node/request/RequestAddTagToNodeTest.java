package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.*;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class RequestAddTagToNodeTest {
    private Node n;
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
        n = s.getNodeFlyweightFactory().getNode(InetAddress.getByName("127.02.20.5"), 5869);
    }

    @Test
    public void executeAddTagToNode() throws Exception {
        Set<Tag> old = new HashSet<>(n.getTags());
        new RequestAddTagToNode(n.getId(), new Tag("jeej")).execute(s);
        Set<Tag> newTag = n.getTags();
        assertEquals(old.size() + 1, newTag.size());
    }
}