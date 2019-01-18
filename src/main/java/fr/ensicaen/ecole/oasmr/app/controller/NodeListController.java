package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXListView;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class NodeListController implements Initializable {

    @FXML
    VBox vbox;

    @FXML
    VBox vboxList;

    private DataModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public ElementListView<GroupBean, NodeBean> generateListView(){
        GroupBean g = model.getCurrentGroupBean();
        ElementListView<GroupBean, NodeBean> list = new ElementListView<>(g, g.getNodes());
        list.getSelectionModel().selectedItemProperty().addListener((obs,ov,nv)-> {
            ObservableList<NodeBean> l = list.getSelectionModel().getSelectedItems();
            model.getCurrentNodeBeans().clear();
            for(NodeBean node : l){
                model.addCurrentNodes(node);
            }
        });
        return list;
    }

    public void setDataModel(DataModel dataModel) {
        this.model = dataModel ;
    }

    public void update() {
        ElementListView<GroupBean, NodeBean> list = generateListView();
        vboxList.getChildren().clear();
        vboxList.getChildren().add(list);
    }
}
