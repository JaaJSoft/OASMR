package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class NodeViewController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDataModel(DataModel dataModel) {
        model = dataModel;
    }

    public void setRequestManager(RequestManager rm) {
        requestManager = rm;
    }

    //TODO : fill with good infos
    public void update(){
        updateNodeInfo();
        updateModuleTab();
        updateNodeTerm();
        updateRightInfo();
    }

    private void updateNodeInfo(){
        nodeName.setText(model.getCurrentNodeBeans().get(0).toString());
        nodeId.setText(String.valueOf(model.getCurrentNodeBeans().get(0).getId()));
    }


    private void updateModuleTab(){
        moduleTabPane.getTabs().clear();
        Tab t = new Tab();
        t.setText("Module 1");
        t.setContent(new Label("Insert module core"));
        Tab t2 = new Tab();
        t2.setText("Module 2");
        t2.setContent(new Label("Insert module core"));
        moduleTabPane.getTabs().addAll(t, t2);
    }

    private void updateNodeTerm(){
        bottomPane.getChildren().clear();
        bottomPane.getChildren().add(new Label("Term"));
    }

    private void updateRightInfo(){
        rightVBox.getChildren().clear();
        rightVBox.getChildren().add(new Label("CPU/RAM usage"));
        rightVBox.getChildren().add(new Separator());
        rightVBox.getChildren().add(new Label("Files"));

    }




}
