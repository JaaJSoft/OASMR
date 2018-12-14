package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestGetNodeswithTag extends Request {
    private final Set<Tag> tags;

    public RequestGetNodeswithTag(Set<Tag> tags) {
        this.tags = tags;
    }

    public RequestGetNodeswithTag(Tag t) {
        tags = new HashSet<>();
        tags.add(t);
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return (Serializable) supervisor.getNodeFlyweightFactory().getNodes().parallelStream()
            .filter(n -> n.getTags().containsAll(tags)).collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        return "get nodes with tags " + tags;
    }
}
