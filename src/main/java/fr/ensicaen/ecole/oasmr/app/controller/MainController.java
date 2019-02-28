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
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainController extends View {

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private NodesModel nodesModel;

    private NodeListController nodeListView;
    private NodeViewController nodeView;
    private GroupViewController groupView;
    private DefaultController defaultView;


    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    @Override
    public void onCreate() {
        try {
            nodeListView = new NodeListController();
            nodeListView.onCreate();
            nodeView = new NodeViewController();
            nodeView.onCreate();
            groupView = new GroupViewController();
            groupView.onCreate();
            defaultView = new DefaultController();
            defaultView.onCreate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        try {
            Config config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }

        try {
            mainPane.getItems().clear();
            mainPane.setDividerPositions(0.2);
            NodeData[] nodeList = (NodeData[]) requestManager.sendRequest(new RequestGetNodes());
            nodesModel = new NodesModel(nodeList);
            mainPane.getItems().add(0, nodeListView.getRoot());
            mainPane.getItems().add(1, defaultView.getRoot());
            nodesModel.getCurrentNodeData().addListener((ListChangeListener.Change<? extends NodeData> c) -> {
                if (nodesModel.getSelectedAmount() > 1) {
                    groupView.onStart();
                    mainPane.getItems().set(1, groupView.getRoot());
                } else if (nodesModel.getSelectedAmount() == 1) {
                    nodeView.onStart();
                    mainPane.getItems().set(1, nodeView.getRoot());
                } else {
                    mainPane.getItems().set(1, defaultView.getRoot());
                }
            });
            nodeView.setDataModel(nodesModel);
            nodeListView.setMainController(this);
            nodeListView.setDataModel(nodesModel);
            groupView.setDataModel(nodesModel);
            nodeListView.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
