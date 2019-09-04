package dev.jaaj.oasmr.supervisor.auth.request;

import dev.jaaj.oasmr.supervisor.request.Request;
import dev.jaaj.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestModifyUserPassword extends Request {
    private final String login;
    private final String password;
    private final String newPassword;

    public RequestModifyUserPassword(String login, String password , String newPassword) {
        this.login = login;
        this.password = password;
        this.newPassword = newPassword;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().modifyUserPassword(login, password, newPassword);
        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestModifyUserLogin";
    }
}
