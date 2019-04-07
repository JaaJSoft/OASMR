package fr.ensicaen.ecole.oasmr.supervisor.node.command.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

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
