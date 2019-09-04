/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
