package dev.jaaj.oasmr.supervisor.auth.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestSetAdmin extends Request {
    private final String login;
    private final boolean admin;

    public RequestSetAdmin(String login, boolean admin){
        this.login = login;
        this.admin = admin;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().getUser(login).setAdmin(admin);
        supervisor.getUserList().saveUsers();

        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestSetAdmin " + admin;
    }
}
//to test