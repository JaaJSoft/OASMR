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

import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NodeSystemInfoController extends View {

    @FXML
    VBox nodeSystemInfoVBox;

    private NodesModel nodesModel;
    private View nodeRamInfo;
    private View nodeCpuInfo;
    private View nodeFileExplorer;

    public NodeSystemInfoController(View parent) throws IOException {
        super("NodeSystemInfo", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodesModel = NodesModel.getInstance();
            nodeRamInfo = new NodeRamInfoController(this);
            addSubView(nodeRamInfo);
            nodeCpuInfo = new NodeCpuInfoController(this);
            addSubView(nodeCpuInfo);
            nodeFileExplorer = new NodeFileExplorerController(this);
            addSubView(nodeFileExplorer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        if(nodesModel.getSelectedAmount() > 1){
            //TODO : generate group view
        }else{
            nodeSystemInfoVBox.getChildren().clear();
            nodeSystemInfoVBox.getChildren().add(nodeCpuInfo.getRoot());
            nodeSystemInfoVBox.getChildren().add(nodeRamInfo.getRoot());
            nodeSystemInfoVBox.getChildren().add(nodeFileExplorer.getRoot());
        }
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }
}
