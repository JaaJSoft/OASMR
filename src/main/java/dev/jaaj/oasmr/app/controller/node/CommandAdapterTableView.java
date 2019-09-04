package dev.jaaj.oasmr.app.controller.node;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dev.jaaj.oasmr.lib.command.Command;
import dev.jaaj.oasmr.supervisor.node.NodeData;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

final class CommandAdapterTableView extends RecursiveTreeObject<CommandAdapterTableView> {
    private final Command command;
    private final NodeData node;

    public CommandAdapterTableView(Command command, NodeData node) {
        this.command = command;
        this.node = node;
    }

    public StringProperty nodeName(){
        return new SimpleStringProperty(node.getName());
    }

    public StringProperty commandName() {
        return new SimpleStringProperty(command.getClass().getSimpleName());
    }

    public StringProperty stateName() {
        return new SimpleStringProperty(command.getState().getStateInString());
    }

    public StringProperty response() {
        return new SimpleStringProperty(command.getState().getStateOutput());
    }

    @Override
    public String toString() {
        return command.toString();
    }
}
