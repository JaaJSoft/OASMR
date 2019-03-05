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

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.app.view.exception.ExceptionSceneNotFound;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class MainController extends View {

    @FXML
    SplitPane mainPane;
    @FXML
    MenuItem menuUserAdmin;

    @FXML
    MenuItem menuConfig;

    private NodesModel nodesModel;

    private View defaultView;
    private View nodeListView;
    private View nodeView;
    private SceneManager sceneManager;
    private RequestManager requestManager;
    private Config config;

    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    @Override
    public void onCreate() {
        sceneManager = SceneManager.getInstance();
        try {
            nodeListView = new NodeListController(this);
            addSubView(nodeListView);
            nodeView = new NodeViewController(this);
            addSubView(nodeView);
            defaultView = new DefaultController(this);

            mainPane.setDividerPositions(0.2);
            mainPane.getItems().add(nodeListView.getRoot());
            mainPane.getItems().add(defaultView.getRoot());

            nodesModel = NodesModel.getInstance();
            nodesModel.getCurrentNodeData().addListener((ListChangeListener.Change<? extends NodeData> c) -> {
                onStart();
            });

            menuUserAdmin.setOnAction(actionEvent -> {
                try {
                    sceneManager.setScenes(UserManagementController.class);
                } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                    exceptionSceneNotFound.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

        try {
            config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
        if (nodesModel.getSelectedAmount() > 0) {
            mainPane.getItems().set(1, nodeView.getRoot());
            nodeView.onLoad();
        } else {
            mainPane.getItems().set(1, defaultView.getRoot());
        }
    }

    @Override
    public void onStop() {

    }
}
