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

package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodesModel {

    private ObservableList<NodeData> allNodeData;

    private ObservableList<NodeData> currentNodeData;

    public NodesModel() {
        this.allNodeData = FXCollections.observableArrayList();
        this.currentNodeData = FXCollections.observableArrayList();
    }

    public NodesModel(NodeData[] nodeList) {
        this.allNodeData = FXCollections.observableArrayList(nodeList);
        this.currentNodeData = FXCollections.observableArrayList();
    }

    public void addCurrentNodes(NodeData nodeData){
        if(!currentNodeData.contains(nodeData)){
            currentNodeData.add(nodeData);
        }
    }

    public void removeCurrentNodes(NodeData nodeData){
        if(currentNodeData.contains(nodeData)){
            currentNodeData.remove(nodeData);
        }
    }

    public void refreshNodeBeanList(NodeData[] nodeList){
        allNodeData.clear();
        currentNodeData.clear();
        allNodeData.addAll(nodeList);
    }

    public ObservableList<NodeData> getAllNodeData(){
        return allNodeData;
    }

    public ObservableList<NodeData> getCurrentNodeData(){
        return currentNodeData;
    }

    public int getSelectedAmount(){
        return currentNodeData.size();
    }

}
