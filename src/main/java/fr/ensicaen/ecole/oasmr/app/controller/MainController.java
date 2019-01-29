package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
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
    private GroupViewController groupView;
    private DefaultController defaultView;


    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    @Override
    public void onCreate() {
        try {
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
        try {
            nodeListView = new NodeListController();
            nodeView = new NodeViewController();
            groupView = new GroupViewController();
            defaultView = new DefaultController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        try {
            mainPane.getItems().clear();
            mainPane.setDividerPositions(0.2);
            NodeBean[] nodeList = (NodeBean[]) requestManager.sendRequest(new RequestGetNodes());
            nodesModel = new NodesModel(nodeList);
            mainPane.getItems().add(0, nodeListView.getRoot());
            mainPane.getItems().add(1, defaultView.getRoot());
            nodesModel.getCurrentNodeBeans().addListener((ListChangeListener.Change<? extends NodeBean> c) -> {
                if (nodesModel.getSelectedAmount() > 1) {
                    groupView.onStart();
                    mainPane.getItems().set(1, groupView.getRoot());
                } else if (nodesModel.getSelectedAmount() == 1) {
                    nodeView.onStart();
                    mainPane.getItems().set(1, nodeView.getRoot());
                } else {
                    mainPane.getItems().set(1, defaultView.getRoot());
                }
            });
            nodeView.setDataModel(nodesModel);
            nodeListView.setMainController(this);
            nodeListView.setDataModel(nodesModel);
            groupView.setDataModel(nodesModel);
            nodeListView.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
