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
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.FXClassInitializer;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

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

        if(requestManager == null){
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
            nodeCommandTabPane.getTabs().clear();
            Tab t = new Tab();
            t.setText("Module 1");
            t.setContent(new Label("Insert module core"));
            Tab t2 = new Tab();
            t2.setText("Module 2");
            t2.setContent(new Label("Insert module core"));
            nodeCommandTabPane.getTabs().addAll(t, t2);

            JFXButton jeej = new JFXButton("echo on node");
            jeej.setStyle("-jfx-button-type: RAISED;-fx-background-color: #FF6026; -fx-text-fill: white;");
            jeej.setOnAction(e -> {
                Stage stage = (Stage) nodeCommandTabPane.getScene().getWindow();

                new FXClassInitializer(stage, CommandEchoString.class).initFromClass(newObject -> {
                    Command c = (Command) newObject;
                    String response = null;
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
            t.setContent(jeej);
        }

    }

    @Override
    public void onStop() {

    }

}
