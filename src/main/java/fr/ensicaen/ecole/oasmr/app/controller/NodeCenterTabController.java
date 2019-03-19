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
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Set;

public class NodeCenterTabController extends View {

    @FXML
    JFXTabPane nodeCommandTabPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;

    private View nodeCommand;
    private View nodeCommandLog;
    private View nodeTaskManager;
    private View nodeTags;

    public NodeCenterTabController(View parent) throws IOException {
        super("NodeCenterTab", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodesModel = NodesModel.getInstance();
            nodeCommandTabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            nodeCommand = new NodeCommandController(this);
            addSubView(nodeCommand);
            nodeCommandLog = new NodeCommandLogController(this);
            addSubView(nodeCommandLog);
            nodeTaskManager = new NodeTaskManagerController(this);
            addSubView(nodeTaskManager);
            nodeTags = new NodeTagsController(this);
            addSubView(nodeTags);
            Tab t = new Tab();
            t.setText("Commands");
            t.setContent(nodeCommand.getRoot());
            Tab t2 = new Tab();
            t2.setText("Commands Logs");
            t2.setContent(nodeCommandLog.getRoot());
            Tab t3 = new Tab();
            t3.setText("Tasks manager");
            t3.setContent(nodeTaskManager.getRoot());
            Tab t4 = new Tab();
            t4.setText("Tags");
            t4.setContent(nodeTags.getRoot());
            nodeCommandTabPane.getTabs().addAll(t, t2, t3, t4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {

    }



    @Override
    public void onStop() {

    }

}
