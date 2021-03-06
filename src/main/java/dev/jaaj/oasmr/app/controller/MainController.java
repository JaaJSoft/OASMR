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

package dev.jaaj.oasmr.app.controller;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import dev.jaaj.oasmr.app.controller.node.NodeListController;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.controller.node.NodeViewController;
import dev.jaaj.oasmr.app.view.NodesModel;
import dev.jaaj.oasmr.app.view.SceneManager;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.app.view.exception.ExceptionSceneNotFound;
import dev.jaaj.oasmr.supervisor.node.NodeData;
import javafx.collections.SetChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;


public class MainController extends View {

    @FXML
    SplitPane mainPane;

    @FXML
    MenuItem menuDisconnect;

    @FXML
    MenuItem menuExit;

    @FXML
    MenuItem menuUserAdmin;

    @FXML
    MenuItem menuParam;

    @FXML
    MenuItem menuAbout;

    private NodesModel nodesModel;

    private View defaultView;
    private View nodeListView;
    private View nodeView;
    private SceneManager sceneManager;
    private Config config = null;
    private Node aboutNode = FXMLLoader.load(getClass().getResource("/dev/jaaj/oasmr/app/About.fxml"));

    public MainController() throws IOException {
        super("Main");
    }

    @Override
    public void onCreate() {
        sceneManager = SceneManager.getInstance();
        try {
            nodeListView = new NodeListController(this);
            addSubView(nodeListView);
            nodeView = new NodeViewController(this);
            addSubView(nodeView);
            defaultView = new DefaultController(this);

            mainPane.getItems().add(nodeListView.getRoot());
            mainPane.getItems().add(defaultView.getRoot());
            mainPane.setDividerPositions(0.2);

            nodesModel = NodesModel.getInstance();
            nodesModel.getCurrentNodeData().addListener((SetChangeListener.Change<? extends NodeData> c) -> {
                onStart();
            });

            menuUserAdmin.setOnAction(actionEvent -> {
                try {
                    sceneManager.setScenes(UserManagementController.class, 1500, 800);
                } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                    exceptionSceneNotFound.printStackTrace();
                }
            });
            menuExit.setOnAction(actionEvent -> {
                getScene().getWindow().fireEvent(new WindowEvent(getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
            });

            menuDisconnect.setOnAction(actionEvent -> {
                try {
                    sceneManager.setScenes(LoginController.class, 400, 450);
                } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                    exceptionSceneNotFound.printStackTrace();
                }
            });

            menuAbout.setOnAction(actionEvent -> {
                JFXDialogLayout about = new JFXDialogLayout();
                about.setHeading(new Label("About"));
                JFXAlert alert = new JFXAlert((Stage)getScene().getWindow());
                alert.setOverlayClose(true);
                alert.setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
                alert.setContent(about);
                alert.initModality(Modality.APPLICATION_MODAL);
                about.setBody(aboutNode);
                JFXButton close = new JFXButton("Close");
                close.setOnAction(e -> alert.close());
                about.setActions(close);
                alert.show();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {

        config = Config.getInstance();

        if (nodesModel.getSelectedAmount() > 0) {
            mainPane.getItems().set(1, nodeView.getRoot());
            nodeView.start();
        } else {
            mainPane.getItems().set(1, defaultView.getRoot());
        }
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }
}
