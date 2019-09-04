/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.jaaj.oasmr.app.controller.node;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dev.jaaj.oasmr.lib.command.Command;
import dev.jaaj.oasmr.lib.command.CommandGetExecutorCommandHistory;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.supervisor.node.NodeData;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeCommandLogController extends View {

    @FXML
    VBox commandLogVBox;

    private RequestManager requestManager = null;
    private Config config = null;
    private NodesModel nodesModel = null;

    private ObservableList<CommandAdapterTableView> commandsList;

    public NodeCommandLogController(View parent) throws IOException {
        super("NodeCommandLog", parent);
        onCreate();
        commandsList = FXCollections.observableArrayList();
    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
                nodesModel = NodesModel.getInstance();
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        JFXTreeTableColumn<CommandAdapterTableView, String> nodeColumn = new JFXTreeTableColumn<>("Node");
        nodeColumn.setPrefWidth(300);
        nodeColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CommandAdapterTableView, String> param) -> {
            if (nodeColumn.validateValue(param)) {
                return param.getValue().getValue().nodeName();
            } else {
                return nodeColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<CommandAdapterTableView, String> commandColumn = new JFXTreeTableColumn<>("Commands");
        commandColumn.setPrefWidth(300);
        commandColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CommandAdapterTableView, String> param) -> {
            if (commandColumn.validateValue(param)) {
                return param.getValue().getValue().commandName();
            } else {
                return commandColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<CommandAdapterTableView, String> stateColumn = new JFXTreeTableColumn<>("State");
        stateColumn.setPrefWidth(150);
        stateColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CommandAdapterTableView, String> param) -> {
            if (stateColumn.validateValue(param)) {
                return param.getValue().getValue().stateName();
            } else {
                return stateColumn.getComputedValue(param);
            }
        });

        JFXTreeTableColumn<CommandAdapterTableView, String> responseColumn = new JFXTreeTableColumn<>("Response");
        responseColumn.setPrefWidth(300);
        responseColumn.setCellValueFactory((TreeTableColumn.CellDataFeatures<CommandAdapterTableView, String> param) -> {
            if (responseColumn.validateValue(param)) {
                return param.getValue().getValue().response();
            } else {
                return responseColumn.getComputedValue(param);
            }
        });

        commandLogVBox.getChildren().clear();

        if (nodesModel.getSelectedAmount() > 0) {
            try {
                for(NodeData n : nodesModel.getCurrentNodeData()){
                    Future<? extends Serializable> reponseCommandHist = requestManager.aSyncSendRequest(
                            new RequestExecuteCommand(n.getId(), new CommandGetExecutorCommandHistory())
                    );

                    Command[] commands = (Command[]) reponseCommandHist.get();
                    for (Command command : commands) {
                        commandsList.add(new CommandAdapterTableView(command, n));
                    }
                }
                final TreeItem<CommandAdapterTableView> root = new RecursiveTreeItem<>(commandsList, RecursiveTreeObject::getChildren);
                JFXTreeTableView<CommandAdapterTableView> commandHistTableView = new JFXTreeTableView<>(root);
                if(nodesModel.getSelectedAmount() > 1){
                    commandHistTableView.getColumns().add(nodeColumn);
                }
                commandHistTableView.getColumns().addAll(commandColumn, stateColumn, responseColumn);
                commandHistTableView.setShowRoot(false);
                commandLogVBox.getChildren().add(commandHistTableView);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onUpdate() {
        if (nodesModel.getSelectedAmount() == 1) {
            try {
                for(NodeData n : nodesModel.getCurrentNodeData()){
                    Future<? extends Serializable> reponseCommandHist = requestManager.aSyncSendRequest(
                            new RequestExecuteCommand(n.getId(), new CommandGetExecutorCommandHistory())
                    );
                    commandsList.removeAll(commandsList);
                    Command[] commands = (Command[]) reponseCommandHist.get();
                    for (Command command : commands) {
                        commandsList.add(new CommandAdapterTableView(command, n));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStop() {
        config = null;
        requestManager = null;
        nodesModel = null;
    }
}
