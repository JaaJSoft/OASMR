package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupViewController implements Initializable {

    @FXML
    Text nodeName;

    @FXML
    Text nodeId;

    private DataModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDataModel(DataModel dataModel) {
        model = dataModel;
    }

    public void update(){
        //TODO : give good infos
        nodeName.setText("Group : ");
        for(NodeBean node : model.getCurrentNodeBeans()) {
            nodeName.setText(nodeName.getText() + node.toString() + " ");
        }
    }

}
