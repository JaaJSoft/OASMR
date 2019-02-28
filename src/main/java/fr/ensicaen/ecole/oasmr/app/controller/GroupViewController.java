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

import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;


public class GroupViewController extends View {

    @FXML
    Text nodeName;

    @FXML
    Text nodeId;

    @FXML
    JFXTabPane moduleTabPane;

    @FXML
    VBox rightVBox;

    @FXML
    VBox mainVBox;

    @FXML
    AnchorPane bottomPane;

    private NodesModel nodesModel;
    private RequestManager requestManager;

    public GroupViewController() throws IOException {
        super("GroupView");
        onCreate();
    }

    public void setDataModel(NodesModel nodesModel) {
        nodesModel = nodesModel;
    }

    private void updateNodesInfo(){
        nodeName.setText("Group : ");
        for(NodeData node : nodesModel.getCurrentNodeData()) {
            nodeName.setText(nodeName.getText() + node.toString() + " ");
        }
    }

    private void updateModuleTab() {

    }

    private void updateNodeTerm() {

    }

    private void updateRightInfo() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        updateNodesInfo();
        updateModuleTab();
        updateNodeTerm();
        updateRightInfo();
    }

    @Override
    public void onStop() {

    }
}
