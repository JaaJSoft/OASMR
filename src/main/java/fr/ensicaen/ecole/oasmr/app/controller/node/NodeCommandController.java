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

package fr.ensicaen.ecole.oasmr.app.controller.node;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.FXClassInitializer;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestGetCommands;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.Future;

public class NodeCommandController extends View {

    @FXML
    FlowPane commandFlowPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;

    public NodeCommandController(View parent) throws IOException {
        super("NodeCommand", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
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


        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {
            try {
                commandFlowPane.getChildren().clear();
                Future<? extends Serializable> reponseCommandList = requestManager.aSyncSendRequest(new RequestGetCommands());
                Set<Class<? extends Command>> commands = (Set<Class<? extends Command>>) reponseCommandList.get();
                for (Class<? extends Command> command : commands) {
                    JFXButton jeej = initButtonFromClass(command);
                    commandFlowPane.getChildren().add(jeej);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private JFXButton initButtonFromClass(Class<? extends Command> command) {
        JFXButton jeej = new JFXButton(command.getSimpleName());
        jeej.setStyle("-jfx-button-type: RAISED;-fx-background-color: #FF6026; -fx-text-fill: white;");
        jeej.setOnAction(e -> {
            Stage stage = (Stage) commandFlowPane.getScene().getWindow();

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
