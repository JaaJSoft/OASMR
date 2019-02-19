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
