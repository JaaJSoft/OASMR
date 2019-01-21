package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetAllTags;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

public class NodeListController implements Initializable {

    @FXML
    JFXButton refreshBtn;

    @FXML
    JFXChipView filter;

    @FXML
    VBox vbox;

    @FXML
    VBox vboxList;

    private DataModel model;
    private RequestManager requestManager;
    private MainController mainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
    }

    public ElementListView<GroupBean, NodeBean> generateListView() {
        GroupBean g = model.getCurrentGroupBean();
        ElementListView<GroupBean, NodeBean> list = new ElementListView<>(g, g.getNodes());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ObservableList<NodeBean> l = list.getSelectionModel().getSelectedItems();
                model.getCurrentNodeBeans().clear();
                for (NodeBean node : l) {
                    model.addCurrentNodes(node);
                }
            }
        });
        return list;
    }

    public void setDataModel(DataModel dataModel) {
        this.model = dataModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void update() {

        ElementListView<GroupBean, NodeBean> list = generateListView();
        vboxList.getChildren().clear();
        vboxList.getChildren().add(list);

        try {
            Tag[] tags = (Tag[]) requestManager.sendRequest(new RequestGetAllTags());
            filter.getSuggestions().addAll(tags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshNodes(ActionEvent actionEvent) throws Exception {
        mainController.reload();
        update();
    }
}
