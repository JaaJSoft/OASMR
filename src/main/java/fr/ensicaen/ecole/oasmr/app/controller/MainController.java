package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController implements Initializable {

    private NodeListController nodeListController;
    private NodeViewController nodeViewController;

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private DataModel dataModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataModel = new DataModel(getAllNodes());
            final FXMLLoader loaderLeft = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeList.fxml"));
            final FXMLLoader loaderRight = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeView.fxml"));
            mainPane.getItems().add(loaderLeft.load());
            mainPane.getItems().add(loaderRight.load());
            nodeListController = loaderLeft.getController();
            nodeViewController = loaderRight.getController();
            nodeListController.setDataModel(dataModel);
            nodeViewController.setDataModel(dataModel);
            nodeListController.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GroupBean getAllNodes() throws Exception {
        GroupBean g = new GroupBean("All node", 1);
        for(int i=0; i<10; i++){
            NodeBean n = new NodeBean("Node " + i, i);
            g.addNode(n);
        }
        return g;
    }


}
