package dev.jaaj.oasmr.supervisor.auth.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestGetAdmin extends Request {
    private final String login;

    public RequestGetAdmin(String login) {
        this.login = login;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return supervisor.getUserList().getUser(login).isAdmin();
    }

    @Override
    public String toString() {
        return login + " RequestGetAdmin";
    }
}
//to test