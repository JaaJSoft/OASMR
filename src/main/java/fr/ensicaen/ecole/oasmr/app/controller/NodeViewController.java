package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.beans.NodeBean;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
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
        this.model = dataModel;
        this.model.getCurrentNodeBeans().addListener((ListChangeListener.Change<? extends NodeBean> c) -> {
            nodeName.setText("");
            nodeId.setText("");
            if(model.getSelectedAmount() == 1){
                nodeName.setText(model.getCurrentNodeBeans().get(0).toString());
                nodeId.setText(String.valueOf(model.getCurrentNodeBeans().get(0).getId()));
            }else{
                nodeName.setText("Group : ");
                for(NodeBean node : model.getCurrentNodeBeans()){
                    nodeName.setText(nodeName.getText() + node.toString() + " ");
                }
            }
        });
    }

}
