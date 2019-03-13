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
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class NodeViewController extends View {

    @FXML
    VBox mainVBox;

    @FXML
    SplitPane vSplitPane;

    @FXML
    SplitPane hSplitPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;

    private View nodeInfoView;
    private View nodeModuleView;
    private View nodeTermView;
    private View nodeSystemInfo;


    public NodeViewController(View parent) throws IOException {
        super("NodeView", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodesModel = NodesModel.getInstance();
            nodeInfoView = new NodeInfoController(this);
            addSubView(nodeInfoView);
            nodeModuleView = new NodeCommandModuleController(this);
            addSubView(nodeModuleView);
            nodeTermView = new NodeTerminalController(this);
            addSubView(nodeTermView);
            nodeSystemInfo = new NodeSystemInfoController(this);
            addSubView(nodeSystemInfo);
            mainVBox.getChildren().add(nodeInfoView.getRoot());
            mainVBox.getChildren().add(new Separator());
            mainVBox.getChildren().add(nodeModuleView.getRoot());
            vSplitPane.getItems().add(nodeTermView.getRoot());
            vSplitPane.setDividerPositions(0.75);
            hSplitPane.getItems().add(nodeSystemInfo.getRoot());
            hSplitPane.setDividerPositions(0.8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

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
        } else {
            //TODO : Configure view for node
        }
    }

    @Override
    public void onStop() {

    }

}
