package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public class RequestForceScanCommands extends Request {
    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getCommandFinder().scan();
        return supervisor.getCommandFinder().getCommands().size();
    }

    @Override
    public String toString() {
        return "scan commands";
    }
}
