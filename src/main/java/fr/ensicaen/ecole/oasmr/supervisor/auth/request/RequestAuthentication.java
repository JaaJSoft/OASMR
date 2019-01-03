package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.HashUtil;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestAuthentication extends Request {
    private String login;
    private String password;

    public RequestAuthentication(String login, String password){
        this.login = login;
        this.password = HashUtil.get_SHA_SecurePassword(password, "SHA-256");;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        if (supervisor.getUserList().authenticate(login, password)){
            supervisor.authentication();
        }
        return null;
    }

    @Override
    public String toString() {
        return login + " AuthenticationRequest";
    }
}
