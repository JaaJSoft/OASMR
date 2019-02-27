package fr.ensicaen.ecole.oasmr.supervisor.node.command;

import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;

import java.io.Serializable;

public class CommandNodeUpdateData extends CommandNode {
    private final NodeData newData;

    public CommandNodeUpdateData(NodeData newData) {
        this.newData = newData;
    }

    @Override
    public Serializable execute(Node node) throws Exception {
        node.setData(newData);
        return 0;
    }

    @Override
    public String toString() {
        return "update nodeData";
    }
}
