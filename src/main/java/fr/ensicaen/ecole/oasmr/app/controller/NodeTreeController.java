package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.view.SceneManager;
import fr.ensicaen.ecole.oasmr.app.view.tree.GenericTree;
import fr.ensicaen.ecole.oasmr.app.view.tree.GroupItem;
import fr.ensicaen.ecole.oasmr.app.view.tree.NodeItem;
import fr.ensicaen.ecole.oasmr.app.view.tree.GenericTreeItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import org.jcp.xml.dsig.internal.dom.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NodeTreeController implements Initializable {

    @FXML
    TreeView<GenericTreeItem<?>> nodeTreeView;

    @FXML
    VBox vbox;

    private SceneManager sceneManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sceneManager = SceneManager.getInstance();
        GroupItem group = new GroupItem("Group 1");

        NodeItem n1 = new NodeItem("Node 1", 1);
        NodeItem n2 = new NodeItem("Node 2", 2);
        NodeItem n3 = new NodeItem("Node 3", 3);

        GroupItem group2 = new GroupItem("Group 2");

        NodeItem n4 = new NodeItem("Node 4", 4);
        NodeItem n5 = new NodeItem("Node 5", 5);
        NodeItem n6 = new NodeItem("Node 6", 6);

        group.getSubObjects().addAll(n1, n2, n3);
        group2.getSubObjects().addAll(n5, n6, n4);
        GenericTree<GenericTreeItem<?>> tree = new GenericTree<>(GenericTreeItem::getSubObjects, group, group2);
        TreeView<GenericTreeItem<?>> treeView = tree.getTreeView();
        treeView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        vbox.getChildren().add(treeView);


    }

}
