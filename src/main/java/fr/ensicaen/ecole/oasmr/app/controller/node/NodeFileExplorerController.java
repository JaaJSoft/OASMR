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

package fr.ensicaen.ecole.oasmr.app.controller.node;

import com.jfoenix.controls.JFXTreeView;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.gui.tree.FileTreeItem;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.filemanagement.CommandGetRootFile;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class NodeFileExplorerController extends View {

    @FXML
    VBox fileExplorerVBox;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;
    private String path  = ".";
    private JFXTreeView<File> fileTreeView;

    public NodeFileExplorerController(View parent) throws IOException {
        super("NodeFileExplorer", parent);
        onCreate();
    }

    @Override
    public void onCreate() {
        nodesModel = NodesModel.getInstance();
    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        if (nodesModel.getSelectedAmount() > 1) {
            //TODO : Configure view for group
        } else if (nodesModel.getSelectedAmount() == 1) {

            try {
                Future<? extends Serializable> reponseRoot = requestManager.aSyncSendRequest(new RequestExecuteCommand(
                        nodesModel.getCurrentNodeData().get(0).getId(),
                        new CommandGetRootFile()
                ));
                File root = (File) reponseRoot.get();
                fileExplorerVBox.getChildren().clear();
                fileTreeView = new JFXTreeView<File>(new FileTreeItem(root));
                fileExplorerVBox.getChildren().add(fileTreeView);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onStop() {

    }

}
