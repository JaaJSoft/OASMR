package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;

import java.io.Serializable;

public abstract class Request extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        return execute((Supervisor) params[0]);
    }

    public abstract Serializable execute(Supervisor supervisor) throws Exception;

}
