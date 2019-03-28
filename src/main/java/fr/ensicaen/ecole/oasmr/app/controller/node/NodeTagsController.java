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

import com.jfoenix.controls.JFXButton;
import fr.ensicaen.ecole.oasmr.app.Config;
import fr.ensicaen.ecole.oasmr.app.view.NodesModel;
import fr.ensicaen.ecole.oasmr.app.view.View;
import fr.ensicaen.ecole.oasmr.lib.FXClassInitializer;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.NodeData;
import fr.ensicaen.ecole.oasmr.supervisor.node.Tag;
import fr.ensicaen.ecole.oasmr.supervisor.node.command.request.RequestAddTagToNode;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class NodeTagsController extends View {
    @FXML
    VBox tagsNode;

    private RequestManager requestManager = null;
    private Config config;
    private NodesModel nodesModel;

    public NodeTagsController(View parent) throws IOException {
        super("NodeTags", parent);
    }

    @Override
    public void onCreate() {

    }

    @Override
    protected void onStart() {
        if (requestManager == null) {
            try {
                nodesModel = NodesModel.getInstance();
                config = Config.getInstance();
                requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(config.getIP()), config.getPort());
            } catch (ExceptionPortInvalid | UnknownHostException exceptionPortInvalid) {
                exceptionPortInvalid.printStackTrace();
            }
        }

        if (nodesModel.getSelectedAmount() == 1) {
            NodeData n = nodesModel.getCurrentNodeData().iterator().next();
            tagsNode.getChildren().clear();
            n.getTags().forEach(e -> tagsNode.getChildren().add(new Label(e.getName())));

            JFXButton newTag = new JFXButton("new Tag");
            newTag.setOnAction(actionEvent -> {
                new FXClassInitializer((Stage) tagsNode.getScene().getWindow(), Tag.class).initFromClass(newObject -> {
                    requestManager.aSyncSendRequest(new RequestAddTagToNode(nodesModel.getCurrentNodeData().iterator().next().getId(), (Tag) newObject));
                });
            });
            tagsNode.getChildren().add(newTag);
        }

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }
}
