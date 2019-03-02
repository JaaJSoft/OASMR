package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXListView;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestGetAllTags;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NodeListController extends View {

    @FXML
    JFXButton refreshBtn;

    @FXML
    JFXChipView filter;

    @FXML
    VBox vbox;

    @FXML
    VBox vboxList;

    @FXML
    JFXListView nodeListView;

    private Config config;
    private NodesModel nodesModel;
    private RequestManager requestManager;
    private MainController mainController;

    public NodeListController() throws IOException {
        super("NodeList");
        refreshBtn.setOnAction(e -> {
            mainController.onLoad();
        });
        onCreate();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {

        try {
            config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }

        nodeListView.getItems().clear();
        nodeListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nodeListView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        nodeListView.setOnMouseClicked(event -> {
            ObservableList<NodeData> l = nodeListView.getSelectionModel().getSelectedItems();
            nodesModel.getCurrentNodeData().clear();
            for (NodeData node : l) {
                nodesModel.addCurrentNodes(node);
            }
        });
        nodeListView.setItems(nodesModel.getAllNodeData());
        try {
            filter.getSuggestions().clear();
            Tag[] tags = (Tag[]) requestManager.sendRequest(new RequestGetAllTags());
            List<Tag> tagArrayList = Arrays.asList(tags);
            filter.getSuggestions().addAll(tagArrayList.stream().map(Tag::getName).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {

    }

    public void setNodesModel(NodesModel nodesModel) {
        this.nodesModel = nodesModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

}
