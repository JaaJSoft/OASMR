package fr.ensicaen.ecole.oasmr.supervisor.node.command.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;
import java.util.Set;

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
