package fr.ensicaen.ecole.oasmr.app.beans;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class GroupBean {

    private String name;
    private int id;
    private ObservableList<NodeBean> nodeBeanList;

    public GroupBean(String name, int id) {
        this.name = name;
        this.id = id;
        nodeBeanList = FXCollections.observableArrayList();
    }


    public GroupBean(String name, int id, List<NodeBean> nodeBeanList) {
        this.name = name;
        this.id = id;
        this.nodeBeanList = FXCollections.observableArrayList(nodeBeanList);
    }

    public int getId() {
        return id;
    }

    public String toString(){
        return name;
    }

    public void addNode(NodeBean nodeBean){
        nodeBeanList.add(nodeBean);
    }

    public ObservableList<NodeBean> getNodes(){
        return nodeBeanList;
    }

}
