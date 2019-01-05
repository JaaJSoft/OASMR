package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class DataModel {

    private ObservableList<GroupBean> groupBeanList;
    private ObservableList<GroupBean> currentGroupBean;
    private ObservableList<NodeBean> currentNodeBeans;

    public DataModel() {
        this.groupBeanList = FXCollections.observableArrayList();
        this.currentNodeBeans = FXCollections.observableArrayList();
        this.currentGroupBean = FXCollections.observableArrayList();
    }

    public DataModel(List<GroupBean> groupBeanList) {
        this.groupBeanList = FXCollections.observableArrayList(groupBeanList);
        this.currentNodeBeans = FXCollections.observableArrayList();
        this.currentGroupBean = FXCollections.observableArrayList();
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

    public ObservableList<GroupBean> getGroupBeanList(){
        return groupBeanList;
    }

    public ObservableList<GroupBean> getCurrentGroupBeans(){
        return currentGroupBean;
    }


    public ObservableList<NodeBean> getCurrentNodeBeans(){
        return currentNodeBeans;
    }

    public int getSelectedAmount(){
        return currentNodeBeans.size();
    }

}
