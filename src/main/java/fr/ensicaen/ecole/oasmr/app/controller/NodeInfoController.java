package fr.ensicaen.ecole.oasmr.app.controller;

import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.lib.system.CommandGetModel;
import fr.ensicaen.ecole.oasmr.lib.system.CommandGetSystem;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeInfoController extends View {

    @FXML
    VBox nodeInfoVbox;

    private Config config;
    private RequestManager requestManager;
    private NodesModel nodesModel;

    public NodeInfoController(View parent) throws IOException {
        super("NodeInfo", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
    }

    @Override
    protected void onStart() {

        try {
            config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {
            NodeData node = nodesModel.getCurrentNodeData().get(0);
            nodeInfoVbox.getChildren().clear();
            nodeInfoVbox.getChildren().add(
                    new Label(node.getName())
            );
            nodeInfoVbox.getChildren().add(
                    new Label("IP : " + node.getNodeAddress())
            );
            try {
                String model = (String) requestManager.sendRequest(
                        new RequestExecuteCommand(
                                nodesModel.getCurrentNodeData().get(0).getId(),
                                new CommandGetModel()
                        ));
                nodeInfoVbox.getChildren().add(
                        new Label("Model : " + model)
                );
                String system = (String) requestManager.sendRequest(
                        new RequestExecuteCommand(
                                nodesModel.getCurrentNodeData().get(0).getId(),
                                new CommandGetSystem()
                        ));
                nodeInfoVbox.getChildren().add(
                        new Label("System : " + system)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //TODO : Configure view for nothing selected
        }
    }

    @Override
    public void onStop() {

    }

}
