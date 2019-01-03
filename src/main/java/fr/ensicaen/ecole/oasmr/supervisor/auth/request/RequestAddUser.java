package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestAddUser extends Request {
    private String login;
    private String password;

    public RequestAddUser(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().addUser(new User(login, password));
        return null;
    }

    @Override
    public String toString() {
        return login + " RequestAddUser";
    }
}
