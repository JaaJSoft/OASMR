package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.io.IOException;


public class GroupViewController extends View {

    @FXML
    Text nodeName;

    @FXML
    Text nodeId;

    @FXML
    JFXTabPane moduleTabPane;

    @FXML
    VBox rightVBox;

    @FXML
    VBox mainVBox;

    @FXML
    AnchorPane bottomPane;

    private NodesModel nodesModel;
    private RequestManager requestManager;

    public GroupViewController() throws IOException {
        super("GroupView");
        onCreate();
    }

    public void setDataModel(NodesModel nodesModel) {
        nodesModel = nodesModel;
    }

    private void updateNodesInfo(){
        nodeName.setText("Group : ");
        for(NodeData node : nodesModel.getCurrentNodeData()) {
            nodeName.setText(nodeName.getText() + node.toString() + " ");
        }
    }

    private void updateModuleTab() {

    }

    private void updateNodeTerm() {

    }

    private void updateRightInfo() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onStart() {
        updateNodesInfo();
        updateModuleTab();
        updateNodeTerm();
        updateRightInfo();
    }

    @Override
    public void onStop() {

    }
}
