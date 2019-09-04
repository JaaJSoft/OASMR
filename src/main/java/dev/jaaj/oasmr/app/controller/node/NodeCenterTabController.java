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

import com.jfoenix.controls.*;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.View;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;

import java.io.IOException;

public class NodeCenterTabController extends View {

    @FXML
    JFXTabPane nodeCommandTabPane;

    private NodesModel nodesModel = null;

    private View nodeCommand;
    private View nodeCommandLog;
    private View nodeTaskManager;
    private View nodeTags;

    private Tab commands;
    private Tab logs;
    private Tab tasks;
    private Tab tags;

    public NodeCenterTabController(View parent) throws IOException {
        super("NodeCenterTab", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodeCommandTabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            nodeCommand = new NodeCommandController(this);
            addSubView(nodeCommand);
            nodeCommandLog = new NodeCommandLogController(this);
            addSubView(nodeCommandLog);
            nodeTaskManager = new NodeTaskManagerController(this);
            addSubView(nodeTaskManager);
            nodeTags = new NodeTagsController(this);
            addSubView(nodeTags);
            commands = new Tab();
            commands.setText("Commands");
            commands.setContent(nodeCommand.getRoot());
            logs = new Tab();
            logs.setText("Commands Logs");
            logs.setContent(nodeCommandLog.getRoot());
            tasks = new Tab();
            tasks.setText("Tasks manager");
            tasks.setContent(nodeTaskManager.getRoot());
            tags = new Tab();
            tags.setText("Tags");
            tags.setContent(nodeTags.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {

        if(nodesModel == null){
            nodesModel = NodesModel.getInstance();
        }

        nodeCommandTabPane.getTabs().clear();

        if (nodesModel.getSelectedAmount() > 1) {
            nodeCommandTabPane.getTabs().addAll(commands, logs, tags);
        }else{
            nodeCommandTabPane.getTabs().addAll(commands, logs, tasks, tags);
        }
    }

    @Override
    protected void onUpdate() {

    }


    @Override
    public void onStop() {

    }

}
