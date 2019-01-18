package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.SplitPane;

import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController implements Initializable {

    @FXML
    SplitPane mainPane;

    private RequestManager requestManager;
    private DataModel dataModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dataModel = new DataModel(getAllNodes());
            final FXMLLoader loaderList = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeList.fxml"));
            final FXMLLoader loaderNode = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/NodeView.fxml"));
            final FXMLLoader loaderGroup = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/GroupView.fxml"));
            final FXMLLoader loaderDefault = new FXMLLoader(getClass().getResource("/fr/ensicaen/ecole/oasmr/app/Default.fxml"));
            Parent nodeViewNode = loaderNode.load();
            Parent groupViewNode = loaderGroup.load();
            Parent defaultViewNode = loaderDefault.load();
            mainPane.getItems().add(0, loaderList.load());
            mainPane.getItems().add(1, defaultViewNode);
            NodeListController nodeListController = loaderList.getController();
            NodeViewController nodeViewController = loaderNode.getController();
            GroupViewController groupViewController = loaderGroup.getController();
            dataModel.getCurrentNodeBeans().addListener((ListChangeListener.Change<? extends NodeBean> c) -> {
                if(dataModel.getSelectedAmount() > 1){
                    groupViewController.update();
                    mainPane.getItems().set(1, groupViewNode);
                }else if (dataModel.getSelectedAmount() == 1){
                    nodeViewController.update();
                    mainPane.getItems().set(1, nodeViewNode);
                }else{
                    mainPane.getItems().set(1, defaultViewNode);
                }
            });
            nodeListController.setDataModel(dataModel);
            nodeViewController.setDataModel(dataModel);
            groupViewController.setDataModel(dataModel);
            nodeListController.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private GroupBean getAllNodes() throws Exception {
        //TODO : Generate from supervisor
        GroupBean g = new GroupBean("All node", 1);
        for(int i=0; i<10; i++){
            NodeBean n = new NodeBean("Node " + i, i);
            g.addNode(n);
        }
        return g;
    }


}
