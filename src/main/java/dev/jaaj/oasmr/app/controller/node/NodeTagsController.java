/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.app.controller.node;

import com.jfoenix.controls.JFXChipView;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.supervisor.node.NodeData;
import dev.jaaj.oasmr.supervisor.node.Tag;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestAddTagToNode;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestGetAllTags;
import dev.jaaj.oasmr.supervisor.node.command.request.RequestRemoveTagToNode;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;
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
    private ListChangeListener<? super String> l;

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
            if (l != null) {
                tags.getChips().removeListener(l);
            }
            NodeData n = nodesModel.getCurrentNodeData().iterator().next();
            tags.getChips().clear();
            try {
                for (Tag t : n.getTags()) {
                    tags.getChips().add(t.getName());
                }
                updateTags();
            } catch (Exception e) {
                e.printStackTrace();
            }
            l = change -> {
                change.next();
                for (String tag : change.getAddedSubList()) {
                    if (Collections.frequency(change.getList(), tag) == 2) {
                        tags.getChips().remove(tags.getChips().size() - 1);
                    } else {
                        Tag t = new Tag(tag);
                        Future<? extends Serializable> response = requestManager.aSyncSendRequest(
                                new RequestAddTagToNode(n.getId(), t)
                        );
                        n.addTag(t);
                    }

                }
                for (String tag : change.getRemoved()) {
                    Tag t = new Tag(tag);
                    Future<? extends Serializable> response = requestManager.aSyncSendRequest(
                            new RequestRemoveTagToNode(n.getId(), t)
                    );
                    n.removeTag(t);
                }
            };
            tags.getChips().addListener(l);
        }

    }

    @Override
    protected void onUpdate() {

    }

    private void updateTags() throws Exception {
        tags.getSuggestions().clear();
        Tag[] tagList = (Tag[]) requestManager.sendRequest(new RequestGetAllTags());
        List<Tag> tagArrayList = Arrays.asList(tagList);
        tags.getSuggestions().addAll(tagArrayList.stream().map(Tag::getName).collect(Collectors.toList()));
    }

    @Override
    public void onStop() {
        config = null;
        requestManager = null;
        nodesModel = null;
    }

}
