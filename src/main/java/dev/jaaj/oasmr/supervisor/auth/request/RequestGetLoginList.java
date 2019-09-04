package dev.jaaj.oasmr.supervisor.auth.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;

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
