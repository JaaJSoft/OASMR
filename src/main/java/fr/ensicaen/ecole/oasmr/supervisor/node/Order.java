package fr.ensicaen.ecole.oasmr.supervisor.node;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;

public abstract class Order extends Command {
    @Override
    public Serializable execute(Object... params) throws Exception {
        return execute((Node) params[0]);
    }

    public abstract Serializable execute(Node node);

}
