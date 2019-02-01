package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

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