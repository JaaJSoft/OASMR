package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXListView;
import fr.ensicaen.ecole.oasmr.app.gui.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.Group;
import fr.ensicaen.ecole.oasmr.app.beans.Node;
import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.*;

public class NodeTreeController implements Initializable {

    @FXML
    VBox vbox;

    private SceneManager sceneManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        JFXListView<ElementListView<Group, Node>> listView = new JFXListView<>();
        ObservableList<ElementListView<Group,Node>> subLists = getGroups();
        listView.setItems(subLists);
        listView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vbox.getChildren().add(listView);
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
            list.add(subList);
        }
        return FXCollections.observableList(list);
    }

}
