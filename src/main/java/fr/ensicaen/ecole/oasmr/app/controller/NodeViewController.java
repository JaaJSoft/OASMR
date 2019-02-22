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
    JFXTabPane bottomPane;

    private NodesModel nodesModel;
    private RequestManager requestManager;
    private TerminalBuilder terminalBuilder;

    public NodeViewController() throws IOException {
        super("NodeView");
        onCreate();
    }


    public void setDataModel(NodesModel nodesModel) {
        this.nodesModel = nodesModel;
    }

    public void setRequestManager(RequestManager rm) {
        requestManager = rm;
    }


    private void updateNodeInfo() {
        nodeName.setText(nodesModel.getCurrentNodeData().get(0).getName());
        nodeId.setText(String.valueOf(nodesModel.getCurrentNodeData().get(0).getId()));
    }


    private void updateModuleTab() {
        moduleTabPane.getTabs().clear();
        Tab t = new Tab();
        t.setText("Module 1");
        t.setContent(new Label("Insert module core"));
        Tab t2 = new Tab();
        t2.setText("Module 2");
        t2.setContent(new Label("Insert module core"));
        moduleTabPane.getTabs().addAll(t, t2);

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
                Stage stage = (Stage) mainVBox.getScene().getWindow();
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
    }

    private void updateNodeTerm() {
        Config config = Config.getInstance();
        Future<String> username = (Future<String>) requestManager.aSyncSendRequest(new CommandGetSSHLogin());

        bottomPane.getTabs().clear();
        NodeData n = nodesModel.getCurrentNodeData().get(0);

        TerminalTab terminal = terminalBuilder.newTerminal();
        try {
            String user = username.get();
            String command = "ssh -t " + user + "@" + config.getIP() + " -p " + config.getSSHPort() + " ssh " + n.getSshLogin() + "@" + n.getNodeAddress().getHostAddress() + " -p " + n.getSshPort() + "\n";
            terminal.onTerminalFxReady(() -> terminal.getTerminal().command(command));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        bottomPane.getTabs().add(terminal);
    }

    private void updateRightInfo() {
        rightVBox.getChildren().clear();
        rightVBox.getChildren().add(new Label("CPU/RAM usage"));
        rightVBox.getChildren().add(new Separator());
        rightVBox.getChildren().add(new Label("Files"));

    }


    @Override
    public void onCreate() {
        try {
            Config config = Config.getInstance();
            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
        } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
            exceptionPortInvalid.printStackTrace();
        }
        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));

        terminalBuilder = new TerminalBuilder(darkConfig);
    }

    @Override
    public void onStart() {
        updateNodeInfo();
        updateModuleTab();
        updateNodeTerm();
        updateRightInfo();
    }

    @Override
    public void onStop() {

    }
}
