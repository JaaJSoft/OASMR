package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestNodeUpdateName extends Request {
    private final Integer id;
    private final String newName;

    public RequestNodeUpdateName(Integer id, String newName) {
        this.id = id;
        this.newName = newName;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getNodeFlyweightFactory().getNode(id).setName(newName);
        return 0;
    }

    @Override
    public String toString() {
        return null;
    }
}
