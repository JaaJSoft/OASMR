package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXListView;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.Group;
import fr.ensicaen.ecole.oasmr.app.beans.Node;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import javax.xml.crypto.Data;
import java.net.URL;
import java.util.*;

public class NodeTreeController implements Initializable {

    @FXML
    VBox vbox;

    @FXML
    JFXListView<ElementListView<Group, Node>> listView;


    private DataModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<ElementListView<Group,Node>> subLists = getGroups();
        listView.setItems(subLists);
        listView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public ObservableList<ElementListView<Group,Node>> getGroups(){
        List<ElementListView<Group,Node>> list = new ArrayList<>();
        for (int i=0; i<5; i++){
            Group g = new Group("G"+i, i);
            for(int j=i; j<5; j++){
                Node n = new Node("N"+j, j);
                g.addNode(n);
            }
            ElementListView<Group, Node> subList = new ElementListView<>(g, g.getNodes());
            subList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    model.setCurrentNode(newValue);
                }
            });
            list.add(subList);
        }
        return FXCollections.observableList(list);
    }

    public void setDataModel(DataModel dataModel) {
        this.model = dataModel ;
    }

}
