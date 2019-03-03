package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.io.IOException;


public class MainController extends View {

    @FXML
    SplitPane mainPane;

    private NodesModel nodesModel;

    private View defaultView;
    private View nodeListView;
    private View nodeView;

    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    @Override
    public void onCreate() {
        try {
            nodeListView = new NodeListController(this);
            addSubView(nodeListView);
            nodeView = new NodeViewController(this);
            addSubView(nodeView);
            defaultView = new DefaultController(this);

            mainPane.setDividerPositions(0.2);
            mainPane.getItems().add(nodeListView.getRoot());
            mainPane.getItems().add(defaultView.getRoot());

            nodesModel = NodesModel.getInstance();
            nodesModel.getCurrentNodeData().addListener((ListChangeListener.Change<? extends NodeData> c) -> {
                onStart();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        if (nodesModel.getSelectedAmount() > 0) {
            mainPane.getItems().set(1, nodeView.getRoot());
            nodeView.onLoad();
        } else {
            mainPane.getItems().set(1, defaultView.getRoot());
        }
    }

    @Override
    public void onStop() {

    }
}
