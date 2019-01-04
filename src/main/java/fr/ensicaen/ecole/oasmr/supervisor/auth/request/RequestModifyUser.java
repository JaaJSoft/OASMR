package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestModifyUser extends Request {
    private String login;
    private String password;
    private String newLogin;
    private String newPassword;

    public RequestModifyUser(String login, String password, String newLogin, String newPassword) {
        this.login = login;
        this.password = password;
        this.newLogin = newLogin;
        this.newPassword = newPassword;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().modifyUser(new User(login, password), new User(newLogin, newPassword));
        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestModifyUser";
    }
}
