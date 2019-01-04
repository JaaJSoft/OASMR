package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.beans.Node;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class DataModel {

    private ObservableList<Node> nodeList;
    private ObservableList<Node> currentNodes;
    private ObjectProperty<Node> currentNode;

    public DataModel() {
        this.nodeList = FXCollections.observableArrayList();
        this.currentNodes = FXCollections.observableArrayList();
        this.currentNode = new SimpleObjectProperty<>();
    }

    public DataModel(List<Node> nodeList) {
        this.nodeList = FXCollections.observableArrayList(nodeList);
        this.currentNodes = FXCollections.observableArrayList();
        this.currentNode = new SimpleObjectProperty<>();
    }

    public List<Node> getCurrentNodes(){
        return currentNodes;
    }

    public void addCurrentNodes(Node node){
        if(!currentNodes.contains(node) && nodeList.contains(node)){
            currentNodes.add(node);
        }
    }

    public void removeCurrentNodes(Node node){
        if(currentNodes.contains(node) && nodeList.contains(node)){
            currentNodes.remove(node);
        }
    }

    public ObjectProperty<Node> nodeProperty(){
        return currentNode;
    }

    public Node getCurrentNode(){
        return currentNode.get();
    }

    public void setCurrentNode(Node node){
        currentNode.setValue(node);
    }

}
