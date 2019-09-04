package dev.jaaj.oasmr.supervisor.node.command.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.node.Node;
import dev.jaaj.oasmr.supervisor.node.Tag;

import java.io.Serializable;
import java.util.Set;

public class RequestRemoveTagsToNode extends Request {

    private final Integer id;
    private final Set<Tag> t;

    public RequestRemoveTagsToNode(Integer id, Set<Tag> tags) {
        this.id = id;
        t = tags;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Node n = supervisor.getNodeFlyweightFactory().getNode(id);
        n.removeTags(t);
        return 0;
    }

    @Override
    public String toString() {
        return "remove tags to " + id;
    }

}
