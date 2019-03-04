/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.app.controller;

import com.jfoenix.controls.JFXTabPane;
import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.request.CommandGetSSHLogin;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeTerminalController extends View {

    @FXML
    JFXTabPane nodeTermTabPane;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;
    private TerminalBuilder terminalBuilder;

    public NodeTerminalController(View parent) throws IOException {
        super("NodeTerminal", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));
        terminalBuilder = new TerminalBuilder(darkConfig);
        nodeTermTabPane.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
    }

    @Override
    protected void onStart() {
        if(requestManager == null){
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        for(NodeData n : nodesModel.getCurrentNodeData()){
            Future<String> username = (Future<String>) requestManager.aSyncSendRequest(new CommandGetSSHLogin());

            nodeTermTabPane.getTabs().clear();

            TerminalTab terminal = terminalBuilder.newTerminal();
            terminal.setText(n.getNodeAddress().toString());
            try {
                String user = username.get();
                String command = "ssh -t " + user + "@" + config.getIP() + " -p " + config.getSSHPort() + " ssh " + n.getSshLogin() + "@" + n.getNodeAddress().getHostAddress() + " -p " + n.getSshPort() + "\n";
                terminal.onTerminalFxReady(() -> terminal.getTerminal().command(command));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            nodeTermTabPane.getTabs().add(terminal);
        }



    }

    @Override
    public void onStop() {

    }

}
