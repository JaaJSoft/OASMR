package fr.ensicaen.ecole.oasmr.app.beans;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Group {

    private String name;
    private int id;
    private ObservableList<Node> nodeList;

    public Group(String name, int id) {
        this.name = name;
        this.id = id;
        nodeList = FXCollections.observableArrayList();
    }


    public Group(String name, int id, List<Node> nodeList) {
        this.name = name;
        this.id = id;
        this.nodeList = FXCollections.observableArrayList(nodeList);
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return name;
    }

    public void addNode(Node node){
        nodeList.add(node);
    }

    public ObservableList<Node> getNodes(){
        return nodeList;
    }

}
