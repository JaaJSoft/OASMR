package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private DataModel model;
    private RequestManager requestManager;

    public GroupViewController() throws IOException {
        super("GroupView");
        onCreate();
    }

    public void setDataModel(DataModel dataModel) {
        model = dataModel;
    }

    public void setRequestManager(RequestManager rm) {
        requestManager = rm;
    }

    private void updateNodesInfo(){
        nodeName.setText("Group : ");
        for(NodeBean node : model.getCurrentNodeBeans()) {
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
