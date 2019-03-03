package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NodeSystemInfoController extends View {

    @FXML
    VBox nodeSystemInfoVBox;

    private NodesModel nodesModel;
    private View nodeRamInfo;
    private View nodeCpuInfo;

    public NodeSystemInfoController(View parent) throws IOException {
        super("NodeSystemInfo", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodesModel = NodesModel.getInstance();
            nodeRamInfo = new NodeRamInfoController(this);
            addSubView(nodeRamInfo);
            nodeCpuInfo = new NodeCpuInfoController(this);
            addSubView(nodeCpuInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        if(nodesModel.getSelectedAmount() > 1){
            //TODO : generate group view
        }else{
            nodeSystemInfoVBox.getChildren().clear();
            nodeSystemInfoVBox.getChildren().add(nodeCpuInfo.getRoot());
            nodeSystemInfoVBox.getChildren().add(nodeRamInfo.getRoot());
        }
    }

    @Override
    public void onStop() {

    }
}
