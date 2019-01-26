package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestModifyUserLogin extends Request {
    private String login;
    private String newLogin;

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
        return login + " RequestModifyUserLogin";
    }
}
