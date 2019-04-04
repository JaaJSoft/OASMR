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
import com.jfoenix.controls.JFXChipView;
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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

public class NodeTagsController extends View {

    @FXML
    private
    VBox tagsNode;

    @FXML
    private
    JFXChipView<String> tags;

    private RequestManager requestManager = null;
    private Config config = null;
    private NodesModel nodesModel = null;

    public NodeTagsController(View parent) throws IOException {
        super("NodeTags", parent);
    }

    @Override
    public void onCreate() {

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

        if (nodesModel.getSelectedAmount() == 1) {
            NodeData n = nodesModel.getCurrentNodeData().iterator().next();
            tagsNode.getChildren().clear();
            //n.getTags().forEach(e -> tagsNode.getChildren().add(new Label(e.getName())));

            for (Tag t: n.getTags()) {
                Label tagLabel = new Label(t.getName());
                tagLabel.setStyle("-fx-background-color: #d2d3d7; -fx-text-fill: #000000;");
                tagsNode.getChildren().add(tagLabel);
            }

            JFXButton newTag = new JFXButton("new Tag");
            newTag.setStyle("-jfx-button-type: RAISED;-fx-background-color: #FF6026; -fx-text-fill: white;");

            tags.getChips().addListener((ListChangeListener<? super String>) change -> {
                ObservableList<? extends String> list = change.getList();
                ObservableSet<NodeData> filterList;
                if (list.isEmpty()) {
                    filterList = nodesModel.getAllNodeData();
                } else {
                    List<Tag> tags = list.stream().map(Tag::new).collect(Collectors.toList());
                    //filterList = filterNodeData(nodesModel.getAllNodeData(), tags);
                }
            });
            newTag.setOnAction(actionEvent -> {
                new FXClassInitializer((Stage) tagsNode.getScene().getWindow(), Tag.class).initFromClass(newObject -> {
                    requestManager.aSyncSendRequest(new RequestAddTagToNode(nodesModel.getCurrentNodeData().iterator().next().getId(), (Tag) newObject));
                    onStart();
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
        config = null;
        requestManager = null;
        nodesModel = null;
    }

}
