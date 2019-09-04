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

package dev.jaaj.oasmr.app.controller.node;

import com.jfoenix.controls.JFXTabPane;
import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.supervisor.node.NodeData;
import dev.jaaj.oasmr.supervisor.request.CommandGetSSHLogin;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeTerminalController extends View {

    @FXML
    JFXTabPane nodeTermTabPane;

    private RequestManager requestManager = null;
    private Config config = null;
    private NodesModel nodesModel = null;

    private TerminalBuilder terminalBuilder;
    private final HashMap<Integer, TerminalTab> flyweightTerminal = new HashMap<>();

    public NodeTerminalController(View parent) throws IOException {
        super("NodeTerminal", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));
        terminalBuilder = new TerminalBuilder(darkConfig);
        nodeTermTabPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
    }

    @Override
    protected void onStart() {
        if (requestManager == null && nodesModel == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
                nodesModel = NodesModel.getInstance();
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        nodeTermTabPane.getTabs().clear();

        for (NodeData n : nodesModel.getCurrentNodeData()) {

            if (flyweightTerminal.containsKey(n.getId())) {
                nodeTermTabPane.getTabs().add(flyweightTerminal.get(n.getId()));
            } else {
                Future<String> username = (Future<String>) requestManager.aSyncSendRequest(new CommandGetSSHLogin());

                TerminalTab terminal = terminalBuilder.newTerminal();
                terminal.setText(n.getNodeAddress().toString());
                try {
                    String user = username.get();
                    String command = "ssh -t " + user + "@" + config.getIP() + " -p " + config.getSSHPort() + " ssh " + n.getSshLogin() + "@" + n.getNodeAddress().getHostAddress() + " -p " + n.getSshPort() + "\n";
                    terminal.onTerminalFxReady(() -> terminal.getTerminal().command(command));
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                flyweightTerminal.put(n.getId(), terminal);
                nodeTermTabPane.getTabs().add(terminal);
            }
        }
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {
        config = null;
        requestManager = null;
        nodesModel = null;
    }

}
