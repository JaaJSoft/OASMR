package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeCommandModuleController extends View {

    @FXML
    JFXTabPane nodeCommandTabPane;

    private Config config;
    private RequestManager requestManager;
    private NodesModel nodesModel;

    public NodeCommandModuleController() throws IOException {
        super("NodeCommandModule");
        onCreate();
    }

    @Override
    public void onCreate() {

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
            nodeCommandTabPane.getTabs().clear();
            Tab t = new Tab();
            t.setText("Module 1");
            t.setContent(new Label("Insert module core"));
            Tab t2 = new Tab();
            t2.setText("Module 2");
            t2.setContent(new Label("Insert module core"));
            nodeCommandTabPane.getTabs().addAll(t, t2);

            JFXButton jeej = new JFXButton("echo on node");
            jeej.setStyle("-jfx-button-type: RAISED;-fx-background-color: #FF6026; -fx-text-fill: white;");
            jeej.setOnAction(e -> {
                try {
                    String response = (String) requestManager.sendRequest(
                            new RequestExecuteCommand(
                                    nodesModel.getCurrentNodeData().get(0).getId(),
                                    new CommandEchoString("Test from node")
                            ));
                    System.out.println(response);
                    Stage stage = (Stage) getScene().getWindow();
                    JFXDialogLayout layout = new JFXDialogLayout();
                    layout.setHeading(new Label("Response"));
                    layout.setBody(new Label(response));
                    JFXAlert alert = new JFXAlert<>(stage);
                    alert.setOverlayClose(true);
                    alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                    alert.setContent(layout);
                    alert.initModality(Modality.NONE);

                    alert.show();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            t.setContent(jeej);
        } else {
            //TODO : Configure view for nothing selected
        }



    }

    @Override
    public void onStop() {

    }

    public void setNodesModel(NodesModel nodesModel){
        this.nodesModel = nodesModel;
    }

}
