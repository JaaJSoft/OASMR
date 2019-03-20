package fr.ensicaen.ecole.oasmr.app.controller.node;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

final class CommandAdapterTableView extends RecursiveTreeObject<CommandAdapterTableView> {
    private final Command command;

    public CommandAdapterTableView(Command command) {
        this.command = command;
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
