package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class RequestGetAllTags extends Request {
    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Set<Node> nodes = supervisor.getNodeFlyweightFactory().getNodes();
        Set<Tag> tags = new HashSet<>();
        for (Node n : nodes) {
            tags.addAll(n.getTags());
        }
        return (Serializable) tags;
    }

    @Override
    public String toString() {
        return "Get tags";
    }
}
