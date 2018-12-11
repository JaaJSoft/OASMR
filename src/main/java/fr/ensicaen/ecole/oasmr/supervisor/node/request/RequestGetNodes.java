package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestGetNodes extends Request {

    @Override
    public Serializable execute(Supervisor supervisor) {
        return (Serializable) supervisor.getNodeFlyweightFactory().getNodes();
    }

    @Override
    public String toString() {
        return "Get Nodes";
    }
}
