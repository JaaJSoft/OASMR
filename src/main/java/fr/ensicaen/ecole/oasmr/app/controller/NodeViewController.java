package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXTabPane;
import com.kodedu.terminalfx.Terminal;
import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import fr.ensicaen.ecole.oasmr.app.view.DataModel;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeBean;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
    JFXTabPane bottomPane;

    private DataModel model;
    private RequestManager requestManager;
    private TerminalBuilder terminalBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));

        terminalBuilder = new TerminalBuilder(darkConfig);
    }

    public void setDataModel(DataModel dataModel) {
        model = dataModel;
    }

    public void setRequestManager(RequestManager rm) {
        requestManager = rm;
    }

    //TODO : fill with good infos
    public void update() {
        updateNodeInfo();
        updateModuleTab();
        updateNodeTerm();
        updateRightInfo();
    }

    private void updateNodeInfo() {
        nodeName.setText(model.getCurrentNodeBeans().get(0).getName());
        nodeId.setText(String.valueOf(model.getCurrentNodeBeans().get(0).getId()));
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
    }

    private void updateNodeTerm() {
        bottomPane.getTabs().clear();
        NodeBean n = model.getCurrentNodeBeans().get(0);

        TerminalTab terminal = terminalBuilder.newTerminal();
        Terminal term = terminal.getTerminal();
        //terminal.getTerminal().command("ssh -t pierre@127.0.0.1 -p 22 ssh " + n.getSshLogin() + "@" + n.getNodeAddress().toString() + "-p " + n.getSshPort());

        bottomPane.getTabs().add(terminal);
    }

    private void updateRightInfo() {
        rightVBox.getChildren().clear();
        rightVBox.getChildren().add(new Label("CPU/RAM usage"));
        rightVBox.getChildren().add(new Separator());
        rightVBox.getChildren().add(new Label("Files"));

    }


}
