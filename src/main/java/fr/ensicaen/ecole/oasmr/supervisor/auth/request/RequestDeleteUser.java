package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestDeleteUser extends Request {
    private String login;
    private String password;

    public RequestDeleteUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().deleteUser(new User(login, password));
        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestDeleteUser";
    }
}
