package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.gui.list.ElementListView;
import fr.ensicaen.ecole.oasmr.app.beans.GroupBean;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetAllTags;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeListController extends View {

    @FXML
    JFXButton refreshBtn;

    @FXML
    JFXChipView filter;

    @FXML
    VBox vbox;

    @FXML
    VBox vboxList;

    private NodesModel nodesModel;
    private RequestManager requestManager;
    private MainController mainController;

    public NodeListController() throws IOException {
        super("NodeList");
    }

    public ElementListView<NodeBean> generateListView() {
        ElementListView<NodeBean> list = new ElementListView<>(nodesModel.getAllNodeBeans());
        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ObservableList<NodeBean> l = list.getSelectionModel().getSelectedItems();
                nodesModel.getCurrentNodeBeans().clear();
                for (NodeBean node : l) {
                    nodesModel.addCurrentNodes(node);
                }
            }
        });
        return list;
    }

    public void setDataModel(NodesModel nodesModel) {
        this.nodesModel = nodesModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public void refreshNodes(ActionEvent actionEvent) throws Exception {
        mainController.onStart();
    }

    @Override
    public void onCreate() {
        try {
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(Config.ip), Config.port);
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        ElementListView<NodeBean> list = generateListView();
        vboxList.getChildren().clear();
        vboxList.getChildren().add(list);

        try {
            Tag[] tags = (Tag[]) requestManager.sendRequest(new RequestGetAllTags());
            filter.getSuggestions().addAll(tags);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }
}
