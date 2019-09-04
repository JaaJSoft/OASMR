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

package dev.jaaj.oasmr.app.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dev.jaaj.oasmr.lib.network.exception.ExceptionPortInvalid;
import dev.jaaj.oasmr.app.Config;
import dev.jaaj.oasmr.app.Session;
import dev.jaaj.oasmr.app.view.SceneManager;
import dev.jaaj.oasmr.app.view.View;
import dev.jaaj.oasmr.app.view.exception.ExceptionSceneNotFound;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAuthentication;
import dev.jaaj.oasmr.supervisor.request.RequestManager;
import dev.jaaj.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginController extends View {

    private SceneManager sceneManager;
    private RequestManager requestManager;

    @FXML
    Text loginError;

    @FXML
    JFXTextField loginUser;

    @FXML
    JFXPasswordField loginPassword;

    @FXML
    JFXButton loginConnect;

    @FXML
    JFXTextField IPServer;

    @FXML
    JFXTextField portNumber;

    public LoginController() throws IOException {
        super("Login");
    }

    private boolean checkInput() { //TODO CHECK PORT NOT VALID
        if (IPServer.getText() == null || IPServer.getText().trim().isEmpty()) {
            loginError.setText("No IP");
            return false;
        } else if (portNumber.getText() == null || portNumber.getText().trim().isEmpty()) {
            loginError.setText("No port");
            return false;
        }else if (Integer.parseInt(portNumber.getText())<0 || Integer.parseInt(portNumber.getText())>65536){
            loginError.setText("Unvalid port");
            return false;
        } else if (loginUser.getText() == null || loginUser.getText().trim().isEmpty()) {
            loginError.setText("No username");
            return false;
        } else if (loginPassword.getText() == null || loginPassword.getText().trim().isEmpty()) {
            loginError.setText("No password");
            loginUser.setDisableAnimation(true);
            return false;
        }
        return true;
    }

    //TODO use Validator
    private void connect(ActionEvent actionEvent) throws UnknownHostException, ExceptionPortInvalid {
        if (checkInput()) {
            loginError.setText("");
            Config config = Config.getInstance();
            config.setIP(IPServer.getText());
            config.setPort(Integer.parseInt(portNumber.getText()));

            requestManager = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName(IPServer.getText()), Integer.parseInt(portNumber.getText()));

            RequestAuthentication requestAuthentication = new RequestAuthentication(loginUser.getText(), loginPassword.getText());
            try {
                if ((boolean) requestManager.sendRequest(requestAuthentication)) {
                    try {
                        Session.setProperty("user", loginUser.getText());
                        sceneManager.setScenes(MainController.class, 1500, 800);
                    } catch (ExceptionSceneNotFound exceptionSceneNotFound) {
                        exceptionSceneNotFound.printStackTrace();
                    }
                } else {
                    loginError.setText("Authentication Failure");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate() {
        loginConnect.setDefaultButton(true);
        loginConnect.setOnAction(actionEvent -> {
            try {
                connect(actionEvent);
            } catch (UnknownHostException | ExceptionPortInvalid e) {
                e.printStackTrace(); //TODO print an error on screen
            }
        });
        sceneManager = SceneManager.getInstance();
    }

    @Override
    public void onStart() {
        Config c = Config.getInstance();
        IPServer.setText(c.getIP());
        portNumber.setText(String.valueOf(c.getPort()));
    }

    @Override
    protected void onUpdate() {

    }

    @Override
    public void onStop() {

    }
}
