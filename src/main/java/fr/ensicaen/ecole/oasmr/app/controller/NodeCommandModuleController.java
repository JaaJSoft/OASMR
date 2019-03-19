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

package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.FXClassInitializer;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.command.CommandGetExecutorCommandHistory;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestGetCommands;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeCommandModuleController extends View {

    @FXML
    JFXTabPane nodeCommandTabPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;

    public NodeCommandModuleController(View parent) throws IOException {
        super("NodeCommandModule", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
        nodeCommandTabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    @Override
    protected void onStart() {

        if (requestManager == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }


        Future<? extends Serializable> reponseCommandList = requestManager.aSyncSendRequest(new RequestGetCommands());

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {
            Future<? extends Serializable> reponseCommandHist = requestManager.aSyncSendRequest(
                    new RequestExecuteCommand(nodesModel.getCurrentNodeData().get(0).getId(), new CommandGetExecutorCommandHistory())
            );// TODO forall selected nodes
            nodeCommandTabPane.getTabs().clear();
            Tab t = new Tab();
            t.setText("Commands");
            t.setContent(new Label("Insert module core"));
            Tab t2 = new Tab();
            t2.setText("Commands Logs");
            t2.setContent(new Label("Insert module core"));
            Tab t3 = new Tab();
            t3.setText("Tasks manager");
            t3.setContent(new Label("Insert module core"));
            Tab t4 = new Tab();
            t4.setText("Tags");
            t4.setContent(new Label("Insert module core"));
            nodeCommandTabPane.getTabs().addAll(t, t2, t3, t4);

            FlowPane flowPane = new FlowPane();
            flowPane.setPadding(new Insets(10));
            flowPane.setVgap(8);
            flowPane.setHgap(8);
            try {
                Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) reponseCommandList.get();
                for (Class<? extends Command> command : commands) {
                    JFXButton jeej = initButtonFromClass(command);
                    flowPane.getChildren().add(jeej);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            t.setContent(flowPane);
            try {
                JFXTreeTableColumn<CommandAdapterTableView, String> commandColumn = new JFXTreeTableColumn<>("Commands");
                commandColumn.setPrefWidth(150);
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


                Command[] commands = (Command[]) reponseCommandHist.get();
                ObservableList<CommandAdapterTableView> commandsList = FXCollections.observableArrayList();
                for (Command command : commands) {
                    commandsList.add(new CommandAdapterTableView(command));
                }
                final TreeItem<CommandAdapterTableView> root = new RecursiveTreeItem<>(commandsList, RecursiveTreeObject::getChildren);

                JFXTreeTableView<CommandAdapterTableView> commandHistTableView = new JFXTreeTableView<>(root);
                commandHistTableView.getColumns().addAll(commandColumn, stateColumn);
                commandHistTableView.setShowRoot(false);

                t2.setContent(commandHistTableView);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private JFXButton initButtonFromClass(Class<? extends Command> command) {
        JFXButton jeej = new JFXButton(command.getSimpleName());
        jeej.setStyle("-jfx-button-type: RAISED;-fx-background-color: #FF6026; -fx-text-fill: white;");
        jeej.setOnAction(e -> {
            Stage stage = (Stage) nodeCommandTabPane.getScene().getWindow();

            new FXClassInitializer(stage, command).initFromClass(newObject -> {
                Command c = (Command) newObject;
                String response;
                try {
                    response = (String) requestManager.sendRequest(
                            new RequestExecuteCommand(nodesModel.getCurrentNodeData().get(0).getId(), c));
                    System.out.println(response);

                    JFXDialogLayout layout = new JFXDialogLayout();
                    layout.setHeading(new Label("Response"));
                    layout.setBody(new Label(response));

                    JFXAlert alert = new JFXAlert<>(stage);
                    alert.setOverlayClose(true);
                    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert.setContent(layout);
                    alert.initModality(Modality.NONE);

                    JFXButton button = new JFXButton("close");
                    button.setOnAction(event -> alert.close());
                    layout.setActions(button);
                    alert.show();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
        });
        return jeej;
    }

    @Override
    public void onStop() {

    }

}
