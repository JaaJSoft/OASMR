package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestModifyUserPassword extends Request {
    private String login;
    private String password;
    private String newPassword;

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
