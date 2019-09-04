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

package dev.jaaj.oasmr.app.view;

import dev.jaaj.oasmr.supervisor.node.NodeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

import java.util.Arrays;

public class NodesModel {

    private static NodesModel instance = null;

    private ObservableSet<NodeData> allNodeData;

    private ObservableSet<NodeData> currentNodeData;

    public static NodesModel getInstance() {
        if (instance == null) {
            instance = new NodesModel();
        }
        return instance;
    }

    private NodesModel() {
        this.allNodeData = FXCollections.observableSet();
        this.currentNodeData = FXCollections.observableSet();
    }

    public void addCurrentNodes(NodeData nodeData) {
        if (!currentNodeData.contains(nodeData)) {
            currentNodeData.add(nodeData);
        }
    }

    public void removeCurrentNodes(NodeData nodeData) {
        currentNodeData.remove(nodeData);
    }

    public void update(NodeData[] nodeList) {
        allNodeData.addAll(Arrays.asList(nodeList));
    }

    public ObservableSet<NodeData> getAllNodeData() {
        return allNodeData;
    }

    public ObservableSet<NodeData> getCurrentNodeData() {
        return currentNodeData;
    }

    public int getSelectedAmount() {
        return currentNodeData.size();
    }

    public int getTotalAmount() {
        return allNodeData.size();
    }

}
