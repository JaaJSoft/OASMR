package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.beans.Node;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class NodeViewController implements Initializable {

    @FXML
    Text nodeName;

    @FXML
    Text nodeId;

    private DataModel model;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setDataModel(DataModel dataModel) {
        this.model = dataModel ;
        this.model.nodeProperty().addListener(nodeListener);
    }

    private ChangeListener<Node> nodeListener = (obsValue, oldValue, newValue) -> {
        nodeName.setText(newValue.toString());
        nodeId.setText(Integer.toString(newValue.getId()));
    };

}
