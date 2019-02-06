package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NodesModel {

    private ObservableList<NodeBean> allNodeBeans;

    private ObservableList<NodeBean> currentNodeBeans;

    public NodesModel() {
        this.allNodeBeans = FXCollections.observableArrayList();
        this.currentNodeBeans = FXCollections.observableArrayList();
    }

    public NodesModel(NodeBean[] nodeList) {
        this.allNodeBeans = FXCollections.observableArrayList(nodeList);
        this.currentNodeBeans = FXCollections.observableArrayList();
    }

    public void addCurrentNodes(NodeBean nodeBean){
        if(!currentNodeBeans.contains(nodeBean)){
            currentNodeBeans.add(nodeBean);
        }
    }

    public void removeCurrentNodes(NodeBean nodeBean){
        if(currentNodeBeans.contains(nodeBean)){
            currentNodeBeans.remove(nodeBean);
        }
    }

    public void refreshNodeBeanList(NodeBean[] nodeList){
        allNodeBeans.clear();
        currentNodeBeans.clear();
        allNodeBeans.addAll(nodeList);
    }

    public ObservableList<NodeBean> getAllNodeBeans(){
        return allNodeBeans;
    }

    public ObservableList<NodeBean> getCurrentNodeBeans(){
        return currentNodeBeans;
    }

    public int getSelectedAmount(){
        return currentNodeBeans.size();
    }

}
