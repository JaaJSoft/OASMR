package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.Serializable;
import java.util.Set;

public class RequestGetCommands extends Request {

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception { //TODO FIX THAT SHIT
        return (Serializable) supervisor.getCommands();
    }

    @Override
    public String toString() {
        return "get commands";
    }
}
