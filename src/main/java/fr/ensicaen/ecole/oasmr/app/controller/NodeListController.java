package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXListView;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class NodeListController implements Initializable {

    @FXML
    VBox vbox;

    @FXML
    JFXListView<ElementListView<GroupBean, NodeBean>> listView;

    private DataModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public ObservableList<ElementListView<GroupBean, NodeBean>> generateListView(){
        List<ElementListView<GroupBean, NodeBean>> list = new ArrayList<>();
        for (GroupBean g : model.getGroupBeanList()){
            ElementListView<GroupBean, NodeBean> subList = new ElementListView<>(g, g.getNodes());
            subList.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)-> {
                ObservableList<NodeBean> l = subList.getSelectionModel().getSelectedItems();
                model.getCurrentNodeBeans().clear();
                for(NodeBean node : l){
                    model.addCurrentNodes(node);
                }
            });
            list.add(subList);
        }
        return FXCollections.observableList(list);
    }

    public void setDataModel(DataModel dataModel) {
        this.model = dataModel ;
    }

    public void update() {
        ObservableList<ElementListView<GroupBean, NodeBean>> subLists = generateListView();
        listView.getItems().clear();
        listView.setItems(subLists);
        listView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }
}
