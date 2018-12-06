package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestGetNodes extends Request {

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return supervisor.getNodes();
    }

    @Override
    public String toString() {
        return "Get Nodes";
    }
}
