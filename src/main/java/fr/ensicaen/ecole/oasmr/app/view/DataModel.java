package fr.ensicaen.ecole.oasmr.app.view;

import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataModel {

    private ObjectProperty<GroupBean> groupBeanObjectProperty;
    private ObservableList<NodeBean> currentNodeBeans;

    public DataModel() {
        this.currentNodeBeans = FXCollections.observableArrayList();
    }

    public DataModel(GroupBean groupBean) {
        this.groupBeanObjectProperty = new SimpleObjectProperty<>(groupBean);
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

    public ObservableList<NodeBean> getCurrentNodeBeans(){
        return currentNodeBeans;
    }

    public ObjectProperty<GroupBean> getCurrentGroupBeanObjectProperty(){
        return groupBeanObjectProperty;
    }

    public GroupBean getCurrentGroupBean(){
        return groupBeanObjectProperty.get();
    }

    public void setGroupBean(GroupBean groupBean){
        groupBeanObjectProperty.setValue(groupBean);
    }

    public int getSelectedAmount(){
        return currentNodeBeans.size();
    }

}
