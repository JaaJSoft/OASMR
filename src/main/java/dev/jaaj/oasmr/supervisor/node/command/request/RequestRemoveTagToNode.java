package dev.jaaj.oasmr.supervisor.node.command.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.node.Node;
import dev.jaaj.oasmr.supervisor.node.Tag;

import java.io.Serializable;

public class RequestRemoveTagToNode extends Request {

    private final Integer id;
    private final Tag t;

    public RequestRemoveTagToNode(Integer id, Tag tag) {
        this.id = id;
        t = tag;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        Node n = supervisor.getNodeFlyweightFactory().getNode(id);
        n.removeTag(t);
        return 0;
    }

    @Override
    public String toString() {
        return "add tag " + t + "to " + id;
    }

}
