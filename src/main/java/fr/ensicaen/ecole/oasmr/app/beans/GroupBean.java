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

package fr.ensicaen.ecole.oasmr.app.beans;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GroupBean {

    private String name;
    private int id;
    private ObservableList<NodeData> nodeDataList;

    public GroupBean(String name, int id) {
        this.name = name;
        this.id = id;
        nodeDataList = FXCollections.observableArrayList();
    }


    public GroupBean(String name, int id, List<NodeData> nodeDataList) {
        this.name = name;
        this.id = id;
        this.nodeDataList = FXCollections.observableArrayList(nodeDataList);
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return name;
    }

    public void addNode(NodeData nodeData){
        nodeDataList.add(nodeData);
    }

    public ObservableList<NodeData> getNodes(){
        return nodeDataList;
    }

}
