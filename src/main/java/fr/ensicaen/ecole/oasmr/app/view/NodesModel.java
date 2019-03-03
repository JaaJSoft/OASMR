package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodesModel {

    private static NodesModel instance = null;

    private ObservableList<NodeData> allNodeData;

    private ObservableList<NodeData> currentNodeData;

    public static NodesModel getInstance(){
        if(instance == null){
            instance = new NodesModel();
        }
        return instance;
    }

    private NodesModel() {
        this.allNodeData = FXCollections.observableArrayList();
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

    public void update(NodeData[] nodeList){
        currentNodeData.clear();
        allNodeData.clear();
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

    public int getTotalAmount(){
        return allNodeData.size();
    }

}
