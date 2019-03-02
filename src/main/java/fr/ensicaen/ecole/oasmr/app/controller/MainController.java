package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MainController extends View {

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private NodesModel nodesModel;

    private NodeListController nodeListView;
    private NodeViewController nodeView;


    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    @Override
    public void onCreate() {
        try {
            nodeListView = new NodeListController();
            addSubView(nodeListView);
            nodeView = new NodeViewController();
            addSubView(nodeView);
            mainPane.setDividerPositions(0.2);
            mainPane.getItems().add(nodeListView.getRoot());
            mainPane.getItems().add(nodeView.getRoot());
            nodeListView.setMainController(this);
            nodesModel = new NodesModel();
            nodeView.setNodesModel(nodesModel);
            nodeListView.setNodesModel(nodesModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        try {
            Config config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }

        try {
            NodeData[] nodeList = (NodeData[]) requestManager.sendRequest(new RequestGetNodes());
            nodesModel.refreshNodeBeanList(nodeList);
            nodesModel.getCurrentNodeData().addListener((ListChangeListener.Change<? extends NodeData> c) -> {
               nodeView.onLoad();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
