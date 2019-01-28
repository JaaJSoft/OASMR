package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

public class MainController extends View {

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private DataModel dataModel;

    private NodeListController nodeListView;
    private NodeViewController nodeView;
    private GroupViewController groupView;
    private DefaultController defaultView;


    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }

    private GroupBean getAllNodes() throws Exception {
        NodeBean[] nodeList = (NodeBean[]) requestManager.sendRequest(new RequestGetNodes());
        GroupBean g = new GroupBean("All node", 1);
        for (NodeBean n : nodeList) {
            g.addNode(n);
        }
        return g;
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
        mainPane.setDividerPositions(0.2);
    }

    @Override
    public void onStart() {
        try {
            mainPane.getItems().clear();
            dataModel = new DataModel(getAllNodes());
            mainPane.getItems().add(0, nodeListView.getRoot());
            mainPane.getItems().add(1, defaultView.getRoot());
            dataModel.getCurrentNodeBeans().addListener((ListChangeListener.Change<? extends NodeBean> c) -> {
                if (dataModel.getSelectedAmount() > 1) {
                    groupView.onStart();
                    mainPane.getItems().set(1, groupView.getRoot());
                } else if (dataModel.getSelectedAmount() == 1) {
                    nodeView.onStart();
                    mainPane.getItems().set(1, nodeView.getRoot());
                } else {
                    mainPane.getItems().set(1, defaultView.getRoot());
                }
            });
            nodeView.setDataModel(dataModel);
            nodeListView.setMainController(this);
            nodeListView.setDataModel(dataModel);
            groupView.setDataModel(dataModel);
            nodeListView.onStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
