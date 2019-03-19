package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestGetLoginList extends Request {
    public RequestGetLoginList() {
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return (Serializable) supervisor.getUserList().getLoginList();
    }

    @Override
    public String toString() {
        return "RequestGetUserList";
    }
}
