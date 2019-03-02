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
        currentNodeData.remove(nodeData);
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
