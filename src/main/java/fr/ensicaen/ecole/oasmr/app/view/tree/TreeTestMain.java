package fr.ensicaen.ecole.oasmr.app.view.tree;

import fr.ensicaen.ecole.oasmr.app.gui.GenericTree;
import fr.ensicaen.ecole.oasmr.app.gui.GenericTreeItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TreeTestMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        GroupItem group = createGroup();

        GenericTree<GenericTreeItem<?>> tree = new GenericTree<>(GenericTreeItem::getSubObjects, group);

        TreeView<GenericTreeItem<?>> treeView = tree.getTreeView();

        BorderPane root = new BorderPane();
        root.setCenter(treeView);
        Scene scene = new Scene(root, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private GroupItem createGroup() {
        GroupItem group = new GroupItem("Group 1");
        GroupItem group2 = new GroupItem("Group 2");

        NodeItem obj1 = new NodeItem("Node 1", 1);
        NodeItem obj2 = new NodeItem("Node 2", 2);
        NodeItem obj3 = new NodeItem("Node 3", 3);

        NodeItem obj4 = new NodeItem("Node 6", 1);
        NodeItem obj5 = new NodeItem("Node 7", 2);
        NodeItem obj6 = new NodeItem("Node 8", 3);

        group.getSubObjects().addAll(obj1, obj2, obj3);
        group2.getSubObjects().addAll(obj4, obj5, obj6);

        return group;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
