package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
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
import java.util.ResourceBundle;

public class MainController extends View implements Initializable {

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private DataModel dataModel;

    private Parent nodeViewNode;
    private Parent groupViewNode;
    private Parent defaultViewNode;

    private NodeListController nodeListController;
    private NodeViewController nodeViewController;
    private GroupViewController groupViewController;

    public MainController(int width, int height) throws IOException {
        super("Main", width, height);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //TODO :
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
            dataModel = new DataModel(getAllNodes());
            final FXMLLoader loaderList = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeList.fxml"));
            final FXMLLoader loaderNode = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeView.fxml"));
            final FXMLLoader loaderGroup = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/GroupView.fxml"));
            final FXMLLoader loaderDefault = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/Default.fxml"));
            nodeViewNode = loaderNode.load();
            groupViewNode = loaderGroup.load();
            defaultViewNode = loaderDefault.load();
            mainPane.getItems().add(0, loaderList.load());
            mainPane.getItems().add(1, defaultViewNode);
            nodeListController = loaderList.getController();
            nodeViewController = loaderNode.getController();
            groupViewController = loaderGroup.getController();
            dataModel.getCurrentNodeBeans().addListener((ListChangeListener.Change<? extends NodeBean> c) -> {
                if (dataModel.getSelectedAmount() > 1) {
                    groupViewController.update();
                    mainPane.getItems().set(1, groupViewNode);
                } else if (dataModel.getSelectedAmount() == 1) {
                    nodeViewController.update();
                    mainPane.getItems().set(1, nodeViewNode);
                } else {
                    mainPane.getItems().set(1, defaultViewNode);
                }
            });
            nodeListController.setDataModel(dataModel);
            nodeListController.setMainController(this);
            nodeViewController.setDataModel(dataModel);
            groupViewController.setDataModel(dataModel);
            nodeListController.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainPane.setDividerPositions(0.2);
    }

    private GroupBean getAllNodes() throws Exception {
        NodeBean[] nodeList = (NodeBean[]) requestManager.sendRequest(new RequestGetNodes());
        GroupBean g = new GroupBean("All node", 1);
        for (NodeBean n : nodeList) {
            g.addNode(n);
        }
        return g;
    }

    public void reload() throws Exception {
        dataModel.reset(getAllNodes());
        nodeListController.update();
        mainPane.getItems().set(1, defaultViewNode);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
