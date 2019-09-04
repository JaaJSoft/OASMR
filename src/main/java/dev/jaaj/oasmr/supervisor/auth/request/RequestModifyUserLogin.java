package dev.jaaj.oasmr.supervisor.auth.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestModifyUserLogin extends Request {
    private final String login;
    private final String newLogin;

    public RequestModifyUserLogin(String login, String newLogin) {
        this.login = login;
        this.newLogin = newLogin;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().modifyUserLogin(login, newLogin);
        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestModifyUserLogin " + newLogin;
    }
}
