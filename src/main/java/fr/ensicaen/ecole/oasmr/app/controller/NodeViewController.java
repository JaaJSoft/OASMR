package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.CommandGetSSHLogin;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeViewController extends View {

    @FXML
    VBox mainVBox;

    @FXML
    SplitPane vSlitPane;

    @FXML
    SplitPane hSlitPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;
    private TerminalBuilder terminalBuilder;

    private NodeInfoController nodeInfoView;
    private NodeCommandModuleController nodeModuleView;
    private NodeTerminalController nodeTermView;

    public NodeViewController(View parent) throws IOException {
        super("NodeView", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        try {
            nodesModel = NodesModel.getInstance();
            nodeInfoView = new NodeInfoController(this);
            addSubView(nodeInfoView);
            nodeModuleView = new NodeCommandModuleController(this);
            addSubView(nodeModuleView);
            nodeTermView = new NodeTerminalController(this);
            addSubView(nodeTermView);
            mainVBox.getChildren().add(nodeInfoView.getRoot());
            mainVBox.getChildren().add(new Separator());
            mainVBox.getChildren().add(nodeModuleView.getRoot());
            vSlitPane.getItems().add(nodeTermView.getRoot());
            vSlitPane.setDividerPositions(0.7);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

        if(requestManager == null){
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else {
            //TODO : Configure view for node
        }
    }

    @Override
    public void onStop() {

    }

}
