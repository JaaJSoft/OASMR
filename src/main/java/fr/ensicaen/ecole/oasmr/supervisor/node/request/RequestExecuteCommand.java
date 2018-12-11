package fr.ensicaen.ecole.oasmr.supervisor.node.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestExecuteCommand extends Request {
    private final Command command;
    private final Integer id;

    public RequestExecuteCommand(Integer id, Command command) {
        this.command = command;
        this.id = id;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        return supervisor.getNodeFlyweightFactory().getNode(id).executeCommand(command);
    }

    @Override
    public String toString() {
        return "execute : " + command;
    }

}
